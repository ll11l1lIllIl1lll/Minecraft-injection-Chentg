package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.PlayerUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Blink extends Module {
    TimeHelper time = new TimeHelper();
    private CopyOnWriteArrayList packets = new CopyOnWriteArrayList();
    private ArrayList positions = new ArrayList();

    public Blink() {
        super("Blink","Blink", Category.PLAYER);
        setChinesename("\u5206\u8eab\u77ac\u79fb");
    }

    @Override
    public String getDescription() {
        return "分身瞬移!";
    }

    public void onEnable() {
        if (this.mc.thePlayer != null && this.mc.theWorld != null) {
            double x = this.mc.thePlayer.posX;
            double y = this.mc.thePlayer.posY;
            double z = this.mc.thePlayer.posZ;
            float yaw = this.mc.thePlayer.rotationYaw;
            float pitch = this.mc.thePlayer.rotationPitch;
            EntityOtherPlayerMP e = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile());
            e.inventory = this.mc.thePlayer.inventory;
            e.inventoryContainer = this.mc.thePlayer.inventoryContainer;
            e.setPositionAndRotation(x, y - 1.5d, z, yaw, pitch);
            e.rotationYawHead = this.mc.thePlayer.rotationYawHead;
            this.mc.theWorld.addEntityToWorld(-1, e);
        }

        this.packets.clear();
        this.positions.clear();
        super.onEnable();
    }

    public void onDisable() {
        this.mc.theWorld.removeEntityFromWorld(-1);
        Iterator var2 = this.packets.iterator();

        while(var2.hasNext()) {
            Packet packet = (Packet)var2.next();
            this.mc.thePlayer.sendQueue.addToSendQueue(packet);
        }

        this.packets.clear();
        super.onDisable();
    }

    @EventTarget
    public void onSendPacket(EventPacket event) {
        if (event.getType() == EventType.SEND) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                this.packets.add(event.getPacket());
                event.setCancelled(true);
            } else if (event.getPacket() instanceof C08PacketPlayerBlockPlacement || event.getPacket() instanceof C07PacketPlayerDigging || event.getPacket() instanceof C09PacketHeldItemChange || event.getPacket() instanceof C02PacketUseEntity) {
                this.packets.add(event.getPacket());
                event.setCancelled(true);
            }
        }else if (event.getType() == EventType.RECIEVE){
            if (event.getPacket() instanceof S08PacketPlayerPosLook && Client.canCancle) {
                event.setCancelled(true);
            }
        }
    }

    private void addPosition() {
        double x = this.mc.thePlayer.posX;
        double y = this.mc.thePlayer.posY;
        double z = this.mc.thePlayer.posZ;
        Vector3f vec = new Vector3f((float)x, (float)y, (float)z);
        if (PlayerUtil.MovementInput() || this.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
            this.positions.add(vec);
        }

    }
}