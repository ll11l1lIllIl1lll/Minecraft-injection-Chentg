package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;

import com.darkmagician6.eventapi.EventTarget;

public class NoRain extends Module {
    public NoRain() {
        super("NoRain", "NoRain", Category.RENDER);
        }
    @Override
    public String getDescription() {
        return "清除下雨天气";
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (mc.theWorld != null) {
            mc.theWorld.setRainStrength(0.0f);
        }
    }
}


