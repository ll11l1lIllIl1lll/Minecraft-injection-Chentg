package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.Value;

import java.util.ArrayList;



public class Slider {
    public float x;
    public float y;
    public String name;
    public Value setting;
    public CategoryPanel panel;
    public boolean dragging;
    public double dragX;
    public double lastDragX;

    public Slider(CategoryPanel panel, float x, float y, Value setting) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.setting = setting;
        panel.categoryButton.panel.theme.SliderContructor(this, panel);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.SliderDraw(this, x, y, this.panel);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.SliderMouseClicked(this, x, y, button, this.panel);
        }
    }

    public void mouseReleased(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.SliderMouseMovedOrUp(this, x, y, button, this.panel);
        }
    }
}
