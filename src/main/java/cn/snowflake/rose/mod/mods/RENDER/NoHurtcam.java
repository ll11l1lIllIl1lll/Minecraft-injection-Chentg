package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;

public class NoHurtcam extends Module {
    public NoHurtcam() {
        super("NoHurtcam","No Hurt cam", Category.RENDER);
        setChinesename("\u65e0\u4f24\u5bb3\u663e\u793a");
    }

    @Override
    public String getDescription() {
        return "无伤害屏幕抖动!";
    }

    public static boolean no;


    @EventTarget
    public void updata(EventUpdate e){
        if(!no)
        no = true;
    }

    @Override
    public void onEnable() {
        no = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        no = false;
        super.onDisable();
    }

}
