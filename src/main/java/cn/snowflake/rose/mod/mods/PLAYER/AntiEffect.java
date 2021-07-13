package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class AntiEffect extends Module {
    public AntiEffect() {
        super("AntiEffect", "Anti Effect", Category.PLAYER);
        setChinesename("\u53cd\u836f\u6c34");
    }
    private final Potion[] badEffects = new Potion[]{Potion.moveSlowdown, Potion.digSlowdown, Potion.harm, Potion.confusion, Potion.blindness, Potion.hunger, Potion.weakness, Potion.poison, Potion.wither};

    @Override
    public String getDescription() {
        return "移除负面药水!";
    }
    @EventTarget
    public void onUpdate(EventUpdate e){
        setDisplayName("Packet");
        if (mc.thePlayer.isPotionActive(Potion.blindness)) {
            mc.thePlayer.removePotionEffect(Potion.blindness.id);
        }
        if (mc.thePlayer.isPotionActive(Potion.confusion)) {
            mc.thePlayer.removePotionEffect(Potion.confusion.id);
        }
        if (mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
            mc.thePlayer.removePotionEffect(Potion.digSlowdown.id);
        }
        if (mc.thePlayer.onGround) {
            for (Potion effect : this.badEffects) {
                if (!mc.thePlayer.isPotionActive(effect)) {
                    continue;
                }
                for (int a2 = 0; a2 <= 20; ++a2) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                }
            }
            if (mc.thePlayer.getHealth() <= 15.0f && mc.thePlayer.isPotionActive(Potion.regeneration)) {
                for (int a3 = 0; a3 <= 10; ++a3) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                }
            }
        }
    }

}
