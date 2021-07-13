package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
public class ConfigButton {
    public float x;
    public float y;
    public ConfigList configList;
    public ButtonType buttonType;

    public ConfigButton(ConfigList configList, float x, float y, ButtonType buttonType) {
        this.x = x;
        this.y = y;
        this.buttonType = buttonType;
        this.configList = configList;
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.configButtonDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.configButtonMouseClicked(this, (float)x, (float)y, button);
        }
    }

 public static enum ButtonType {
    LOAD,
    SAVE,
    RESET;
    
    }
}
