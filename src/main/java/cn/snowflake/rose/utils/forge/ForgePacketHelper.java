package cn.snowflake.rose.utils.forge;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class ForgePacketHelper {
	private Minecraft mc = Minecraft.getMinecraft();
	
    public static void sendPacket(Packet packet) {
    	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    public static void sendPacket(String channel, ByteBuf data) {
        sendPacket(new FMLProxyPacket(data, channel));
    }
    
    public static void sendPacket(String channel, Object ... data) {
        sendPacket(channel, bufWriter(data));
    }
    
    public static ByteBuf bufWriter(Object ... data) {
        return bufWriter(Unpooled.buffer(0), data);
    }
    
}
