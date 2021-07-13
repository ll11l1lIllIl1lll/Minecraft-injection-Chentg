package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.injection.ClientLoader;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

import java.lang.reflect.InvocationTargetException;

public class NoSlowDown extends Module {
    public Value<String> mode = new Value<>("NoSlow","Mode",0);

    public NoSlowDown() {
        super("NoSlow","No Slow", Category.PLAYER);
        this.mode.addValue("Normal");
        this.mode.addValue("NCP");
        this.mode.addValue("AAC");
        setChinesename("\u4f7f\u7528\u7269\u54c1\u65e0\u51cf\u901f");
    }
    @Override
    public String getDescription() {
        return "\u4f7f\u7528\u7269\u54c1\u65e0\u51cf\u901f!";
    }
    public static boolean no = false;
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

    @EventTarget
    public void onUpdate(EventUpdate e){
        no = isEnabled();
        if (mode.isCurrentMode("NCP") || mode.isCurrentMode("AAC")){
            if((this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && mc.thePlayer.isBlocking()  ) || (this.mc.thePlayer.getHeldItem().getItem() instanceof ItemFood && mc.thePlayer.isUsingItem())) {
                try {
                    mc.playerController.getClass().getDeclaredMethod(ClientLoader.runtimeObfuscationEnabled ? "func_78750_j" : "syncCurrentPlayItem").invoke(mc.playerController);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException illegalAccessException) {
                }
                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(5, 0,0,0, 0));
            }
        }
    }


}
