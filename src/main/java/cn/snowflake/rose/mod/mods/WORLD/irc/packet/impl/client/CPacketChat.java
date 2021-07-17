package cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.IRCPacket;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;

public class CPacketChat extends IRCPacket {
    public static final String PACKET_ID = "CHAT";


    public CPacketChat(String message) {
        super(PACKET_ID + I + PacketsType.SEND.name() + I + message + I + Client.username);
    }



}
