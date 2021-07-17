package cn.snowflake.rose.mod.mods.WORLD;


import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.WORLD.irc.core.IRC;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.concurrent.CopyOnWriteArrayList;

public class FakeLag extends Module
{
    Value<Double> lagValue;
    CopyOnWriteArrayList<C03PacketPlayer> packetList;
    TimeHelper lagHelper;
    C03PacketPlayer lastPacket;

    public FakeLag() {
        super("FakeLag", "Fake Lag", Category.COMBAT);
        this.lagValue = new Value<Double>("FakeLag_Delay", 3000.0, 300.0, 5000.0);
        this.packetList = new CopyOnWriteArrayList<C03PacketPlayer>();
        this.lagHelper = new TimeHelper();
        if (!IRC.beta){
            working = false;
        }
    }

    @Override
    public String getDescription() {
        return "假身 ";
    }

    @EventTarget
    public void onPacket(EventPacket eventPacket) {
        if (eventPacket.getType() == EventType.SEND) {
            Packet packet = eventPacket.getPacket();
            if (packet instanceof C03PacketPlayer) {
                C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) packet;
                if (this.packetList.contains(c03PacketPlayer)) {
                    this.packetList.remove(c03PacketPlayer);
                } else {
                    this.packetList.add(c03PacketPlayer);
                    eventPacket.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (this.lagHelper.isDelayComplete(this.lagValue.getValueState().longValue())) {
            if (this.mc.theWorld.getEntityByID(-1) != null) {
            }
            for (C03PacketPlayer c03PacketPlayer : this.packetList) {
                this.mc.thePlayer.sendQueue.addToSendQueue(c03PacketPlayer);
                this.lastPacket = c03PacketPlayer;
            }
            if (this.lastPacket != null && this.mc.gameSettings.thirdPersonView != 0) {
                setDisplayName("lagging");
            }else {
                setDisplayName("");
            }
            this.lagHelper.reset();
        }
    }
}

