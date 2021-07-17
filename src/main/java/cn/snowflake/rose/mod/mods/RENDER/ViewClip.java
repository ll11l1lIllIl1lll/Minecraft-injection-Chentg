package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;

public class ViewClip extends Module
{
    public static boolean x = false;

    public ViewClip() {
        super("ViewClip","View Clip", Category.RENDER);
    }

    @Override
    public String getDescription() {
        return "第三人称视角无遮挡!";
    }

    @Override
    public void onEnable() {
        x = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        x = false;
        super.onDisable();
    }
}
