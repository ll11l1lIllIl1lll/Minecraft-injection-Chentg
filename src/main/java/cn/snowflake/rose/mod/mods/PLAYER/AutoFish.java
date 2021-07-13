package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.server.S29PacketSoundEffect;


public class AutoFish extends Module {
    public AutoFish() {
        super("AutoFish","Auto Fish", Category.PLAYER);
        setChinesename("\u81ea\u52a8\u9493\u9c7c");
    }

    @Override
    public String getDescription() {
        return "自动钓鱼!";
    }

    @EventTarget
    public void onPacket(EventPacket e){
        S29PacketSoundEffect packet = (S29PacketSoundEffect) e.getPacket();
        if(packet.func_149212_c().contains("random.splash")) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1500);
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                        return;
                    }catch (Exception e) {}
                }
            }.start();
        }
    }

}
