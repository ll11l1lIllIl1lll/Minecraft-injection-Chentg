package cn.snowflake.rose.mod.mods.FORGE.mrf;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;

public class RocketChaos extends Module {

    public RocketChaos(){
        super("RocketChaos","Rocket Chaos", Category.FORGE);
            setWorking(Loader.isModLoaded("MineFactoryReloaded"));
    }

    @Override
    public String getDescription() {
        return "鼠标左键发射火箭弹!";
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
        if (Mouse.isButtonDown(0)) {
            sendRocket(0);
        }
    }
    public void sendRocket(int entityId) {
        int playerId = mc.thePlayer.getEntityId();
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(mc.thePlayer.dimension);
        buf.writeShort(11);
        buf.writeInt(playerId);
        buf.writeInt(entityId);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("MFReloaded", buf);
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

}
