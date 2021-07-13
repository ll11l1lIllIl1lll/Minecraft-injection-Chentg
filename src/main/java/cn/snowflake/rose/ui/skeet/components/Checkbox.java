package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.Value;

import java.util.ArrayList;

public class Checkbox {
    public CategoryPanel panel;
    public boolean enabled;
    public float x;
    public float y;
    public String name;
    public Value setting;

    public Checkbox(CategoryPanel panel, String name, float x, float y, Value setting) {
        this.panel = panel;
        this.name = name;
        this.x = x;
        this.y = y;
        this.setting = setting;
        this.enabled = (Boolean)setting.getValueState();
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            if (!this.panel.visible) continue;
            theme.checkBoxDraw(this, x, y, this.panel);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.checkBoxMouseClicked(this, x, y, button, this.panel);
        }
    }
}
