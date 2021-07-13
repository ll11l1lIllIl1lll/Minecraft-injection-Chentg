package cn.snowflake.rose.ui.window;

import cn.snowflake.rose.Client;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class WindowsScreen extends GuiScreen {
    public UIClick windows;
    public boolean initialized;



    public WindowsScreen(){
        if (!this.initialized) {
            this.windows = new UIClick();
            this.initialized = true;
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.windows.draw(mouseX,mouseY);
    }


    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        super.keyTyped(p_73869_1_, p_73869_2_);
    }


    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        this.windows.mouseMovedOrUp(p_146286_1_,p_146286_2_);
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        super.actionPerformed(p_146284_1_);
    }

    @Override
    public void onGuiClosed() {
        Client.instance.fileMgr.saveKeys();
        Client.instance.fileMgr.saveMods();
        Client.instance.fileMgr.saveValues();
        super.onGuiClosed();
    }

}
