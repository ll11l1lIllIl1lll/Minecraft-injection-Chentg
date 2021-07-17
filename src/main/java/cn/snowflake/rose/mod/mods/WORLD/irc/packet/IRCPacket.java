package cn.snowflake.rose.mod.mods.WORLD.irc.packet;

import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;

import java.io.PrintWriter;

public class IRCPacket {
    public final static String I = "::";

    private PacketsType type;
    private String data;

    public IRCPacket(String data) {
        this.data = data;
    }

    public IRCPacket(PacketsType type) {
        this.type = type;
    }

    public void sendPacketToServer(PrintWriter printWriter) {
        printWriter.println(data);
    }


}
