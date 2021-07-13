package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.MOVEMENT.TP2;
import cn.snowflake.rose.utils.client.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;

public class CommandTP2 extends Command {
    public CommandTP2(String[] command) {
        super(command);
        this.setArgs("-tp2 <x> <y> <z>");
    }
    @Override
    public void onCmd(String[] args) {
        if (args.length < 2) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(this.getArgs()));
        } else {
            if (args.length >= 3) {
                String sx = args[1];
                String sy = args[2];
                String sz = args[3];
                TP2.x = Integer.parseInt(sx);
                TP2.y = Integer.parseInt(sy);
                TP2.z = Integer.parseInt(sz);
                ModManager.getModByName("TP2").set(true);
            } else {
                String playername = args[1];
                if (mc.theWorld.getPlayerEntityByName(playername) != null) {
                    EntityLivingBase entity1 = (EntityLivingBase) mc.theWorld.getPlayerEntityByName(playername);
                    if (entity1.getCommandSenderName().startsWith(playername)) {
                        if (entity1.equals(mc.thePlayer)) {
                            ChatUtil.sendClientMessage(" can not tp youself");
                            return;
                        }
                        TP2.x = (int) entity1.posX;
                        TP2.y = (int) entity1.posY;
                        TP2.z = (int) entity1.posZ;
                        ModManager.getModByName("TP2").set(true);
                    } else {
                        System.out.println(playername + " " + entity1.getCommandSenderName());
                        ChatUtil.sendClientMessage(" can not find the player");
                    }
                }
                return;
            }
        }
    }

}