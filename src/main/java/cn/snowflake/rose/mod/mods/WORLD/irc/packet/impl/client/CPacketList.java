package cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.IRCPacket;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;

public class CPacketList extends IRCPacket {
    public static final String PACKET_ID = "LIST";


    public CPacketList() {
        super(PACKET_ID + I + PacketsType.SEND.name() +  I + Client.username);
    }


}
