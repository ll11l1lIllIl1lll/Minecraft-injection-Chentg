package cn.snowflake.rose.ui;

import cn.snowflake.rose.antianticheat.impl.NaBanMod;
import org.lwjgl.input.Keyboard;

import cn.snowflake.rose.utils.auth.LoginUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiMultiplayerLogin extends GuiMultiplayer
{
    
    private GuiTextField username;
    public GuiMultiplayerLogin(GuiScreen GuiScreen) {
        super(GuiScreen);
    }
    
    public void initGui() {
        super.initGui();
        this.username = new GuiTextField(mc.fontRenderer, 2, 14, 80, 14);
        this.username.setFocused(true);
        this.buttonList.add(new GuiButton(300, 85, 10, 60, 20, "Login"));
        this.buttonList.add(new GuiButton(400, 155, 10, 60, 20, "UpdateID"));
        NaBanMod.cpuID = new StringBuilder().insert(0,NaBanMod.getCpuID()).append(NaBanMod.getSerialNumber()).toString();
    }
    
    @Override
    public void drawScreen(int x2, int y2, float z2) {
		super.drawScreen(x2, y2, z2);
//        this.drawString(mc.fontRenderer, "Name\2478 : \247f" + this.mc.getSession().getUsername(),2, 2, 0x00FF00);

        this.drawString(mc.fontRenderer, "Name\2478 : \247f" + this.mc.getSession().getUsername()  + "  \2474CPUID : \247f" + NaBanMod.cpuID,2, 2, 0x00FF00);
	    this.username.drawTextBox();
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();

    }
    
    @Override
    protected void keyTyped(char character, int key) {
        super.keyTyped(character, key);
        if (character == '\t') {
            if (!this.username.isFocused()) {
                this.username.setFocused(true);
            }
        }
        if (character == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        super.mouseClicked(x2, y2, button);
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void actionPerformed(GuiButton GuiButton) {
        super.actionPerformed(GuiButton);
        if (GuiButton.enabled && GuiButton.id == 300) {
         LoginUtil.changeCrackedName(username.getText());
        }
        if (GuiButton.enabled && GuiButton.id == 400) {
            NaBanMod.cpuID = new StringBuilder().insert(0,NaBanMod.getCpuID()).append(NaBanMod.getSerialNumber()).toString();
        }
    }
    
    
}