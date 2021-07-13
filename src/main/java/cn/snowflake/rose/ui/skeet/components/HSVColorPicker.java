package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.render.ColorValue;


public class HSVColorPicker {
    public float x;
    public float y;
    public ColorPreview colorPreview;
    public ColorValue color;
    public boolean selectingOpacity;
    public boolean selectingColor;
    public boolean selectingHue;
    public float hue;
    public float saturation;
    public float brightness;
    public float opacity;

    public HSVColorPicker(float x, float y, ColorPreview colorPreview, ColorValue color) {
        this.x = x;
        this.y = y;
        this.colorPreview = colorPreview;
        this.color = color;
        colorPreview.categoryPanel.panel.theme.colorPickerConstructor(this, x, y);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.colorPickerDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.colorPickerClick(this, (float)x, (float)y, button);
        }
    }

    public void mouseReleased(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.colorPickerMovedOrUp(this, (float)x, (float)y, button);
        }
    }
}
