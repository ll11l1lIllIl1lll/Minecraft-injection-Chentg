package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.mod.mods.WORLD.Xray;
import cn.snowflake.rose.utils.client.ChatUtil;
import net.minecraft.block.Block;

import java.util.regex.Pattern;

public class CommandXray extends Command {
    public CommandXray(String[] commands) {
        super(commands);
        this.setArgs("-xray add id / -xray remove id");
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    @Override
    public void onCmd(String[] args){
        if (args.length < 3){
            ChatUtil.sendClientMessage(this.getArgs());
        }else{
            String strid = args[2];
            String c = args[1];
            if (isNumeric(strid)){
                Block block = Block.getBlockById( Integer.parseInt(strid));
                if (c.equalsIgnoreCase("add")){
                    if (!Xray.block.contains(block)){
                        Xray.block.add(block);
                        ChatUtil.sendClientMessage((Client.chinese ? "\u6dfb\u52a0" : "Add ")+ block.getLocalizedName() + (Client.chinese ?  "\u6210\u529f" : " Successfully !"));
                        Client.instance.fileMgr.saveBlocks();
                    }else{
                        ChatUtil.sendClientMessage(Client.chinese ? "\u4f60\u5df2\u7ecf\u6dfb\u52a0\u4e86\u8fd9\u4e2a\u65b9\u5757" : "You are already add this block !");
                    }
                }
                if (c.equalsIgnoreCase("remove")){
                    if (Xray.block.contains(block)){
                        Xray.block.remove(block);
                        ChatUtil.sendClientMessage((Client.chinese ?  "\u5220\u9664": "Remove ") + block.getLocalizedName() + (Client.chinese ?  "\u6210\u529f" : " Successfully !"));
                        Client.instance.fileMgr.saveBlocks();
                    }else{
                        ChatUtil.sendClientMessage(Client.chinese ? "\u4f60\u6ca1\u6dfb\u52a0\u8fd9\u4e2a\u65b9\u5757" : "You have no add this block !");
                    }
                }
            }else{
                ChatUtil.sendClientMessage(this.getArgs());
            }
        }
    }

}
