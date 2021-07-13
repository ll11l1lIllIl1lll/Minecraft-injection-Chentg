package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.management.ModManager;

public class CommandPlugins extends Command {
    public CommandPlugins(String[] commands) {
        super(commands);
        this.setArgs(Client.chinese ? "\u67e5\u770b\u63d2\u4ef6" : "-plugins");
    }

    @Override
    public void onCmd(String[] args) {
        ModManager.getModByName("Plugins").set(true);
        super.onCmd(args);
    }
}
