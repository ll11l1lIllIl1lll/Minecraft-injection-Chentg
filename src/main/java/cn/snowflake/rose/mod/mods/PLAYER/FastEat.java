package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module
{
    public FastEat() {
        super("FastEat","Fast Eat", Category.PLAYER);
        setChinesename("\u5feb\u901f\u5403\u4e1c\u897f");
    }

    @Override
    public String getDescription() {
        return "快速吃东西!";
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (this.mc.thePlayer.isEating()
                && this.mc.thePlayer.getItemInUse() != null
                && (this.mc.thePlayer.getItemInUse().getItem() instanceof ItemFood || this.mc.thePlayer.getItemInUse().getItem() instanceof ItemPotion)
                && this.mc.thePlayer.fallDistance < 3.0f
        ) {
            for (int i = 0; i < 8; ++i) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(this.mc.thePlayer.onGround));
            }
        }
    }
}