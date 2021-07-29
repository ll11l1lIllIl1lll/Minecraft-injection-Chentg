package cn.snowflake.rose.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendClientMessage(String string) {
        if (mc.thePlayer != null && mc.theWorld !=null) {
//            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\u00a7b[Season] " + "\u00a7e" + string));
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\u00a7b[Chentg] " + "\u00a7e" + string));
        }
    }

    public static void sendMessageWithoutPrefix(String string) {
        if (mc.thePlayer != null && mc.theWorld !=null) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(string));
        }
    }
}
