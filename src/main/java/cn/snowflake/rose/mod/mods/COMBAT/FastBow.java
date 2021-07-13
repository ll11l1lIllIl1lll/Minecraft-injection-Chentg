package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public  class FastBow extends Module {
    public FastBow() {
        super("FastBow","Fast Bow",  Category.COMBAT);
        setChinesename("\u5feb\u901f\u62c9\u5f13");
    }

    @Override
    public String getDescription() {
        return "快速射弓!";
    }
    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (this.mc.thePlayer.getHealth() > 0.0f && (this.mc.thePlayer.onGround || this.mc.thePlayer.capabilities.isCreativeMode) && this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && this.mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
            this.mc.playerController.sendUseItem((EntityPlayer)this.mc.thePlayer,this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
            this.mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer);
            for (int i = 0; i < 20; ++i) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(5, 0, 0, 0, 255));
            this.mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer, 10);
        }
    }
    
}
