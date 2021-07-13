package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;

public class NameProtect extends Module {
    public static Value<String> name = new Value<>("NameProtect_Name","","ChentgUser");



    public NameProtect() {
        super("NameProtect", "Name Protect", Category.RENDER);
        setChinesename("\u533f\u540d");
    }

    @Override
    public String getDescription() {
        return "匿名!";
    }

}
