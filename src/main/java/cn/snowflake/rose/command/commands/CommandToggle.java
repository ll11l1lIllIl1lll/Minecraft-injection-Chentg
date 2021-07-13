package cn.snowflake.rose.command.commands;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.ChatUtil;

public class CommandToggle extends Command {

    public CommandToggle(String[] commands) {
        super(commands);
        this.setArgs(Client.chinese ? "-t \u6216\u8005 -toggle <\u529f\u80fd\u540d\u5b57> (\u5f00\u5173\u529f\u80fd)" : "-t / -toggle <module>");
    }

    @Override
    public void onCmd(String[] args) {
        if(args.length < 2) {
            ChatUtil.sendClientMessage(this.getArgs());
        } else {
            String mod = args[1];
            for (Module m : Client.instance.modManager.getModList()) {
                if(m.getName().equalsIgnoreCase(mod)) {
                    m.set(!m.isEnabled());
                    ChatUtil.sendClientMessage((Client.chinese ? "\u529f\u80fd" : "Module ") + m.getName() + " " + (m.isEnabled() ? (Client.chinese ? "\u6253\u5f00" : "Toggle") : (Client.chinese ? "\u6253\u5f00" : "Disable")));
                    return;
                }
            }
        }
    }
}
