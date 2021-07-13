package cn.snowflake.rose.mod.mods.FORGE.galacticraft;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.util.Random;

public class NoLimitSpin extends Module {

    public Value<String> YawMode = new Value<String>("NoLimitSpin","YawMode", 0);
    public Value<String> PitchMode = new Value<String>("NoLimitSpin","PitchMode", 0);

    public Value<Double> CustomYaw= new Value<Double>("NoLimitSpin_CustomYaw", 180.0D, 0.0D, 360.0D,5.0D);
    public Value<Double> CustomPitch= new Value<Double>("NoLimitSpin_CustomPitch", -180.0D, -180.0D, 180.0D,5.0D);

    public Value<Double> SpinRadius = new Value<Double>("NoLimitSpin_SpinRadius", 50.0D, 5.0D, 100.0D,5.0D);

    public Value<Boolean> players = new Value("NoLimitSpin_Player", true);
    public Value<Boolean> otherentity = new Value("NoLimitSpin_Otherentity", true);


    public Random R = new Random();

    public NoLimitSpin(){
        super("NoLimitSpin", "NoLimit Spin", Category.FORGE);
        this.YawMode.addValue("Random");
        this.YawMode.addValue("Custom");
        this.PitchMode.addValue("Random");
        this.PitchMode.addValue("Custom");
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
        } catch (Exception e) {
            working = false;
        }
    }

    @Override
    public String getDescription() {
        return "让你周围的玩家身体乱转(鬼畜)!";
    }

    @EventTarget
    public void onTicks(EventUpdate et) {
        try {
            for (Object object : mc.theWorld.loadedEntityList) {
                Entity e = (Entity) object;
                if(mc.thePlayer.getDistanceToEntity(e) <= SpinRadius.getValueState().floatValue() && e != mc.thePlayer){
                    if (e instanceof EntityLivingBase && !(e instanceof EntityPlayer) && otherentity.getValueState() && !players.getValueState()) {
                        spinEntity(e.getEntityId());
                    }
                    if (e instanceof EntityPlayer && !otherentity.getValueState() && players.getValueState()) {
                        spinEntity(e.getEntityId());
                    }
                    if (e instanceof EntityLivingBase && otherentity.getValueState() && players.getValueState()) {
                        spinEntity(e.getEntityId());
                    }
                }
            }
//            List<EntityPlayer> players = mc.theWorld.playerEntities;
//            players.forEach((o) -> {
//                spinEntity(((Entity) o).getEntityId());
//            });
        } catch (Exception e) {
        }
    }

    public void spinEntity(int entityId) {
        float yaw = YawMode.isCurrentMode("Random") ? (R.nextFloat() * 360):CustomYaw.getValueState().floatValue();
        float pitch = PitchMode.isCurrentMode("Random") ? (R.nextFloat() * 180f - 90f):CustomPitch.getValueState().floatValue();
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(1);
        buf.writeInt(entityId);
        buf.writeFloat(pitch);
        buf.writeFloat(yaw);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("GalacticraftCore", buf);
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }


}
