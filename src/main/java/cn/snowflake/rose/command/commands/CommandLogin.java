package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.auth.LoginUtil;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;

public class CommandLogin extends Command {
    public CommandLogin(String[] commands) {
        super(commands);
        setArgs(Client.chinese ? "-l\u6216\u8005-login  <\u6e38\u620f\u540d\u5b57> (\u6539\u53d8\u4f60\u7684\u6e38\u620fid)" : "Change minecraft account");
    }

    @Override
    public void onCmd(String[] args) {
        try
        {
            if(args[1].contains(":")) {
                String email = "";
                String password = "";
                if(args[1].contains(":")) {
                    String[] split = args[1].split(":");
                    email = split[0];
                    password = split[1];
                }
                String log = LoginUtil.loginAlt(email, password);
                ChatUtil.sendClientMessage(log);
            }
            else
            {
                LoginUtil.changeCrackedName(args[1]);
                ChatUtil.sendClientMessage((Client.chinese ? "\u767b\u5f55\u6210\u529f" : "Logged [Cracked]: ") + Minecraft.getMinecraft().getSession().getUsername());
            }
        }
        catch(Exception e)
        {
            ChatUtil.sendClientMessage(getArgs());
        }
        super.onCmd(args);
    }
}
