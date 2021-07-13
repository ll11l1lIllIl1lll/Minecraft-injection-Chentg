package cn.snowflake.rose.mod.mods.FORGE.thaumcraft;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventTick;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.render.MouseInputHandler;
import cn.snowflake.rose.utils.time.WaitTimer;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MovingObjectPosition;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AspectGod extends Module {

    public static Value<String> mode = new Value("AspectGod", "Bypass Mode", 0);

    public Value<Double> bypass_aph_delay = new Value<Double>("AspectGod_Bypass Delay", 130d, 0d, 500.0, 10);
    public Value<Double> Aspect_Number = new Value<Double>("AspectGod_Aspect Number", 3d, 3d, 10000.0, 1);
    public Value<Double> Packet_Number = new Value<Double>("AspectGod_Packet Shooting", 1d, 1d, 999.0, 1);
    public Value<Boolean> target = new Value("AspectGod_MCT", false);

    public AspectGod() {
        super("AspectGod", "Aspect God", Category.FORGE);
        mode.mode.add("Vanilla");
        mode.mode.add("ACBF");
        try {
            Class.forName("thaumcraft.api.aspects.Aspect");
            Class.forName("thaumcraft.common.Thaumcraft");
            if(Loader.isModLoaded("anothercommonbugfix")){ mode.setCurrentMode(1); }
        } catch (Exception ex) {
            setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "封包解锁神秘所有要素!"+getACBF();
    }

    public String getACBF() {
        if(Loader.isModLoaded("anothercommonbugfix")){
            return "(检测到通用修复ACBF)";
        }
        return "";
    }

    @Override
     public void onEnable() {
        if (mode.isCurrentMode("ACBF")) {
            ChatUtil.sendClientMessage("请打开研究太台开始自动获取要素!!!");
        }
        index=0;
        this.aspectspacket.clear();
     }


    @Override
    public void onDisable() {
        index=0;
        this.aspectspacket.clear();
    }

    private CopyOnWriteArrayList<Packet> aspectspacket = new CopyOnWriteArrayList<Packet>();
    private WaitTimer time = new WaitTimer();
    private int index;

    private final MouseInputHandler handler = new MouseInputHandler(2);
    private Entity entity ;
     @EventTarget
     public void onTick(EventTick et){
         if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
             if(handler.canExcecute()) {
                 entity = mc.objectMouseOver.entityHit;
                 String name = this.mc.objectMouseOver.entityHit.getCommandSenderName();
                 Client.instance.getNotificationManager().addNotification(this, "Target : \247c" + name, Notification.Type.SUCCESS);
             }
         }
         try {
             boolean ResearchTable = Class.forName("thaumcraft.common.container.ContainerResearchTable").isInstance(mc.thePlayer.openContainer);
             long delay = bypass_aph_delay.getValueState().longValue();
             if (mode.isCurrentMode("ACBF")) {
                 if (time.hasTimeElapsed(delay, true) && ResearchTable) {
                     if (aspectspacket.isEmpty()){
                         try {
                             if(Auto_close()){
                                 Client.instance.getNotificationManager().addNotification(this,"\247c OFF !",2000, Notification.Type.WARNING);
                                 set(false);
                             }
                             ArrayList aspects = (ArrayList) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getCompoundAspects").invoke(null);
                             Collections.reverse(aspects);
                             for (Object aspect : aspects) {
                                 if(checkaspectnumber(aspect))continue;
                                 Object aspect1 = ((Object[]) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[0];
                                 Object aspect2 = ((Object[]) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[1];
                                 String a1 = (String) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(aspect1);
                                 String a2 = (String) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(aspect2);
                                 doGive(a1, a2);
                             }
                         } catch (Exception ignored) {
                         }
                     }else {
                         Client.instance.getNotificationManager().addNotification(this, "Aspect Unlock " +index+ " !",(int)delay, Notification.Type.SUCCESS);
                         for (int i = 0; i < (1 * Packet_Number.getValueState()); i++) {
                             mc.thePlayer.sendQueue.addToSendQueue(aspectspacket.get(index));
                         }
                         index++;
                         if (aspectspacket.size() <= index) {
                             index = 0;
                             this.aspectspacket.clear();
                             if(!Auto_close()){
                                 Client.instance.getNotificationManager().addNotification(this, "Repeat get aspect!", Notification.Type.SUCCESS);
                             }else {
                                 Client.instance.getNotificationManager().addNotification(this,"\247c OFF !",2000, Notification.Type.WARNING);
                                 set(false);
                             }
                         }
                     }

                 }
             } else if (mode.isCurrentMode("Vanilla")){
                 if (aspectspacket.isEmpty()){
                     try {
                         if(Auto_close()){
                             Client.instance.getNotificationManager().addNotification(this, "AspectGod Auto Close!", Notification.Type.SUCCESS);
                             set(false);
                         }
                         ArrayList aspects = (ArrayList) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getCompoundAspects").invoke(null);
                         Collections.reverse(aspects);
                         for (Object aspect : aspects) {
                             if(checkaspectnumber(aspect))continue;
                             Object aspect1 = ((Object[]) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[0];
                             Object aspect2 = ((Object[]) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[1];
                             String a1 = (String) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(aspect1);
                             String a2 = (String) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(aspect2);
                             doGive(a1, a2);
                         }
                     } catch (Exception ignored) {
                     }
                 }else {
                     if (time.hasTimeElapsed(delay, true)) {
                         Client.instance.getNotificationManager().addNotification(this, "Aspect Unlock " +index+ " !",(int)delay, Notification.Type.SUCCESS);
                         for (int i = 0; i < (1 * Packet_Number.getValueState()); i++) {
                             mc.thePlayer.sendQueue.addToSendQueue(aspectspacket.get(index));
                         }
                         index++;
                         if (aspectspacket.size() <= index) {
                             index = 0;
                             this.aspectspacket.clear();
                             if(!Auto_close()){
                                 Client.instance.getNotificationManager().addNotification(this, "Repeat get aspect!", Notification.Type.SUCCESS);
                             }else {
                                 Client.instance.getNotificationManager().addNotification(this,"\247c OFF !",2000, Notification.Type.WARNING);
                                 set(false);
                             }
                         }
                     }
                 }
             }
         } catch (ClassNotFoundException e) {
             Client.instance.getNotificationManager().addNotification(this,"Check ContainerResearchTable Error!", Notification.Type.SUCCESS);
             set(false);
         }

     }

     private boolean Auto_close(){
         try {
             ArrayList aspects = (ArrayList) Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getCompoundAspects").invoke(null);
             Collections.reverse(aspects);
             for (Object aspect : aspects) {
                 if(checkaspectnumber(aspect))continue;
                 return false;
             }
         } catch (Exception ignored) {
         }
         return true;
     }


    private boolean checkaspectnumber(Object aspect){
        try {
            Class<?> thaumcraft = Class.forName("thaumcraft.common.Thaumcraft");
            Object proxy = thaumcraft.getField("proxy").get(null);
            Class<?> commonProxy = Class.forName("thaumcraft.common.CommonProxy");
            Object knowledge = commonProxy.getMethod("getPlayerKnowledge").invoke(proxy);
            Class<?> playerknowledge = Class.forName("thaumcraft.common.lib.research.PlayerKnowledge");
            Object aspetList = playerknowledge.getMethod("getAspectsDiscovered",String.class).invoke(knowledge,mc.thePlayer.getCommandSenderName());
            int aspectNumber = (int) Class.forName("thaumcraft.api.aspects.AspectList")
                    .getMethod("getAmount", Class.forName("thaumcraft.api.aspects.Aspect"))
                    .invoke(aspetList,aspect);;
            if(aspectNumber >= Aspect_Number.getValueState().intValue() && !target.getValueState()){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    private void doGive(String a1, String a2) {
        MovingObjectPosition entity = mc.objectMouseOver;
        int playerId = mc.thePlayer.getEntityId();
        int dimensionId = mc.thePlayer.dimension;
        if(target.getValueState() && this.entity != null){
             playerId = this.entity.getEntityId();
        }
        BlockPos block = entity != null ? new BlockPos(entity.blockX,entity.blockY,entity.blockZ) : new BlockPos(0,0,0);
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(13);
        buf.writeInt(dimensionId);
        buf.writeInt(playerId);
        buf.writeInt(block.getX());
        buf.writeInt(block.getY());
        buf.writeInt(block.getZ());
        ByteBufUtils.writeUTF8String(buf, a1);
        ByteBufUtils.writeUTF8String(buf, a2);
        buf.writeBoolean(true);
        buf.writeBoolean(true);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumcraft", buf);
        this.aspectspacket.add(packet);
//        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
    
}