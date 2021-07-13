package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;

public class FastClimb extends Module {
    public Value<Double> boost = new Value<Double>("FastClimb_MoitonBoost", 0.2d, 0d, 2.0, 0.01);

    public FastClimb() {
        super("FastClimb", "Fast Climb", Category.MOVEMENT);
        setChinesename("\u5feb\u901f\u722c\u68af");
    }

    @Override
    public String getDescription() {
        return "快速攀爬!";
    }

    @EventTarget
    public void isclimb(EventUpdate eventUpdate){
        if (mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindForward.getIsKeyPressed()){
            mc.thePlayer.motionY += boost.getValueState();
        }
    }

}
