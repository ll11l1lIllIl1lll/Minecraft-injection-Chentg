package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;

public class ScreenProtect extends Module {
    public static Value<String> mode = new Value<String>("ScreenProtect","CatMode",0);
    public static Value<String> mode2 = new Value<String>("ScreenProtect","DeciMode",0);

    @Override
    public String getDescription() {
        return "反反作弊保护屏幕模式!";
    }

    public ScreenProtect() {
        super("ScreenProtect", "ScreenProtect", Category.FORGE);
//        mode.addValue("close");
        mode.addValue("leave");
        mode.addValue("Custom");

        mode2.addValue("close");
//        mode2.addValue("leave");
        mode2.addValue("Custom");
    }

}
