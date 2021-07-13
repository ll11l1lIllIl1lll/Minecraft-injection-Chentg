package cn.snowflake.rose.command.commands;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class CommandHidden extends Command {

    public CommandHidden(String[] commands) {
        super(commands);
        this.setArgs(Client.chinese ? "-hidden <\u529f\u80fd\u540d\u5b57>" : "-hidden <module>");
    }

    @Override
    public void onCmd(String[] args) {
        if(args.length < 2) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(this.getArgs()));
        } else {
            String mod = args[1];
            for (Module m : Client.instance.modManager.getModList()) {
                if(m.getName().equalsIgnoreCase(mod)) {
                    m.setHidden(!m.isHidden());
                    Client.instance.fileMgr.saveHidden();
                    ChatUtil.sendClientMessage((Client.chinese ? "\u529f\u80fd" : "Module ") + m.getName() + " " + (!m.isHidden() ? (Client.chinese ? "\u663e\u793a" : "display") : (Client.chinese ? "\u9690\u85cf" : "hidden")));
                    return;
                }
            }
        }
    }
}

