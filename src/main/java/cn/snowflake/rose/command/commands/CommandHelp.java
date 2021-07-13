package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.management.CommandManager;
import cn.snowflake.rose.utils.client.ChatUtil;

import java.util.Iterator;

public class CommandHelp extends Command{
    public CommandHelp(String[] commands) {
        super(commands);
        this.setArgs(Client.chinese ? "-help (\u663e\u793a\u6240\u6709\u547d\u4ee4)" : "Shows all commands");
    }

    public void onCmd(String[] args) {
        Iterator var3 = CommandManager.getCommands().iterator();
        while(var3.hasNext()) {
            Command c = (Command)var3.next();
            String aliases = c.getCommands()[0];
            for(int i = 1; i < c.getCommands().length; ++i) {
                aliases = aliases + (i != c.getCommands().length ? ", " : "") + c.getCommands()[i];
            }
            ChatUtil.sendClientMessage(aliases + "\u00a7f " + c.getArgs());
        }
    }
}
