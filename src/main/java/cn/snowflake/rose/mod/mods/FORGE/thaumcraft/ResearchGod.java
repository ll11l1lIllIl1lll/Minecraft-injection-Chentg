package cn.snowflake.rose.mod.mods.FORGE.thaumcraft;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventTick;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.time.WaitTimer;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class ResearchGod extends Module {

    private CopyOnWriteArrayList<Packet> doResearchpacket = new CopyOnWriteArrayList<Packet>();
    private WaitTimer time = new WaitTimer();
    private WaitTimer findtimer = new WaitTimer();
    private int index;

    public Value<Double> bypass_aph_delay = new Value<Double>("ResearchGod_Bypass Delay", 130d, 0d, 500.0, 10);

    public Value<Boolean> Notes = new Value<Boolean> ("ResearchGod_Notes", false);

    public ResearchGod() {
        super("ResearchGod", "Research God", Category.FORGE);
        try {
            Class.forName("thaumcraft.api.research.ResearchCategories");
            Class.forName("thaumcraft.api.research.ResearchCategoryList");
            Class.forName("thaumcraft.api.research.ResearchItem");
            Class.forName("thaumcraft.common.lib.research.ResearchManager");
            Class.forName("thaumcraft.api.aspects.AspectList");
        } catch (Exception e) {
            setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "封包解锁神秘未解锁的笔记!"+getanothercommonbugfix();
    }


    public String getanothercommonbugfix() {
        if(Loader.isModLoaded("anothercommonbugfix")){
            return "(检测到通用修复)";
        }
        return "";
    }

    @Override
     public void onEnable() {
        index = 0;
        this.doResearchpacket.clear();
     }

    @EventTarget 
    public void onTicks(EventTick e) {
        if(doResearchpacket.isEmpty()) {
            try {
                Field f = Class.forName("thaumcraft.client.gui.GuiResearchPopup").getDeclaredField("theResearch");
                f.setAccessible(true);
                ((Collection) f.get(Class.forName("thaumcraft.client.lib.ClientTickEventsFML").getField("researchPopup").get(null))).clear();
                LinkedHashMap<String, Object> researchCategories = (LinkedHashMap<String, Object>) getPrivateValue("thaumcraft.api.research.ResearchCategories", "researchCategories", null);
                for (Object listObj : researchCategories.values()) {
                    Map<String, Object> research = (Map<String, Object>) getPrivateValue("thaumcraft.api.research.ResearchCategoryList", "research", listObj);
                    for (Object item : research.values()) {
                        String[] parents = (String[]) getPrivateValue("thaumcraft.api.research.ResearchItem", "parents", item);
                        String[] parentsHidden = (String[]) getPrivateValue("thaumcraft.api.research.ResearchItem", "parentsHidden", item);
                        String key = (String) getPrivateValue("thaumcraft.api.research.ResearchItem", "key", item);
                        if (!isResearchComplete(key)) {
                            boolean doIt = true;
                            if (parents != null) {
                                for (String parent : parents) {
                                    if (!isResearchComplete(parent)) {
                                        doIt = false;
                                        break;
                                    }
                                }
                            }
                            if (!doIt) {
                                continue;
                            }
                            if (parentsHidden != null) {
                                for (String parent : parentsHidden) {
                                    if (!isResearchComplete(parent)) {
                                        doIt = false;
                                        break;
                                    }
                                }
                            }
                            if (!doIt) {
                                continue;
                            }
                            for (Object aObj : getAspects(item)) {
                                if (aObj == null) {
                                    doIt = false;
                                    break;
                                }
                            }
                            if (!doIt) {
                                continue;
                            }
                            doResearch(key);
                        }
                    }
                }
                if(doResearchpacket.isEmpty()){
                    Client.instance.getNotificationManager().addNotification(this,"All studies have been completed !",2000, Notification.Type.WARNING);
                    set(false);
                    Client.instance.getNotificationManager().addNotification(this,"\247c OFF !",2000, Notification.Type.WARNING);
                }else {
                    Client.instance.getNotificationManager().addNotification(this,"Find \247c" + doResearchpacket.size() +"\247e not research complete ! Waiting %timer",2000, Notification.Type.WARNING);
                    findtimer.reset();

                }
            } catch (Exception ee) {
                
            }
        } else if(findtimer.hasTimeElapsed(2000,false)){
            int delay = bypass_aph_delay.getValueState().intValue();
            if (time.hasTimeElapsed(delay,true)){
                mc.thePlayer.sendQueue.addToSendQueue(doResearchpacket.get(index));
                C17PacketCustomPayload packet = (C17PacketCustomPayload) doResearchpacket.get(index);
                ByteBuf buffer = Unpooled.wrappedBuffer(packet.func_149558_e());
                byte bytes= buffer.readByte();
                String Key = ByteBufUtils.readUTF8String(buffer);
                int dim = buffer.readInt();
                String name= ByteBufUtils.readUTF8String(buffer);
                byte bytess = buffer.readByte();

                Client.instance.getNotificationManager().addNotification(this,"Do research \247cKey \2477: \247a"+ Key,delay, Notification.Type.SUCCESS);
                index++;
                if (doResearchpacket.size() <= index) {
                    index = 0;
                    this.doResearchpacket.clear();
                    Client.instance.getNotificationManager().addNotification(this,"Re check not research complete!",2000, Notification.Type.INFO);
//                set(false);
                }
            }
        }

    }
    
    
    private Object getPrivateValue(final String className, final String fieldName, final Object from) throws Exception {
        return ReflectionHelper.findField(Class.forName(className), new String[] { fieldName }).get(from);
    }

    private boolean isResearchComplete(String researchId) throws Exception {
        return (Boolean) Class.forName("thaumcraft.common.lib.research.ResearchManager").getMethod("isResearchComplete", String.class, String.class).invoke(null, mc.thePlayer.getCommandSenderName(), researchId);
    }

    private Object[] getAspects(Object item) throws Exception {
        Object aspectListObj = getPrivateValue("thaumcraft.api.research.ResearchItem", "tags", item);
        return (Object[]) Class.forName("thaumcraft.api.aspects.AspectList").getMethod("getAspects").invoke(aspectListObj);
    }

    private void doResearch(String researchId) {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(14);
        ByteBufUtils.writeUTF8String(buf, researchId);
        buf.writeInt(mc.thePlayer.dimension);
        ByteBufUtils.writeUTF8String(buf, mc.thePlayer.getCommandSenderName());
        buf.writeByte(Notes.getValueState() ? 1:0);

        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for (byte b : buf.array()) {
            bytes.add(b);
        }
        bytes.remove(63);

        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumcraft", this.toPrimitives(bytes.toArray(new Byte[0])));

//        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumcraft", buf);
        this.doResearchpacket.add(packet);
//        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }


    public byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; ++i) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }

}