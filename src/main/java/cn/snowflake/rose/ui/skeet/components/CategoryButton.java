package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;
import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.Value;


public class CategoryButton {
    public float x;
    public float y;
    public String name;
    public MainPanel panel;
    public boolean enabled;
    public CategoryPanel categoryPanel;

    public CategoryButton(MainPanel panel, String name, float x, float y) {
        this.panel = panel;
        this.name = name;
        this.x = x;
        this.y = y;
        panel.theme.categoryButtonConstructor(this, this.panel);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.categoryButtonDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.categoryButtonMouseClicked(this, this.panel, x, y, button);
        }
    }

    public void mouseReleased(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.categoryButtonMouseReleased(this, x, y, button);
        }
    }
}
