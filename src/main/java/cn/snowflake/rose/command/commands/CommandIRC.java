package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.mod.mods.WORLD.IRC;

public class CommandIRC
        extends Command {
    public CommandIRC(String[] commands) {
        super(commands);
        this.setArgs(Client.chinese ? "-irc <\u4fe1\u606f> (\u53d1\u9001\u8de8\u670d\u804a\u5929\u4fe1\u606f)" : "-irc <Text>");
    }
    public void onCmd(String[] args) {
        if (args.length == 1) {
            return;
        }
        String msg = "";
        for (int i = 1; i < args.length; ++i) {
            msg = String.valueOf(String.valueOf(String.valueOf(msg))) + args[i] + " ";
        }
        IRC.sendIRCMessage(  msg, true);
    }
}
