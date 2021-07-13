package cn.snowflake.rose.ui.window;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.mods.RENDER.ClickGui;
import cn.snowflake.rose.ui.window.elements.impl.CategoryBox;
import cn.snowflake.rose.ui.window.windows.MainWindows;
import cn.snowflake.rose.utils.render.MouseInputHandler;
import cn.snowflake.rose.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public class UIClick {
    private MouseInputHandler handler;
    private Minecraft mc;
    public MainWindows mainWindows;
    public int startX;
    public int startY;

    private boolean dragging;
    private boolean drag;

    public int moveX = 0;
    public int moveY = 0;

    private ArrayList<CategoryBox> categoriesList;

    public UIClick(){
        this.handler = new MouseInputHandler(0);
        this.mc = Minecraft.getMinecraft();
        startX = 60;
        startY = 40;

        //add CategoryBox
        categoriesList = new ArrayList<>();
        Category[] categories = Category.values();
        for(int i = 0; i < categories.length; ++i  ) {
            Category c = Category.values()[i];
            categoriesList.add(new CategoryBox(c));
        }

    }

    public void mouseMovedOrUp(int mouseX, int mouseY){
        if(dragging) {
            dragging = false;
        }
        if(drag) {
            drag = false;
        }
    }


    public void draw(int mouseX, int mouseY) {
        int minX = startX;
        int maxX = startX + 430;
        int minY = startY;
        int maxY = startY + 270;
        int color = new  Color(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue()).darker().getRGB();

        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);


        if (isHovered(minX ,minY,maxX,maxY - 250,mouseX,mouseY)){
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

        RenderUtil.drawBordRect(minX ,minY,maxX,maxY,0.7,new Color(255,255,255).getRGB(),color);

        Client.instance.fontManager.robotoregular19.drawCenteredString("Chentg Settings",minX + ( (maxX - minX) / 2 ),minY + 30,new Color(0,0,0).getRGB());

        int cx = minX + 48;
        int cy = minY + 110;
        for (int j = 0;j < categoriesList.size();j++){
            CategoryBox categoryBox = categoriesList.get(j);
            categoryBox.draw(cx,cy,mouseX,mouseY);

            cx += 134;
            if ( j > 1 ){
                cx = minX - 220;
                cx = cx + (j * 134);
                cy = minY + 110;
                cy += 55;
            }
        }



    }


    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2) {
            return true;
        }
        return false;
    }

}
