package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.antianticheat.ScreenshotUtil;

public class CommandScreenshot extends Command {
    public CommandScreenshot(String[] commands) {
        super(commands);
        setArgs(Client.chinese ? "-screenshot <deci\u6216\u8005cac> \u8bbe\u7f6e\u81ea\u5b9a\u4e49\u622a\u56fe" : "-screenshot <cac/deci>");
    }


    @Override
    public void onCmd(String[] args) {
        if (args.length < 2){
            ChatUtil.sendClientMessage(this.getArgs());
        }else {
            String c = args[1];
            if (c.equalsIgnoreCase("cac")){
                ScreenshotUtil.catanticheatImage = ScreenshotUtil.catAnticheatScreenhost();
            }
            if (c.equalsIgnoreCase("deci")){
                ScreenshotUtil.bufferedImage = ScreenshotUtil.customScreenshot();
            }
            ChatUtil.sendClientMessage(Client.chinese ? "\u8bbe\u7f6e\u622a\u56fe\u6210\u529f\u0020\u0021" : "Set screenhost successfully !");
        }
        super.onCmd(args);
    }
}
