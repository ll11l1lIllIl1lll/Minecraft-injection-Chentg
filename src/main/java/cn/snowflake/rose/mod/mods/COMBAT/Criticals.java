package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.events.impl.EventAura;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.time.TimeHelper;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;


import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
    int onGroundState;
    TimeHelper timer = new TimeHelper();
    public static Value modes = new Value("Criticals", "Mode", 0);

    public Criticals() {
        super("Criticals","Criticals", Category.COMBAT);
        modes.addValue("Packet");
        setChinesename("\u5200\u7206");
    }
    @Override
    public String getDescription() {
        return "刀刀暴击!";
    }
    public static  double CodeByLiquidSlowly(){
        // by liquidslowly
        double i = 0.0f;
        double o = 0.0f;
        double a = 0.0f;
        boolean p = false;
        for (o = 0.399f; 0.419f > o; o += 0.001) {
            for (o = 0.201f; 0.399f > o; o += 0.001) {
                p = false;
                if (o > 0.397f) {
                    for (i = 0.419; 0.399f < i; i -= 0.001) {
                        if (i < 0.4f) {
                            o = 0.001f;
                            p = false;
                        } else {
                            p = true;
                            o = 0.398f;
                        }
                        for (i = 0.399; 0.201f < i; i -= 0.001) {
                            if (i < 0.202f) {
                                o = 0.001f;
                                p = false;
                            } else {
                                p = true;
                                o = 0.398f;
                            }
                            a = (i + Math.random()) / 100;
                            return  a ;
                        }
                        a = i / 100;
                        return  a ;
                    }
                } else if (!p)
                    a = (o + Math.random()) / 100;
                return  a ;
            }
            if (!p)
                a = o  / 100;
            return  a ;
        }
        if(a != 0.0f){
            a = a;
        } else {
            a = 3.999999E-4 + (Math.random() / 110110);
        }
        return  a;
    }

    @EventTarget
    public void Eventaura(EventAura e) {
        if (modes.isCurrentMode("Packet")) {
            if (ModManager.getModByName("Aura").isEnabled() && Aura.target != null) {
                if (mc.thePlayer.onGround && !ModManager.getModByName("Fly").isEnabled()  && !ModManager.getModByName("Speed").isEnabled()) {
                    double[] offsets = {CodeByLiquidSlowly(),0.0};
                    int var6 = offsets.length;
                    for (int i = 0; i < offsets.length; i++) {
                        double v = offsets[i];
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX,this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY + v, this.mc.thePlayer.posZ, false));
                    }
                }
            }
        }
    }


    public void onEnable() {
    }

    public void onDisable() {
    }
}
