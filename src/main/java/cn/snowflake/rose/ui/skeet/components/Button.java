package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.ui.skeet.ui.UI;

import java.util.ArrayList;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.Value;
public class Button {
    public float x;
    public float y;
    public String name;
    public CategoryPanel panel;
    public boolean enabled;
    public Module module;
    public boolean isBinding;

    public Button(CategoryPanel panel, String name, float x, float y, Module module) {
        this.panel = panel;
        this.name = name;
        this.x = x;
        this.y = y;
        this.module = module;
        this.enabled = module.isEnabled();
        panel.categoryButton.panel.theme.buttonContructor(this, this.panel);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            if (!this.panel.visible) continue;
            theme.buttonDraw(this, x, y, this.panel);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.buttonMouseClicked(this, x, y, button, this.panel);
        }
    }

    public void keyPressed(int key) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.buttonKeyPressed(this, key);
        }
    }
}
