package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.events.impl.EventTick;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.Priority;

public class NoRecoil extends Module {
    public static Value<Boolean> vertical = new Value("NoRecoil_Vertical",true);
    public static Value<Boolean> horizontal = new Value("NoRecoil_Horizontal",true);

    public NoRecoil() {
        super("NoRecoil", "No Recoil", Category.COMBAT);
        setChinesename("\u65e0\u540e\u5ea7");
    }

    @Override
    public String getDescription() {
        return "无后坐力!";
    }
    @EventTarget(Priority.HIGH)
    public void ontick(EventTick e){
        if (!ModManager.getModByName("Aimbot").isEnabled() && NoRecoil.horizontal.getValueState()) {
            mc.thePlayer.rotationPitch = mc.thePlayer.prevRotationPitch;
        }
        if (!ModManager.getModByName("Aimbot").isEnabled() && NoRecoil.vertical.getValueState()){
            mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw;
        }
    }

}
