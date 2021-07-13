package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.utils.client.ChatUtil;

public class CommandVClip extends Command {
    public CommandVClip(String[] commands) {
        super(commands);
        setArgs(Client.chinese ? "-vclip \u6216\u8005 - v <\u8ddd\u79bb> (\u7a7f\u5899 \u6b63\u6570\u5411\u4e0a \u8d1f\u6570\u5411\u4e0b)" : "-vclip or -v <distance>");
    }

    public static boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }
    @Override
    public void onCmd(String[] args) {
        if (args.length < 2){
            ChatUtil.sendClientMessage(getArgs());
        }else{
            if (isNumeric(args[1])){
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Double.parseDouble(args[1]), mc.thePlayer.posZ);
            }
        }
        super.onCmd(args);
    }
}
