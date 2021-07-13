package cn.snowflake.rose.utils.render;

import cn.snowflake.rose.mod.mods.RENDER.ClickGui;

import java.awt.*;

public class ColorUtil {
    public static Color getClickGUIColor() {
        return new Color((int)ClickGui.r.getValueState().doubleValue(), (int)ClickGui.g.getValueState().doubleValue(), (int)ClickGui.b.getValueState().doubleValue());
    }
    public static Color getClickGUIColora() {
        return new Color((int) ClickGui.r.getValueState().doubleValue(), (int)ClickGui.g.getValueState().doubleValue(), (int)ClickGui.b.getValueState().doubleValue(),240);
    }
}
