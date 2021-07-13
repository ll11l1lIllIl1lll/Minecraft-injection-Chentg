package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.ui.skeet.ui.UI;

import java.util.ArrayList;


public class GroupBox {
    public float x;
    public float y;
    public float ySize;
    public CategoryPanel categoryPanel;
    public Module module;

    public GroupBox(Module module, CategoryPanel categoryPanel, float x, float y, float ySize) {
        this.x = x;
        this.y = y;
        this.categoryPanel = categoryPanel;
        this.module = module;
        this.ySize = ySize;
        categoryPanel.categoryButton.panel.theme.groupBoxConstructor(this, x, y);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.groupBoxDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.groupBoxMouseClicked(this, x, y, button);
        }
    }

    public void mouseReleased(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.groupBoxMouseMovedOrUp(this, x, y, button);
        }
    }
}
