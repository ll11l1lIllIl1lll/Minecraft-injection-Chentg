package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;


public class NoFall extends Module {
    private int hypixel;
    public Value<String> mode = new Value("NoFall_Mode", "Mode", 0);

    public NoFall() {
        super("NoFall","No Fall",  Category.MOVEMENT);
        this.mode.mode.add("onGround");
        setChinesename("\u65e0\u6389\u843d\u4f24\u5bb3");
    }

    @Override
    public String getDescription() {
        return "无视摔落伤害!";
    }

    @EventTarget
    public void OnPre(EventMotion e) {
        if(mode.isCurrentMode("onGround")) {
            if(mc.thePlayer.fallDistance > 2) {
               e.setOnGround(true);
            }
        }
    }

}
