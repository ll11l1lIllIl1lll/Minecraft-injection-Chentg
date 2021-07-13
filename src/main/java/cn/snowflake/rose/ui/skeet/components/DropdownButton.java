package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;


public class DropdownButton {
    public String name;
    public float x;
    public float y;
    public DropdownBox box;

    public DropdownButton(String name, float x, float y, DropdownBox box) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.box = box;
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.dropDownButtonDraw(this, this.box, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.dropDownButtonMouseClicked(this, this.box, x, y, button);
        }
    }
}
