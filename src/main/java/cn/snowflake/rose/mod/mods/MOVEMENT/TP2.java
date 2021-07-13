package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventTick;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.ChatUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;

public class TP2 extends Module {
    public static Value modes = new Value("TP", "Mode", 0);
    public static int x;
    public static int y;
    public static int z;

    public TP2() {
        super("TP2", "TP2", Category.MOVEMENT);
        modes.addValue("SB");
        working = false;
    }

    @Override
    public String getDescription() {
        return "传送2!";
    }

    @EventTarget
    public void ontick(EventTick event) {
        PlayerCapabilities playerCapabilities = new PlayerCapabilities();
        playerCapabilities.isFlying = false;
        playerCapabilities.allowFlying = true;
        mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(0, (short) (-1), false));
        mc.getNetHandler().addToSendQueue(new C13PacketPlayerAbilities(playerCapabilities));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
        int i;
        //tp y
        for (double yPos = mc.thePlayer.posY; yPos < y - 3; yPos += 5) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, yPos, yPos, mc.thePlayer.posZ, true));
        }
        for (double yPos = mc.thePlayer.posY; yPos > y + 3; yPos -= 5) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, yPos, yPos, mc.thePlayer.posZ, true));
        }
        //tp x
        for (double xPos = mc.thePlayer.posX; xPos < x - 3; xPos += 5) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(xPos, mc.thePlayer.posY, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
        for (double xPos = mc.thePlayer.posX; xPos > x + 3; xPos -= 5) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(xPos, mc.thePlayer.posY, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
        //tp z
        for (double zPos = mc.thePlayer.posZ; zPos < z - 3; zPos += 5) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posY, zPos, true));
        }
        for (double zPos = mc.thePlayer.posZ; zPos > z + 3; zPos -= 5) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posY, zPos, true));
        }
        //tp
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x,y,y,z,true));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x,y-0.5,y-0.5,z,false));
        ChatUtil.sendClientMessage("传送到"+x+" "+y+" "+z);
        set(false);
    }
}
