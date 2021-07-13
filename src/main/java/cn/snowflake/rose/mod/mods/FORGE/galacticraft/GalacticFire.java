package cn.snowflake.rose.mod.mods.FORGE.galacticraft;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import java.util.stream.Stream;

public class GalacticFire extends Module {


    public Value<Double> FireRadius = new Value<Double>("GalacticFire_FireRadius", 50.0D, 5.0D, 100.0D,5.0D);

    public Value<Boolean> players = new Value("GalacticFire_Player", true);
    public Value<Boolean> otherentity = new Value("GalacticFire_Otherentity", true);

    public GalacticFire() {
        super("GalacticFire", "Galactic Fire", Category.FORGE);
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
        } catch (Exception e) {
            working = false;
        }
    }

    @Override
    public String getDescription() {
        return "让你周围的玩家全身着火!";
    }


    private void sendFirePacket(Entity e) {
        if (e != null) {
            sendPacket("GalacticraftCore", (byte) 0, 7, e.getEntityId());
        }
    }

    @EventTarget
    public void onUpdata(EventUpdate eu) {
        try {
            for (Object object : mc.theWorld.loadedEntityList) {
                Entity e = (Entity) object;
                if(mc.thePlayer.getDistanceToEntity(e) <= FireRadius.getValueState().floatValue() && e != mc.thePlayer){
                    if (e instanceof EntityLivingBase && !(e instanceof  EntityPlayer) && otherentity.getValueState() && !players.getValueState()) {
                        sendFirePacket(e);
                    }
                    if (e instanceof EntityPlayer && !otherentity.getValueState() && players.getValueState()) {
                        sendFirePacket(e);
                    }
                    if (e instanceof EntityLivingBase && otherentity.getValueState() && players.getValueState()) {
                        sendFirePacket(e);
                    }
                }
            }
//            List<EntityPlayer> players = mc.theWorld.playerEntities;
//            players.forEach((o) -> {
//                spinEntity(((Entity) o).getEntityId());
//            });
        } catch (Exception e) {
        }

//        nearEntityes().filter(ent -> ent instanceof EntityLivingBase).forEach(this::sendFirePacket);

    }
    public Stream<Entity> nearEntityes() {
        return nearEntityes(FireRadius.getValueState().intValue());
    }

    public Stream<Entity> nearEntityes(int radius) {
        return mc.theWorld.loadedEntityList.stream().filter(e -> mc.thePlayer.getDistanceToEntity((Entity) e) <= radius && e != mc.thePlayer
        && (e instanceof EntityPlayer && !players.getValueState())
                        && (e instanceof EntityLivingBase && !otherentity.getValueState())
        );
    }
    public void sendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public void sendPacket(String channel, ByteBuf data) {
        sendPacket(new FMLProxyPacket(data, channel));
    }

    public void sendPacket(String channel, Object ... data) {
        sendPacket(channel, bufWriter(data));
    }

    public ByteBuf bufWriter(Object ... data) {
        return bufWriter(Unpooled.buffer(0), data);
    }

    public ByteBuf bufWriter(ByteBuf buf, Object ... data) {
        for (Object o : data) {
            if (o instanceof Integer) {
                buf.writeInt((Integer)o);
            } else if (o instanceof Byte) {
                buf.writeByte((Byte)o);
            } else if (o instanceof Short) {
                buf.writeShort((Short)o);
            } else if (o instanceof Float) {
                buf.writeFloat((Float)o);
            } else if (o instanceof String) {
                ByteBufUtils.writeUTF8String(buf, (String)o);
            } else if (o instanceof ItemStack) {
                ByteBufUtils.writeItemStack(buf, (ItemStack)o);
            } else if (o instanceof NBTTagCompound) {
                ByteBufUtils.writeTag(buf, (NBTTagCompound)o);
            } else if (o instanceof ByteBuf) {
                buf.writeBytes((ByteBuf)o);
            } else if (o instanceof Double) {
                buf.writeDouble((Double)o);
            } else if (o instanceof Boolean) {
                buf.writeBoolean((Boolean)o);
            } else if (o instanceof byte[]) {
                buf.writeBytes((byte[])o);
            } else if (o instanceof Long) {
                buf.writeLong((Long)o);
            } else if (o instanceof int[]) {
                for (int i : (int[]) o) {
                    bufWriter(buf, i);
                }
            }
        }
        return buf;
    }
}
