package cn.snowflake.rose.mod.mods.PLAYER;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastUse extends Module {
    public boolean canBoost = false;
    public Value<Boolean> allitem = new Value<>("FastUse_AllItem",false);
    private Value<Double> delay = new Value<Double>("FastUse_UseDelay", 50.0, 0.0, 100.0, 1.0);
    TimeHelper timeHelper = new TimeHelper();
    public FastUse() {
        super("FastUse","Fast Use", Category.PLAYER);
        setChinesename("\u5feb\u901f\u4f7f\u7528");
    }

    @Override
    public String getDescription() {
        return "快速使用!";
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (allitem.getValueState() && timeHelper.isDelayComplete(delay.getCurrentMode())){
            this.canBoost = mc.gameSettings.keyBindUseItem.getIsKeyPressed();
        }
        if (!allitem.getValueState() && mc.thePlayer.getItemInUseDuration() == 16) {
            this.canBoost = true;
        }

        if ((mc.thePlayer.onGround || mc.thePlayer.isRiding()) && this.canBoost && !(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
            for (int i = 0; i < 25; ++i) {
                this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            }
            this.canBoost = false;
        }
    }
}
