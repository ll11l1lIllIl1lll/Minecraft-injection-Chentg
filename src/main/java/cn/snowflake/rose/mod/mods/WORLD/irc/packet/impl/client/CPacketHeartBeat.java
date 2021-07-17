package cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.IRCPacket;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;

public class CPacketHeartBeat extends IRCPacket {
    public static final String PACKET_ID = "HB";

    public CPacketHeartBeat() {
        super(PACKET_ID + I + PacketsType.SEND + I + Client.username);
    }

}
