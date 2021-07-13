package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;
import java.util.List;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;

public class ConfigList {
    public float x;
    public float y;
    public CategoryPanel categoryPanel;
    public List<ConfigButton> configButtonList = new ArrayList();

    public ConfigList(float x, float y, CategoryPanel categoryPanel) {
        this.x = x;
        this.y = y;
        this.categoryPanel = categoryPanel;
        this.configButtonList.add((ConfigButton)new ConfigButton(this, x, y, ConfigButton.ButtonType.LOAD));
        this.configButtonList.add((ConfigButton)new ConfigButton(this, x, y, ConfigButton.ButtonType.SAVE));
        this.configButtonList.add((ConfigButton)new ConfigButton(this, x, y, ConfigButton.ButtonType.RESET));
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.configListDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.configListMouseClicked(this, (float)x, (float)y, button);
        }
    }
}
