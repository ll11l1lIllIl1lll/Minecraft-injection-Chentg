package cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.IRCPacket;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import me.skids.margeleisgay.utils.EncryptionUtils;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class CPacketLogin extends IRCPacket {
    public static final String PACKET_ID = "LOGIN";


    public CPacketLogin() {
        super(PACKET_ID + I +  PacketsType.SEND.name() + I + HWIDUtils.getHWID() + I + Objects.requireNonNull(EncryptionUtils.doAES(Client.instance.shitname.split("-")[0], EncryptionUtils.KEY)) + I + Minecraft.getMinecraft().getSession().getPlayerID());
    }

}
