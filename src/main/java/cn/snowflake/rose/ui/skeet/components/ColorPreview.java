package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.render.ColorValue;

public class ColorPreview {
    public String colorName;
    public float x;
    public float y;
    public CategoryButton categoryPanel;
    public ColorValue colorObject;
    public ArrayList<HSVColorPicker> sliders = new ArrayList();

    public ColorPreview(ColorValue colorObject, String colorName, float x, float y, CategoryButton categoryPanel) {
        this.colorObject = colorObject;
        this.categoryPanel = categoryPanel;
        this.colorName = colorName;
        this.x = x;
        this.y = y;
        categoryPanel.panel.theme.colorConstructor(this, x, y);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            if (!this.categoryPanel.enabled) continue;
            theme.colorPrewviewDraw(this, x, y);
        }
    }
}
