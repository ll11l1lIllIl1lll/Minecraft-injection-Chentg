package cn.snowflake.rose.ui;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.RENDER.ClickGui;
import cn.snowflake.rose.mod.mods.RENDER.HUD;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import cn.snowflake.rose.utils.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author SnowFlake , SuChen
 *
 * Time : 2020.01.05 12:01
 *
 */
public class CSGOGUI extends GuiScreen {
    private MouseInputHandler handlerMid = new MouseInputHandler(2);
    private MouseInputHandler handlerRight = new MouseInputHandler(1);
    private MouseInputHandler handler = new MouseInputHandler(0);
    UnicodeFontRenderer font2 = Client.chinese ? Client.instance.fontManager.wqy13 : Client.instance.fontManager.simpleton13;

    public int moveX = 0;
    public int moveY = 0;

    public int startX = 60;
    public int startY = 40;
    public int lastselect;
    public int selectCategory = 0;
    private float scrollY;
    private float modscrollY;

    public static boolean binding = false;
    public Module bmod;
    public static Module currentMod = null;

    private int modlistsize = 0;
    private int valuelistsize = 0;

    private boolean caninput = false;
    private static int alpha;
    private double targetAlpha;
    int selectedChar;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode)  {
        for(int i = 0; i < getModsInCategory(Category.values()[selectCategory]).size(); ++i  ) {
            Module mod = (Module) getModsInCategory(Category.values()[selectCategory]).get(i);
            for (Value value : Value.list){
                if (mod.openValues){
                    if (value.getValueName().split("_")[0].equalsIgnoreCase(mod.getName()) && value.isValueString){
                        if (caninput){
                            selectedChar =  value.getText().toString().length();
                            StringBuilder stringBuilder;
                            String oldString;
                            String textString = value.getText().toString();

                            switch (keyCode){
                                case Keyboard.KEY_BACK:
                                    if (!value.getText().toString().isEmpty()){
                                        oldString = value.getText().toString();
                                        stringBuilder = new StringBuilder(oldString);
                                        stringBuilder.charAt(selectedChar - 1);
                                        stringBuilder.deleteCharAt(selectedChar - 1);

                                        textString = ChatAllowedCharacters.filerAllowedCharacters(stringBuilder.toString());
                                        --selectedChar;

                                        if (selectedChar > textString.length()) {
                                            selectedChar = textString.length();
                                        }

                                        value.setText(textString);
                                    }
                                    break;
                                case Keyboard.KEY_LEFT:
//                                   if (selectedChar > 0) {
//                                       selectedChar = selectedChar - 1;
//                                   }
                                    selectedChar = this.selectedChar - 1;

                                    break;
                                case Keyboard.KEY_RIGHT:
                                    if (selectedChar < textString.length()) {
                                        ++selectedChar;
                                    }
                                    break;
                            }
                            width = font2.getStringWidth(textString.substring(0, selectedChar));

                            if (keyCode != Keyboard.KEY_NONE && ChatAllowedCharacters.isAllowedCharacter(Keyboard.getEventCharacter())){
                                value.setText(value.getText().toString()+Keyboard.getEventCharacter());
                                Client.instance.fileMgr.saveValues();
                            }
                            if (keyCode == Keyboard.KEY_RETURN){
                                caninput = false;
                            }
                        }
                    }
                }
            }
        }
        //the main method by hero code
        if(keyCode == 1) {
            this.lastselect = selectCategory;
        }
        if(binding) {
            if (keyCode != 1) {
                ChatUtil.sendClientMessage("Bound '" + this.bmod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
                this.bmod.setKey(keyCode);
            }
            binding = false;
            bmod = null;
            Client.instance.fileMgr.saveKeys();
        }
        super.keyTyped(typedChar, keyCode);
    }
    boolean move;
    private boolean dragging;
    private boolean drag;

    Value v;

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        if(dragging) {
            dragging = false;
        }
        if(drag) {
            drag = false;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        alpha = (int)RenderUtil.linearAnimation(alpha, this.targetAlpha, 100.0);
        if (isHovered(startX - 20, startY - 8, startX + 300, startY + 5, mouseX, mouseY) && !isHovered(startX+289, startY-8, startX+296, startY+0, mouseX, mouseY)) {
            if(handler.canExcecute()){
                dragging = true;
            }
        }
        if (dragging) {
            if (moveX == 0 && moveY == 0) {
                moveX = mouseX - startX;
                moveY = mouseY - startY;
            } else {
                startX = mouseX - moveX;
                startY = mouseY - moveY;
            }
        }else {
            if (moveX != 0 || moveY != 0) {
                moveX = 0;
                moveY = 0;
            }
        }
        if (startX > (float)(rs.getScaledWidth() - 304)) {
            startX = rs.getScaledWidth() - 304;
        }
        if (startX < 22) {
            startX = 22;
        }
        if (startY > (float)(rs.getScaledHeight() - 202)) {
            startY = rs.getScaledHeight() - 202;
        }
        if (startY < 11.0f) {
            startY = 11;
        }

        int CLRed =ClickGui.r.getValueState().intValue();
        int CLGreen =ClickGui.g.getValueState().intValue();
        int CLBlue =ClickGui.b.getValueState().intValue();

        int CLcolor = new  Color(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(),alpha).getRGB();

        GL11.glPushMatrix();
        GL11.glEnable(3089);
        //GLScissor
        RenderUtil.doGlScissor(startX - 21,startY - 9, startX +302, startY + 199);
        //Category
        RenderUtil.drawRect(startX - 20, startY - 8, startX + 300, startY  + 198,new Color(12,12,12,alpha).getRGB());
        //Mods
        RenderUtil.drawRect(startX + 60, startY - 8, startX + 300, startY  + 198,new Color(22, 22, 22,alpha).getRGB());
        RenderUtil.drawRect(startX + 59.5, startY - 8, startX + 60, startY  + 198,new Color(48, 48, 48,alpha).getRGB());
        RenderUtil.drawRect(startX + 59, startY -8, startX + 59.5, startY  + 198,new Color(0, 0, 0,alpha).getRGB());

        RenderUtil.drawRect(startX + 151, startY - 8, startX + 296, startY  + 198,new Color(25, 25, 25,alpha).getRGB());
        RenderUtil.drawRect(startX + 149.5, startY - 8, startX + 150, startY  + 198,new Color(48, 48, 48,alpha).getRGB());
        RenderUtil.drawRect(startX + 150, startY -8, startX + 150.5, startY  + 198,new Color(0, 0, 0,alpha).getRGB());




//        //value bar
//        RenderUtil.drawRect(startX + 150, startY + 5, startX + 300, startY  + 198,new Color(64,64,64).getRGB());

        //Category Render Recode by SuChen
        int CY = 5;
        Category[] arrcategory = Category.values();
        int n2 = arrcategory.length;
        for(int i = 0; i < n2; ++i  ) {
            Category c = Category.values()[i];
            String name = c.name();

            if(isHovered(startX - 17, startY + 14 + CY, startX + 50, startY + 32+ CY, mouseX, mouseY) ) {
                if (handler.canExcecute()){
                    modscrollY = 0;
                    this.selectCategory = i;
                    this.modlistsize = getModsInCategory(Category.values()[selectCategory]).size();
                }

//                RenderUtil.drawRect(
//                        startX - 20,
//                        startY + 14+ CY,
//                        startX + 60,
//                        startY + 32+ CY,
//                        new Color(82,85,85).getRGB());
            }
            if (selectCategory == i) {
            	
                RenderUtil.rectangleBordered(startX - 20,
                        startY + 14 + CY,
                        startX +56,
                        startY + 33 + CY
                        , (double)0.5, RenderUtil.reAlpha(Colors.getColor(48), alpha), RenderUtil.reAlpha(Colors.getColor(0), alpha));
                RenderUtil.drawRect(
                        startX - 20,
                        startY + 14.5 + CY,
                        startX + 60,
                        startY + 32.5 + CY,
                        new Color(48, 48, 48,alpha).getRGB()
                );
                RenderUtil.drawRect(
                        startX - 20,
                        startY + 15 + CY,
                        startX + 61,
                        startY + 32 + CY,
                        new Color(22, 22, 22,alpha).getRGB()
                );
            }

            String iconname = null;
            if (name.toLowerCase().equalsIgnoreCase("movement")){
                iconname = "A";
            }else if (name.toLowerCase().equalsIgnoreCase("render")){
                iconname =  "C";
            }else if (name.toLowerCase().equalsIgnoreCase("combat")){
                iconname = "D";
            }else if (name.toLowerCase().equalsIgnoreCase("player")){
                iconname = "B";
            }else if (name.toLowerCase().equalsIgnoreCase("forge")){
                iconname = "E";
            }else if (name.toLowerCase().equalsIgnoreCase("world")){
                iconname = "G";
            }

            Client.instance.fontManager.notif20.drawBoldString(iconname,startX - 8,
                    startY + 19 + CY,
                    (selectCategory == i) ? CLcolor :new Color(151, 151, 151,alpha).getRGB());

            //Category name
            Client.instance.fontManager.simpleton13.drawString(
                    name.substring(0, 1)+ name.toLowerCase().substring(1, name.length()),
                    startX +4,
                    startY + 20 +CY,
                    (selectCategory == i) ? CLcolor :new Color(151, 151, 151,alpha).getRGB());
            CY += 18;
        }

        Client.instance.fontManager.simpleton20.drawCenteredString("C",(startX + 4* 3) - 7,startY + 2,RenderUtil.reAlpha(CLcolor, alpha));
        Client.instance.fontManager.simpleton20.drawCenteredString("hentg",(startX + 4* 7+1) - 7,startY + 2,RenderUtil.reAlpha(-1, alpha));

//        Client.instance.fontManager.simpleton20.drawCenteredString("S",(startX + 4* 3) - 7,startY + 2,RenderUtil.reAlpha(CLcolor, alpha));
//        Client.instance.fontManager.simpleton20.drawCenteredString("eason",(startX + 4* 7+1) - 7,startY + 2,RenderUtil.reAlpha(-1, alpha));

        //windows title
//        Client.instance.fontManager.simpleton13.drawCenteredString("ClickGui", startX + 3 * 10, startY - 5, new Color(170, 170, 170).getRGB());


        //Fuck U SnowFlake
	     /*Client.fontManager.simpleton13.drawCenteredString("Combat", startX + 4* 7, startY + 7, new Color(170, 170, 170).getRGB());
	     Client.fontManager.simpleton13.drawCenteredString("Movement", startX + 4* 7, startY + 17, new Color(170, 170, 170).getRGB());
	     Client.fontManager.simpleton13.drawCenteredString("Render", startX + 4 * 7, startY + 27, new Color(170, 170, 170).getRGB());
	     Client.fontManager.simpleton13.drawCenteredString("Player", startX + 4* 7, startY + 37, new Color(170, 170, 170).getRGB());
	     Client.fontManager.simpleton13.drawCenteredString("World", startX + 4* 7, startY + 47, new Color(170, 170, 170).getRGB());
	     if(isHovered(startX + 3, startY + 7, startX + 50, startY + 15, mouseX, mouseY) && handler.canExcecute()) {
		 selectCategory = 0;
	     }else if(isHovered(startX + 3, startY + 15, startX + 50, startY + 23, mouseX, mouseY) && handler.canExcecute()){
		 selectCategory = 1;
	     }else  if(isHovered(startX + 3, startY + 23, startX + 50, startY + 32, mouseX, mouseY) && handler.canExcecute()){
		 selectCategory = 2;
	     }else if(isHovered(startX + 3, startY + 32, startX + 50, startY + 40, mouseX, mouseY) && handler.canExcecute()){
		 selectCategory = 3;
	     }else if(isHovered(startX + 3, startY + 40, startX + 50, startY + 54, mouseX, mouseY) && handler.canExcecute()){
		 selectCategory = 4;
	     }
	     */

        int x =  startX + 64;
        int y = startY + 10;
        int vY= startY + 12;
        String description = "";
        //for head
        for(int i = 0; i < getModsInCategory(Category.values()[selectCategory]).size(); ++i  ) {
            Module mod = (Module)getModsInCategory(Category.values()[selectCategory]).get(i);
            //TODO Mod scroll
            if( isHovered(startX + 60, startY + 5, startX + 150, startY  + 185, mouseX, mouseY) && getModsInCategory(Category.values()[selectCategory]).size() > 12 && isHovered(x, y - 2, x + 82, y+12, mouseX, mouseY)) {
                final float modscroll = (float)Mouse.getDWheel();
                this.modscrollY += modscroll / 10.0f;
            }
            if(getModsInCategory(Category.values()[selectCategory]).size() < 12) {
                modscrollY = 0.0F;
            }
            if (modscrollY > 0.0) {
                modscrollY = 0.0F;
            }
            if(modlistsize > 12 && modscrollY < (modlistsize - 12) * -15) {
                modscrollY = (modlistsize - 12) * -15;
            }

            //mod backgorund
            RenderUtil.drawRoundedRectCSGO(x,
                    y - 2+modscrollY,
                    x + 82,
                    y+12 + modscrollY,
                    new Color(17, 17, 17,alpha).getRGB());

            RenderUtil.rectangle((double)(x+4 + 0.6),
                    y  +3+modscrollY + 0.6,
                    x + 8 + -0.6,
                    y+7 + modscrollY + -0.6,
                    RenderUtil.reAlpha(Colors.getColor(10), alpha));



            RenderUtil.drawGradient(x+4,
                    y  +3+modscrollY,
                    x + 8,
                    y+7 + modscrollY,
                    mod.isEnabled() ?  CLcolor : RenderUtil.reAlpha(Colors.getColor(76), alpha),
                    mod.isEnabled() ?  Colors.getColor(CLRed,CLGreen,CLBlue,alpha <= 120 ? alpha : 120) : RenderUtil.reAlpha(Colors.getColor(51), alpha));

            if(isHovered(startX + 60, startY + 5, startX + 150, startY  + 189, mouseX, mouseY) && isHovered(x, y - 1 + modscrollY , x + 82, y+12 + modscrollY, mouseX, mouseY) && !mod.isEnabled()) {
                RenderUtil.rectangle(x+4,
                        y  +3+modscrollY,
                        x + 8,
                        y+7 + modscrollY,
                        Colors.getColor(255, alpha <= 40 ? alpha : 40));
            }
            if(isHovered(startX + 60, startY + 5, startX + 150, startY  + 189, mouseX, mouseY) && isHovered(x, y - 1 + modscrollY , x + 82, y+12 + modscrollY, mouseX, mouseY) ) {
            description = mod.getDescription();
            }

            //mod name
            font2.drawCenteredStringWithColor(bmod == mod ? "......"  : Client.chinese ? mod.getChinesename() : mod.working ? mod.getName() : "\247c"+mod.getName() , x + 42, y + 2 + modscrollY,  new Color(200, 200, 200, alpha).getRGB());
            // has value
            font2.drawCenteredString(mod.hasValues() ? mod.openValues ? "-" : "+" : "", x + 76 , y + 2 + modscrollY, new Color(200, 200, 200, alpha).getRGB());

            //binding
            if(isHovered(startX + 60, startY + 5, startX + 150, startY  + 185, mouseX, mouseY) && isHovered(x, y - 1 + modscrollY, x + 82, y+12 + modscrollY, mouseX, mouseY) && handlerMid.canExcecute()) {
                binding = true;
                bmod = mod;
            }else if (binding && handlerRight.canExcecute()){
                binding = false;
                bmod.setKey(Keyboard.KEY_NONE);
                ChatUtil.sendClientMessage("Unbound '" + this.bmod.getName() + "'");
                bmod = null;
                Client.instance.fileMgr.saveKeys();
            }

            //mod open
            if(isHovered(startX + 60, startY + 5, startX + 150, startY  + 189, mouseX, mouseY) && isHovered(x, y - 1 + modscrollY, x + 82, y+12 + modscrollY, mouseX, mouseY) && handler.canExcecute()) {
                mod.set(!mod.isEnabled());
                Client.instance.fileMgr.saveMods();
            }
            String ValueName;
            //Open Value
            if(isHovered(startX + 60, startY + 5, startX + 150, startY  + 189, mouseX, mouseY) && isHovered(x, y - 1 + modscrollY , x + 82, y+12 + modscrollY, mouseX, mouseY) && handlerRight.canExcecute() && !mod.openValues) {
                mod.openValues = !mod.openValues;
                currentMod = mod;
                this.scrollY = 0.0F;
                this.valuelistsize = getValueList(currentMod).size();
                for(Module mods : ModManager.modList) {
                    if(mods.openValues && mods.getName() != mod.getName()) {
                        mods.openValues = false;
                    }
                }
                Client.instance.fileMgr.saveValues();
            }

            for (final Value value : Value.list) {
                boolean BBoolean = value.getValueName().split("_")[0].equalsIgnoreCase(mod.getName());
                String StrValue = value.getValueName().split("_")[1];
                String StrName = value.getValueName().split("_")[0];

                if(vY > startY  + 185 && isHovered(startX + 151 ,startY - 8, startX +300, startY + 185, mouseX, mouseY)) {
                    final float scroll = (float)Mouse.getDWheel();
                    this.scrollY += scroll / 10.0f;
                }
                if (scrollY > 0.0) {
                    scrollY = 0.0F;
                }
                if(currentMod != null
                        && valuelistsize >= 12
                        && scrollY < (valuelistsize - 12) * -15) {
                    scrollY = (valuelistsize - 12) * -15;
                }
                //openValues ?
                if(mod.openValues) {
                    if (BBoolean) {
                        if (value.isValueString) {
                            RenderUtil.drawRoundedRectCSGO(x + 145, vY + scrollY-1, x + 230, vY + 9 + scrollY,
                                    new Color(32, 32, 32, alpha).getRGB());

                            if(value.getText().equals("")){
                                font2.drawBoldString( "Null", x + 147, vY + 1 + scrollY, new Color(151,151,151, alpha).getRGB());
                            }else
                                font2.drawBoldString(value.getText() + "", x + 147, vY + 1 + scrollY, new Color(151,151,151, alpha).getRGB());
//                            font2.drawBoldString("_", x + 144 + width, vY + 1 + scrollY, -1);

                            if (isHovered(x + 145, vY + scrollY, x + 230, vY + 8 + scrollY, mouseX, mouseY) && handler.canExcecute()) {
                                this.caninput = true;
                            }
                            if (caninput && this.handlerRight.canExcecute()){
                                this.caninput = false;
                                Client.instance.fileMgr.saveValues();
                            }
                            font2.drawBoldString(StrValue, x + 90, vY + scrollY, new Color(153, 153, 169, alpha).getRGB());
                            vY += 15;
                        } else
                        if (value.isValueDouble) {
                            this.width = 100;
                            float lastMouseX = -1.0f;
                            final double val = (double) value.getValueState();
                            final double min = (double) value.getValueMin();
                            final double max = (double) value.getValueMax();
                            double percSlider = ((double) value.getValueState() - (double) value.getValueMin()) / ((double) value.getValueMax() - (double) value.getValueMin());

                            final double valAbs = mouseX - (x + 145);
                            double perc = valAbs / 85;
                            perc = Math.min(Math.max(0.0, perc), 1.0);
                            final double valRel = ((double) value.getValueMax() - (double) value.getValueMin()) * perc;
                            double valuu = (double) value.getValueMin() + valRel;
                            double valu = (x + 145) + 85 * percSlider;
                            //down bar
                            RenderUtil.rectangle((x + 145 - 0.3),
                                    (vY + 3 + scrollY- 0.3),
                                    (x + 230 + 0.3),
                                    (vY + 5 + scrollY + 0.3),
                                    RenderUtil.reAlpha(Colors.getColor(10), alpha));

                            RenderUtil.drawGradient(x + 145, vY + 3 + scrollY, x + 230, vY + 5 + scrollY,
                            		RenderUtil.reAlpha(Colors.getColor(46), alpha),
                            		RenderUtil.reAlpha(Colors.getColor(27), alpha));

                            RenderUtil.drawGradient(x + 145,
                                    vY + 3 + scrollY,
                                    valu ,
                                    vY + 5 + scrollY,
                                    (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(),alpha),
                                    (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), alpha <= 120 ? alpha : 120));


                            font2.drawBoldString("" + (Double) value.getValueState(), x + 145, vY - 4 + scrollY, new Color(153, 153, 169,alpha).getRGB());

                            font2.drawBoldString(StrValue, x + 90, vY + scrollY, new Color(153, 153, 169,alpha).getRGB());
                            //TODO Double Hovered
                            if (isHovered(startX + 151, startY + 5, startX + 300, startY + 185, mouseX, mouseY) && isHovered(x + 145, vY + 1 + scrollY, x + 230, vY + 7 + scrollY, mouseX, mouseY) && handler.canExcecute()) {
                                v = value;
                                drag=true;
                            }

                            if(drag && value == v) {
                                lastMouseX = (Math.min(Math.max(x + 155, mouseX), x + 145 + 100) - (float)x + 145) / 100;
                                valuu = Math.round(valuu * (1.0 / value.getSteps())) / (1.0 / value.getSteps());
                                value.setValueState(valuu);
                                Client.instance.fileMgr.saveValues();
                            }else {
                                valuu = Math.round((double)value.getValueState() * (1.0 / value.getSteps())) / (1.0 / value.getSteps());
                                value.setValueState(valuu);
                            }

                            vY += 15;
                        }
                        //MODE Recode by SuChen
                        if (value.isValueMode) {
                            String modeName = value.getModeAt(value.getCurrentMode());
                            String NameText = String.valueOf((Object) value.getModeTitle());
                            String modeCountText = String.valueOf((int) (value.getCurrentMode() + 1)) + "/" + value.mode.size();

                            boolean LeftHovered = isHovered(startX + 151, startY + 5, startX + 300, startY + 184, mouseX, mouseY) && isHovered(x + 145, vY + scrollY - 1, x + 151, vY + 7 + scrollY, mouseX, mouseY);
                            boolean RightHovered = isHovered(startX + 151, startY + 5, startX + 300, startY + 184, mouseX, mouseY) && isHovered(x + 209, vY + scrollY - 1, x + 215, vY + 7 + scrollY, mouseX, mouseY);

                            int Left = LeftHovered ? 1:0;
                            int Right = RightHovered ? 1:0;

                            RenderUtil.drawRect(x + 145 -0.3,
                                    (vY + scrollY  - 1 -Left - 0.3),
                                    (x + 151 + 0.3),
                                    (vY + 7+Left + scrollY+ 0.3),
                                    Colors.getColor(10));

                            RenderUtil.drawGradient(x + 145, vY + scrollY - 1 - Left, x + 151, vY + 7+Left + scrollY,

                                    Colors.getColor(LeftHovered? 46+20 : 46),
                                    Colors.getColor(LeftHovered? 27+10 : 27));

                            GlStateManager.pushMatrix();
                            GlStateManager.translate((double) (x + 148.0F), (double) vY + scrollY + 4, 0.0D);
                            GlStateManager.rotate(mod.openValues ? 180.0F : 90.0F, 0.0F, 0.0F, 90.0F);
                            RenderUtil.rectangle(-1.0D, 0.0D, -0.5D, 2.5D, Colors.getColor(151));
                            RenderUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(151));
                            RenderUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(151));
                            RenderUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(151));
                            GlStateManager.popMatrix();


                            RenderUtil.drawRoundedRectCSGO(x + 152, vY + scrollY -2, x + 208, vY + 8 + scrollY,
                                    new Color(32, 32, 32).getRGB());


                            RenderUtil.drawRect(x + 209-0.3,
                                    (vY + scrollY  - 1 -Right- 0.3),
                                    (x + 215 + 0.3),
                                    (vY + 7+Right + scrollY+ 0.3),
                                    Colors.getColor(10));

                            RenderUtil.drawGradient(x + 209, vY + scrollY - 1-Right, x + 215, vY + 7+Right + scrollY,
                                    Colors.getColor(RightHovered? 46+20 : 46),
                                    Colors.getColor(RightHovered? 27+10 : 27));

                            GlStateManager.pushMatrix();
                            GlStateManager.translate((double) (x + 212.0F), (double) vY + scrollY + 1.8, 0.0D);
                            GlStateManager.rotate(mod.openValues ? 360.0F : 90.0F, 0.0F, 0.0F, 90.0F);
                            RenderUtil.rectangle(-1.0D, 0.0D, -0.5D, 2.5D, Colors.getColor(151));
                            RenderUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(151));
                            RenderUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(151));
                            RenderUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(151));
                            GlStateManager.popMatrix();

                            font2.drawBoldString(modeName, x + 180 - font2.getStringWidth("" + modeName) / 2, vY + scrollY - 0.5f,  new Color(151, 151, 151).getRGB());

                            font2.drawBoldString(NameText, x + 90, vY + scrollY - 1, new Color(153, 153, 169).getRGB());

                            font2.drawBoldString(modeCountText, x + 230 - font2.getStringWidth(modeCountText), vY + scrollY, new Color(153, 153, 169).getRGB());

                            if (isHovered(startX + 151, startY + 5, startX + 300, startY + 184, mouseX, mouseY) && isHovered(x + 145, vY + scrollY - 1, x + 151, vY + 7 + scrollY, mouseX, mouseY) && handler.canExcecute()) {

                                if (value.getCurrentMode() > 0 && value.getCurrentMode() != 0) {
                                    value.setCurrentMode(value.getCurrentMode() - 1);
                                    Client.instance.fileMgr.saveValues();
                                } else {
                                    value.setCurrentMode(value.mode.size() - 1);
                                    Client.instance.fileMgr.saveValues();
                                }
                            }

                            if (isHovered(startX + 151, startY + 5, startX + 300, startY + 184, mouseX, mouseY) && isHovered(x + 209, vY + scrollY - 1, x + 215, vY + 7 + scrollY, mouseX, mouseY) && handler.canExcecute()) {
                                if (value.getCurrentMode() < value.mode.size() - 1) {
                                    value.setCurrentMode(value.getCurrentMode() + 1);
                                    Client.instance.fileMgr.saveValues();
                                } else {
                                    value.setCurrentMode(0);
                                    Client.instance.fileMgr.saveValues();
                                }
                            }

                            vY += 15;
                        }

                        //TODO Boolean
                        if (value.isValueBoolean) {
                            RenderUtil.rectangle((x + 210 - 0.3),
                                    (vY + scrollY - 0.3),
                                    (x + 230 + 0.3),
                                    (vY + 6 + scrollY+ 0.3),
                                    Colors.getColor(10));

                            RenderUtil.drawGradient(x + 210, vY + scrollY , x + 230, vY + 6 + scrollY,
                            		RenderUtil.reAlpha(Colors.getColor(46), alpha),
                            		RenderUtil.reAlpha(Colors.getColor(27),alpha));

//                            if ((Boolean) value.getValueState()) {
//                                RenderUtil.drawRect(x + 220, vY + scrollY, x + 229, vY + 6 + scrollY, new Color(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue()).getRGB());
//                            } else {
//                                RenderUtil.drawRect(x + 211, vY + scrollY, x + 220, vY + 6 + scrollY, new Color(153, 153, 153).getRGB());
//                            }
                            RenderUtil.drawGradient(x + (((Boolean) value.getValueState()) ? 220 :210),
                                    vY + scrollY,
                                    x + (((Boolean) value.getValueState()) ? 230 :220),
                                    vY + 6 + scrollY,
                                    ((Boolean) value.getValueState()) ?  (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(),alpha) :
                                            RenderUtil.reAlpha(Colors.getColor(76), alpha),
                                    ((Boolean) value.getValueState()) ? (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), alpha <= 120 ? alpha : 120):
                                    	RenderUtil.reAlpha(Colors.getColor(51), alpha));

                            //Boolean
                            if (isHovered(x + 210, vY + scrollY - 1, x + 230, vY + 7 + scrollY, mouseX, mouseY) && handler.canExcecute()) {
                                value.setValueState(!(Boolean) value.getValueState());
                                Client.instance.fileMgr.saveValues();
                            }

                            font2.drawBoldString(StrValue, x + 90, vY + scrollY, new Color(153, 153, 169,alpha).getRGB());
                            vY += 15;
                        }
                    }
                }
            }
            y+= 15;
        }

        boolean saveHovered = isHovered(startX - 14, startY + 160 , startX + 56, startY + 174, mouseX, mouseY);
        //saveCFG
        RenderUtil.drawRoundedRectCSGO(startX-14 , startY + 160, startX + 56, startY  + 174,new Color(17, 17, 17,alpha).getRGB());

        if(saveHovered) {
            font2.drawCenteredString("Save Config",startX+20 ,startY+164,RenderUtil.reAlpha(-1, alpha));
            description = "保存你现在的配置到系统用户里!";
            if(handler.canExcecute()){
                if (ClickGui.export.getValueState()){
                    Client.instance.fileMgr.saveLocalConfig();
                }else {
                    Client.instance.fileMgr.saveConfig();
                }
            }

        }else {
            font2.drawCenteredString("Save Config",startX+20 ,startY+164,new Color(153, 153, 153,alpha).getRGB());
        }

        //loadCFG
        RenderUtil.drawRoundedRectCSGO(startX-14 , startY + 178, startX + 56, startY  + 192,new Color(17, 17, 17,alpha).getRGB());
        if(isHovered(startX - 14, startY + 178 , startX + 56, startY + 192, mouseX, mouseY)) {
            font2.drawCenteredString("Load Config",startX+20 ,startY+182,RenderUtil.reAlpha(-1, alpha));
            description = "读取你保存在系统用户里的配置!";
            if(handler.canExcecute()){
                if (ClickGui.export.getValueState()){
                    Client.instance.fileMgr.loadLocalConfig();
                }else {
                    Client.instance.fileMgr.loadConfig();
                }

            }
        }else {
            font2.drawCenteredString("Load Config",startX+20 ,startY+182,new Color(153, 153, 153,alpha).getRGB());
        }

        //top bar
        RenderUtil.drawRect(startX + 60, startY - 8, startX + 300, startY  + 6,new Color(0, 0, 0,alpha).getRGB());
        RenderUtil.drawRect(startX + 60, startY - 8, startX + 300, startY  + 5.5,new Color(48, 48, 48,alpha).getRGB());
        RenderUtil.drawRect(startX + 60, startY - 8, startX + 300, startY  + 5,new Color(25, 25, 25,alpha).getRGB());

        RenderUtil.drawGradientRect(startX - 17, startY - 6, startX +298, startY - 5.5,true,
                HUD.rainbow(100),
                HUD.rainbow1(System.nanoTime(),2f,1f).getRGB());

        if (ClickGui.info.getValueState()){
            mc.fontRenderer.drawString(description,startX+62,startY-4,new Color(180,180,180,alpha).getRGB());
        }
        //buttom bar
        /*
        RenderUtil.drawRect(startX + 60, startY + 189, startX + 300, startY  + 196,new Color(25, 25, 25).getRGB());
        RenderUtil.drawRect(startX + 60, startY + 189.5, startX + 300, startY  + 196,new Color(48, 48, 48).getRGB());
        RenderUtil.drawRect(startX + 60, startY + 190, startX + 298, startY  + 196,new Color(25, 25, 25).getRGB());
		*/
        RenderUtil.rectangleBordered((double)((double)(startX - 20) - 0.3), (double)((double)(startY - 8) - 0.3-0.5), (double)((double)(startX + 300) + 0.5), (double)((double)(startY + 198) + 0.3+0.5), (double)0.5, (int) Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)10,alpha));
        RenderUtil.rectangleBordered((double)(startX - 20), (double)(startY - 8-0.5), (double)(startX + 300), (double)(startY + 198+0.5), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)60,alpha));
        RenderUtil.rectangleBordered((double)(startX - 20 + 2.0f), (double)(startY - 8 + 2.0f-0.5), (double)(startX + 300 - 2.0f), (double)(startY + 198 - 2.0f+0.5), (double)0.5, (int)Colors.getColor((int)0, (int)0), Colors.getColor(60,alpha));
        RenderUtil.rectangleBordered((double)((double)(startX - 20) + 0.6), (double)((double)(startY - 8) + 0.6-0.5), (double)((double)(startX + 300) - 0.5), (double)((double)(startY + 198) - 0.6+0.5), (double)1.3, Colors.getColor(0, 0), Colors.getColor((int)40,alpha));


        GL11.glDisable(3089);
        GL11.glPopMatrix();




    }

    /**
     *
     * @param x left
     * @param y top
     * @param x2 right
     * @param y2 bottom
     * @param mouseX
     * @param mouseY
     * @return mouse hovered ?
     */
    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2) {
            return true;
        }
        return false;
    }


    @Override
    public void initGui() {
        alpha = 0;
        this.targetAlpha = 255.0;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }


    public static double round(double value) {
        return (double)Math.round(value * 100.0D) / 100.0D;
    }

    /**
     * @author SuChen
     * @param mods Mod
     * @return valueList
     */
    public static List getValueList(Module mods) {
        List valueList = new ArrayList();
        Iterator var3 = Value.list.iterator();
        while(var3.hasNext()) {
            Value value = (Value)var3.next();
//            if(currentMod == null) {
//                return null;
//            }
            if (value.getValueName().split("_")[0].equalsIgnoreCase(mods.getName())) {
                valueList.add(value);
            }
        }
        return valueList;
    }

    /**
     * @author SnowFalke
     * @param category
     * @return modList
     */
    public static List getModsInCategory(Category category) {
        List modList = new ArrayList();
        Iterator var3 = Client.instance.modManager.getModList().iterator();

        while(var3.hasNext()) {
            Module mod = (Module)var3.next();
            if (mod.getCategory() == category) {
                modList.add(mod);
            }
        }
        return modList;
    }

}