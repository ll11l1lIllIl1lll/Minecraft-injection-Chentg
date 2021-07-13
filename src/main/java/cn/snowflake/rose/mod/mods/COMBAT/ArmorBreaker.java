package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;


/**
 *
 *  Author :
 *  From Sakura Client
 *
 *
 */
public class ArmorBreaker extends Module {
    public ArmorBreaker() {
        super("ArmorBreaker","Armor Breaker", Category.COMBAT);
        setChinesename("\u7834\u7532");
    }
    TimeHelper time = new TimeHelper();

    @Override
    public String getDescription() {
        return "破甲!";
    }

    @EventTarget
    public void onPacket(EventPacket e){
        if (e.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity) e.getPacket()).func_149565_c() == C02PacketUseEntity.Action.ATTACK && time.isDelayComplete(200L) && mc.theWorld != null){
            e.setCancelled(true);
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId,9,mc.thePlayer.inventory.currentItem, 2,mc.thePlayer);
            mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity( ( (C02PacketUseEntity)e.getPacket() ).func_149564_a(mc.theWorld), C02PacketUseEntity.Action.ATTACK));
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId,9,mc.thePlayer.inventory.currentItem, 2,mc.thePlayer);
            this.time.reset();
        }
    }


}
