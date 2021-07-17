package cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.IRCPacket;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;
import me.skids.margeleisgay.utils.EncryptionUtils;

import java.util.Objects;

public class CPacketCrash extends IRCPacket {
    public static final String PACKET_ID = "CRASH";



    public CPacketCrash(String username) {
        super(PACKET_ID + I + PacketsType.SEND + I + Objects.requireNonNull(EncryptionUtils.doAES(Client.instance.shitname.split("-")[0], EncryptionUtils.KEY)) + I + username);
    }

}
