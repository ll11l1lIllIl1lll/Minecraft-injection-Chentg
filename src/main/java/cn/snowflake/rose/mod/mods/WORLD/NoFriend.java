package cn.snowflake.rose.mod.mods.WORLD;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;

public class NoFriend extends Module {
    public NoFriend() {
        super("NoFriend","No Friend",  Category.WORLD);
        setChinesename("\u65e0\u89c6\u670b\u53cb");
    }

    @Override
    public String getDescription() {
        return "无视朋友!";
    }

}
