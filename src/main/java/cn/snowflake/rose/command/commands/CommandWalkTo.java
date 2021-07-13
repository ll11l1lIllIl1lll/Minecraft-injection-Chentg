package cn.snowflake.rose.command.commands;


import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.mod.mods.WORLD.Bot;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.path.AStarPath;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class CommandWalkTo extends Command {
    public static int x;
    public static int y;
    public static int z;

    private boolean startThread = true;

    public CommandWalkTo(String[] commands) {
        super(commands);
        this.setArgs("-to x-y-z");
    }

    @Override
    public void onCmd(String[] args) {
        if (args.length < 2) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(this.getArgs()));
        } else {
            String mod = args[1];
            String[] xyz = mod.split("-");
            String sx = xyz[0];
            String sy = xyz[1];
            String sz = xyz[2];
            this.x = Integer.parseInt(sx);
            this.y = Integer.parseInt(sy);
            this.z = Integer.parseInt(sz);
            ChatUtil.sendClientMessage("已设置坐标! : " + CommandWalkTo.x + "-" + CommandWalkTo.x + "-" +CommandWalkTo.x);

        }
    }

    private void updatePathFinder(int x, int y, int z) {
        if (startThread == false) {
            Bot.pathFinder = new AStarPath(new BlockPos(x, y, z),
                    new BlockPos((int) Minecraft.getMinecraft().thePlayer.posX, (int) Minecraft.getMinecraft().thePlayer.posY, (int) Minecraft.getMinecraft().thePlayer.posZ)
            );
        }


    }
}

