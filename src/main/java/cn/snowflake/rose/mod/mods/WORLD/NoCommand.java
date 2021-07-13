package cn.snowflake.rose.mod.mods.WORLD;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;

public class NoCommand extends Module {
    public static boolean n = false;
    public NoCommand() {
        super("NoCommand","No Command", Category.WORLD);
        setChinesename("\u5173\u95ed\u547d\u4ee4");
    }

    @Override
    public String getDescription() {
        return "关闭命令台!";
    }

    @Override
    public void onDisable() {
        n = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        n = true;
        super.onEnable();
    }
}
