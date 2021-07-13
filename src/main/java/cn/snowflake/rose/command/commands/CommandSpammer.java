package cn.snowflake.rose.command.commands;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.mod.mods.WORLD.Spammer;
import cn.snowflake.rose.utils.client.ChatUtil;

public class CommandSpammer extends Command {

    public CommandSpammer(String[] commands) {
        super(commands);
        this.setArgs(Client.chinese ? "-sp\u6216\u8005-spammer <\u4fe1\u606f>/<load> (\u81ea\u5b9a\u4e49\u81ea\u52a8\u804a\u5929\u4fe1\u606f)" : "-sp\u6216\u8005-spammer <text>/<load> ");
    }

    public void onCmd(String[] args) {
        if (args.length != 2) {
            ChatUtil.sendClientMessage(this.getArgs());
        } else {
            args[1] = args[1].replace("&", "\247");
            if (args[1].equalsIgnoreCase("load")){
                Client.instance.fileMgr.loadSpammerText();
                return;
            }
            Spammer.customtext = args[1];
            Client.instance.fileMgr.saveSpammerText();
            ChatUtil.sendClientMessage( (Client.chinese ? "\u81ea\u52a8\u804a\u5929\u5185\u5bb9\u4fee\u6539\u4e3a\u0020\uff1a\u0020" : "Spammer customText Changed to ") + args[1]);
            super.onCmd(args);
        }
    }

}
