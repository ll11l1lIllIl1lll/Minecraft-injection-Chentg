package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.ChatUtil;
import org.lwjgl.input.Keyboard;




public class CommandBind extends Command {
    public CommandBind(String[] command) {
        super(command);
        this.setArgs(Client.chinese ?  "-bind <\u529f\u80fd\u540d\u5b57> <\u6309\u952e\u540d\u5b57> (\u7ed1\u5b9a\u529f\u80fd\u6309\u952e)" :"-bind <mod> <key>");
    }

    @Override
    public void onCmd(String[] args) {
        if(args.length < 3) {
            ChatUtil.sendClientMessage(this.getArgs());
        } else {
            String mod = args[1];
            int key = Keyboard.getKeyIndex((String)args[2].toUpperCase());
            for (Module m : ModManager.modList) {
                if (!m.getName().equalsIgnoreCase(mod)) continue;
                m.setKey(key);
                ChatUtil.sendClientMessage(String.valueOf(m.getName()) + (Client.chinese ? "\u88ab\u7ed1\u5b9a\u5230\u6309\u952e" : " was bound to ") + Keyboard.getKeyName((int)key));
                Client.instance.fileMgr.saveKeys();
                return;
            }
            ChatUtil.sendClientMessage(Client.chinese ? "\u65e0\u6cd5\u627e\u5230\u529f\u80fd" : "Cannot find Module : " + mod);
        }
    }
}
