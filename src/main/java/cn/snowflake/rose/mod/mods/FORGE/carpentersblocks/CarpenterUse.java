package cn.snowflake.rose.mod.mods.FORGE.carpentersblocks;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;

public class CarpenterUse extends Module {

    public CarpenterUse() {
        super("CarpenterUse","Carpenter Use", Category.FORGE);
            setWorking(Loader.isModLoaded("CarpentersBlocks"));
    }

    @Override
    public String getDescription() {
        return "强制打开别人的任何箱子!";
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        super.onDisable();
    }

    @SubscribeEvent
    public void mouseEvent(MouseEvent event) {
        if (event.button == 1 && event.buttonstate) {
            try {
                MovingObjectPosition position = mc.objectMouseOver;
                if (position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    if (event.isCancelable()) {
                        event.setCanceled(true);
                    }
                    ByteBuf buf = Unpooled.buffer(0);
                    buf.writeInt(0);
                    buf.writeInt(position.blockX);
                    buf.writeInt(position.blockY);
                    buf.writeInt(position.blockZ);
                    buf.writeInt(position.sideHit);
                    C17PacketCustomPayload packet = new C17PacketCustomPayload("CarpentersBlocks", buf);
                    mc.thePlayer.sendQueue.addToSendQueue(packet);
                    Client.instance.getNotificationManager().addNotification(this,"Open!", Notification.Type.SUCCESS);
                }
            } catch (Exception e) {
            }
        }
    }

}
