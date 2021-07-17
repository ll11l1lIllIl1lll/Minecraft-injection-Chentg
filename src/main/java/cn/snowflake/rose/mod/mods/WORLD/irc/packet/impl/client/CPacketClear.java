package cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.IRCPacket;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;

/**
 * @Auther: SnowFlake
 * @Date: 2021/7/17 18:07
 */
public class CPacketClear extends IRCPacket {
    public static final String PACKET_CLEAR = "CLEAR";


    public CPacketClear() {
        super(PACKET_CLEAR + I + PacketsType.SEND.name()+I );
    }



}
