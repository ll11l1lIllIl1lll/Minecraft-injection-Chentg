package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.*;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.PlayerUtil;
import com.darkmagician6.eventapi.EventTarget;

import com.darkmagician6.eventapi.types.EventType;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.util.UUID;

public class Freecam extends Module {
    private double posX;
    private double posY;
    private double posZ;
    private float rotYaw;
    private float rotPitch;

    public Freecam() {
        super("Freecam","Freecam", Category.PLAYER);
        setChinesename("\u7075\u9b42\u51fa\u7a8d");
    }

    @Override
    public String getDescription() {
        return "灵魂出窍!";
    }

    public EntityOtherPlayerMP oldentity;
    @Override
    public void onEnable() {
        if (this.mc.theWorld != null) {

            this.posX = this.mc.thePlayer.posX;
            this.posY = this.mc.thePlayer.posY;
            this.posZ = this.mc.thePlayer.posZ;
            this.rotYaw = this.mc.thePlayer.rotationYaw;
            this.rotPitch = this.mc.thePlayer.rotationPitch;
            this.mc.thePlayer.noClip = true;

            this.oldentity = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(new UUID(69L, 96L), "123"));
            this.oldentity.inventory = mc.thePlayer.inventory;
            this.oldentity.inventoryContainer = mc.thePlayer.inventoryContainer;
            this.oldentity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY - 1.5d, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            this.oldentity.rotationYawHead = mc.thePlayer.rotationYawHead;
            this.oldentity.setHealth(19);
            mc.theWorld.addEntityToWorld(this.oldentity.getEntityId(), this.oldentity);

        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.noClip = false;

        this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = 0.0D;
        this.mc.thePlayer.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotYaw, this.rotPitch);
        if (this.oldentity != null){
            mc.theWorld.removeEntityFromWorld(this.oldentity.getEntityId());
        }

        super.onDisable();
    }



    @EventTarget
    public void onMove(EventMove event) {
        this.mc.thePlayer.noClip = true;
    }

    @EventTarget
    public void onUpdate(EventMotion event) {
        this.mc.thePlayer.motionY = 0.0D;
        if (PlayerUtil.MovementInput()) {
            PlayerUtil.setSpeed(1.0D);
        } else {
            this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = 0.0D;
        }

        if (this.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
            this.mc.thePlayer.motionY -= 0.5D;
        } else if (this.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
            this.mc.thePlayer.motionY += 0.5D;
        }

    }

    @EventTarget
    public void pushOut(EventPushOut event) {
        event.cancel = true;
    }

    @EventTarget
    public void insideBlock(EventInsideBlock event) {
        event.cancel = true;
    }


    @EventTarget
    public void onPacketSend(EventPacket event) {
        if (event.getType() == EventType.SEND ) {
//            if (event.getPacket() instanceof C03PacketPlayer){
//                C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
//                JReflectUtility.setField(packet.getClass(),packet,"field_149479_a",this.posX);
//                JReflectUtility.setField(packet.getClass(),packet,"field_149475_d",this.posY);
//                JReflectUtility.setField(packet.getClass(),packet,"field_149478_c",this.posZ);
//                JReflectUtility.setField(packet.getClass(),packet,"field_149473_f",this.rotPitch);
//                JReflectUtility.setField(packet.getClass(),packet,"field_149476_e",this.rotYaw);
//            }else
            if (event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook){
                event.setCancelled(true);
            }else if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition){
                event.setCancelled(true);
            }else if (event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook){
                event.setCancelled(true);
            }else if (event.getPacket() instanceof C03PacketPlayer){
                event.setCancelled(true);
            }

            //packet.field_149479_a = this.posX;
            //packet.field_149475_d = this.posY;
            //packet.field_149478_c = this.posZ;
            //packet.field_149476_e = this.rotYaw;
            //packet.field_149473_f = this.rotPitch;
        }
        if (event.getType() == EventType.RECIEVE && event.getPacket() instanceof S08PacketPlayerPosLook & Client.canCancle) {
            event.setCancelled(true);
        }

    }
}
