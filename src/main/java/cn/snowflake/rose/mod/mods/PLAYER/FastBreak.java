package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.events.impl.EventPlayerDamageBlock;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;


public class FastBreak extends Module {
    public static Value mode = new Value("FastBreak", "Mode", 0);
    public static Value<Double> speed = new Value<Double>("FastBreak_Damage",0.8,0.5,0.8,0.1);
    public static Value<Double> Pspeed = new Value<Double>("FastBreak_PacketSpeed",1.6,1.0,3.0,0.1);

    private boolean bzs = false;
    private float bzx = 0.0f;
    public BlockPos blockPos;
    public int facing;

    public FastBreak() {
        super("FastBreak","Fast Break", Category.PLAYER);
        mode.mode.add("Vanilla");
        mode.mode.add("Packet");
        setChinesename("\u5feb\u901f\u7834\u574f");
    }

    @Override
    public String getDescription() {
        return "快速挖掘!";
    }

    @EventTarget
    public void onDamageBlock(EventPacket event) {
        if (mode.isCurrentMode("Packet")) {
            if (event.packet instanceof C07PacketPlayerDigging && !mc.playerController.extendedReach()
                    && mc.playerController != null) {
                C07PacketPlayerDigging packet = (C07PacketPlayerDigging) event.packet;
                if (packet.func_149506_g() == 0) {
                    this.bzs = true;
                    this.blockPos = new BlockPos(packet.func_149505_c(),packet.func_149503_d(),packet.func_149502_e());
                    this.facing = packet.func_149501_f();
                    this.bzx = 0.0f;
                } else if (packet.func_149506_g() == 1
                        || packet.func_149506_g() == 2) {
                    this.bzs = false;
                    this.blockPos = null;
                    this.facing = 0;
                }
            }
        }
    }

    @EventTarget
    public void OnUpdate(EventPlayerDamageBlock e) {
        if (mode.isCurrentMode("Packet")) {
            if (mc.playerController.extendedReach()) {
                JReflectUtility.setBlockHitDelay(0);
            } else if (this.bzs) {
                Block block = this.mc.theWorld.getBlock(this.blockPos.getX(),this.blockPos.getY(),this.blockPos.getZ());
                this.bzx += (float) ((double) block.getPlayerRelativeBlockHardness(mc.thePlayer, this.mc.theWorld,
                        this.blockPos.getX(),this.blockPos.getY(),this.blockPos.getZ()) * Pspeed.getValueState().floatValue());
                if (this.bzx >= 1.0f) {
                    this.mc.theWorld.setBlockToAir(this.blockPos.getX(),this.blockPos.getY(),this.blockPos.getZ());
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, this.blockPos.getX(),this.blockPos.getY(),this.blockPos.getZ(), this.facing));
                    this.bzx = 0.0f;
                    this.bzs = false;
                }
            }
        }
    }

    @EventTarget
    public void OnUpdata(EventUpdate e) {
        if (mode.isCurrentMode("Packet")) {
            this.setDisplayName("Packet");
        }else {
            this.setDisplayName("Vanilla");
        }

        JReflectUtility.setBlockHitDelay(0);
        if (mode.isCurrentMode("Vanilla")) {
            if (JReflectUtility.getCurBlockDamageMP() >= speed.getValueState().floatValue()) {
                JReflectUtility.setCurBlockDamageMP(1);
                boolean item = mc.thePlayer.getCurrentEquippedItem() == null;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 20, item ? 1 : 0));
            }
        }
    }
    public void onDisable() {
        mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }
}

