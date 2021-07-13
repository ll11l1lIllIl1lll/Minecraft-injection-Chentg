package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;
import java.util.List;
import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.Value;

public class DropdownBox {
    public Value setting;
    public float x;
    public float y;
    public List<DropdownButton> buttons = new ArrayList();
    public CategoryPanel panel;
    public boolean active;

    public DropdownBox(Value setting, float x, float y, CategoryPanel panel) {
        this.setting = setting;
        this.panel = panel;
        this.x = x;
        this.y = y;
        panel.categoryButton.panel.theme.dropDownContructor(this, x, y, this.panel);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            if (!this.panel.visible) continue;
            theme.dropDownDraw(this, x, y, this.panel);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.dropDownMouseClicked(this, x, y, button, this.panel);
        }
    }
}
