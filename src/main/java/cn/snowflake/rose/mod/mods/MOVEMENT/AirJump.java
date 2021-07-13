package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;



public class AirJump
        extends Module {
    public AirJump() {
        super("AirJump","Air Jump", Category.MOVEMENT);
        setChinesename("\u8e0f\u7a7a\u8df3");
    }

    @Override
    public String getDescription() {
        return "踏空跳!";
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventMotion event) {

        mc.thePlayer.onGround = true;
        mc.thePlayer.isAirBorne = false;

    }

    public static float get360Angle(float angle) {
        float output = angle;
        if (output < 0.0f) {
            output += 360.0f;
        }
        return output;
    }
}