package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;

import java.util.ArrayList;



public class SLButton {
    public float x;
    public float y;
    public String name;
    public MainPanel panel;
    public boolean load;

    public SLButton(MainPanel panel, String name, float x, float y, boolean load) {
        this.panel = panel;
        this.name = name;
        this.x = x;
        this.y = y;
        this.load = load;
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.slButtonDraw(this, x, y, this.panel);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.slButtonMouseClicked(this, (float)x, (float)y, button, this.panel);
        }
    }
}
