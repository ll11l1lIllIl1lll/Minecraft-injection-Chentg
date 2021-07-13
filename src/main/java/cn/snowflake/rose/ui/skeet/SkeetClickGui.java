package cn.snowflake.rose.ui.skeet;

import java.util.ArrayList;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.components.MainPanel;
import cn.snowflake.rose.ui.skeet.ui.SkeetMenu;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;


public class SkeetClickGui
extends GuiScreen {
    public static MainPanel mainPanel;
    ArrayList<UI> themes = new ArrayList();

    public ArrayList<UI> getThemes() {
        return this.themes;
    }

    public SkeetClickGui() {
        this.themes.add(new SkeetMenu());
        this.mainPanel = new MainPanel("Exhibition", 50.0f, 50.0f, (UI)this.themes.get(0));
    }
    
    @Override
    public void initGui() {
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        this.mainPanel.draw(mouseX, mouseY);
        GlStateManager.popMatrix();
    }

    public void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        this.mainPanel.mouseMovedOrUp(mouseX, mouseY, mouseButton);
        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
    }

    public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
        this.mainPanel.mouseClicked(mouseX, mouseY, clickedButton);
        super.mouseClicked(mouseX, mouseY, clickedButton);
    }

    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.mainPanel.keyPressed(keyCode);
    }
    

    public void onGuiClosed() {
    	Client.instance.fileMgr.saveMods();
    	Client.instance.fileMgr.saveValues();
        Keyboard.enableRepeatEvents((boolean)false);
    }
}
