package cn.snowflake.rose.mod.mods.FORGE.taintedmagic;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.other.JReflectUtility;
import com.darkmagician6.eventapi.EventTarget;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.util.Objects;


public class NoLimitAura extends Module {

    public Value<Double> AuraRadius = new Value<Double>("NoLimitAura_AuraRadius", 50.0D, 5.0D, 100.0D,5.0D);
    public Value<Boolean> leech = new Value("NoLimitAura_Leech", false);
    public Value<Boolean> players = new Value("NoLimitAura_Player", true);
    public Value<Boolean> otherentity = new Value("NoLimitAura_Otherentity", true);

    public NoLimitAura() {
        super("NoLimitAura", "NoLimit Aura", Category.FORGE);
        try {
            Class.forName("taintedmagic.common.network.PacketKatanaAttack");
        } catch (Exception var2) {
            this.setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "秒杀你范围内所有生物!!";
    }

    @EventTarget
    public void onUpdate(EventUpdate eu) {
        try {
            for (Object object : mc.theWorld.loadedEntityList) {
                Entity e = (Entity) object;
                if(mc.thePlayer.getDistanceToEntity(e) <= AuraRadius.getValueState().floatValue() && e != mc.thePlayer && !Objects.requireNonNull(JReflectUtility.getNPCEntity()).isInstance(e)){

                    if (e instanceof EntityLivingBase && !(e instanceof EntityPlayer) && otherentity.getValueState() && !players.getValueState()) {
                        killEntity(e.getEntityId());
                    }
                    if (e instanceof EntityPlayer && !otherentity.getValueState() && players.getValueState()) {
                        killEntity(e.getEntityId());
                    }
                    if ( e instanceof EntityLivingBase && otherentity.getValueState() && players.getValueState()) {
                        killEntity(e.getEntityId());
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void killEntity(int entityId) {
        int playerId = mc.thePlayer.getEntityId();
        int dimensionId = mc.thePlayer.dimension;
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(entityId);
        buf.writeInt(playerId);
        buf.writeInt(dimensionId);
        buf.writeFloat(Float.MAX_VALUE);
        buf.writeBoolean(leech.getValueState());
        C17PacketCustomPayload packet = new C17PacketCustomPayload("taintedmagic", buf);
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

}
