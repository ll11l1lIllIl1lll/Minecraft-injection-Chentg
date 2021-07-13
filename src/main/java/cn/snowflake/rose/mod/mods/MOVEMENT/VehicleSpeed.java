package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;

public class VehicleSpeed extends Module {
    public VehicleSpeed() {
        super("VehicleSpeed", "Vehicle Speed", Category.MOVEMENT);
    }
    public static Value<Double> speed = new Value("VehicleSpeed_Speed",1.0d,0.0d,50.0d,1.0d);

    @Override
    public String getDescription() {
        return "车辆速度控制!";
    }

    @EventTarget
    public void update(EventUpdate e){
        if (mc.thePlayer != null && mc.thePlayer.isRiding() && mc.thePlayer.ridingEntity != null){
            mc.thePlayer.ridingEntity.motionX *= speed.getValueState();
            mc.thePlayer.ridingEntity.motionZ *= speed.getValueState();
        }
    }

}
