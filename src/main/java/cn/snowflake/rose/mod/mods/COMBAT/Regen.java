package cn.snowflake.rose.mod.mods.COMBAT;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.time.TimeHelper;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;


import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.apache.logging.log4j.LogManager;

public class Regen extends Module {
    public static Value<Double> slot = new Value("Regen_Slot", 9d, 1d, 9d, 1d);
    private Value<Double> eatdelay = new Value<Double>("Regen_EatDelay", 50.0, 0.0, 1000.0, 10.0);
    private Value packet = new Value("Regen_Packets", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(1000.0D), 1.0D);
    private TimeHelper delay = new TimeHelper();
    private Value<Double> regendelay = new Value("Regen_Delay", Double.valueOf(500.0D), Double.valueOf(0.0D), Double.valueOf(10000.0D), 100D);
    private TimeHelper useTimer = new TimeHelper();
    private Value<Boolean> eat = new Value<>("Regen_AutoEat",false);

    public Regen() {
        super("Regen","Regen", Category.COMBAT);
        setChinesename("\u5feb\u901f\u56de\u8840");
    }
    @Override
    public String getDescription() {
        return "快速回复!";
    }



    @EventTarget
    public void onMotion(EventMotion event) {
        if(delay.isDelayComplete(regendelay.getValueState().intValue())) {

            if(!ModManager.getModByName("Fly").isEnabled()) {
                if(!(this.mc.thePlayer.fallDistance > 2.0F)) {
                    if(this.mc.thePlayer.getHealth() < this.mc.thePlayer.getMaxHealth()) {

                        if(this.mc.thePlayer.onGround) {
                            if (this.mc.thePlayer.getFoodStats().getFoodLevel() >= 19){
                                for (int i = 0; (double) i < ((Double) this.packet.getValueState()).doubleValue(); ++i) {

                                    if (this.mc.thePlayer.onGround) {
                                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                                        this.setDisplayName("Health:" + this.mc.thePlayer.getHealth());
                                        delay.reset();
                                    } else {
                                        this.setDisplayName("OtherNoGround");
                                    }

                                }
                            }else if(this.mc.thePlayer.getFoodStats().getFoodLevel() < 18){
                                if (eat.getValueState()) {
                                    this.EatFood();
                                }
                                this.setDisplayName("FoodLevelLow");
                            }
                        }else {
                            this.setDisplayName("NoGround");
                        }

                    }else {
                        this.setDisplayName("MaxHealth");
                    }
                }else {
                    this.setDisplayName("Falling");
                }
            }else {
                this.setDisplayName("Flying");
            }
        }
    }


    private void EatFood() {
//        int slotId = this.getFreeSlot();
        int foodID = this.slot.getValueState().intValue();
//        if (slotId != -1) {
        if (foodID != -1 && this.useTimer.isDelayComplete(this.eatdelay.getValueState().longValue())) {
            int old = this.mc.thePlayer.inventory.currentItem;

            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(foodID - 1));

            this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(
                    -1,
                    -1,
                    -1,
                    -1,
                    this.mc.thePlayer.inventoryContainer.getSlot(foodID - 1).getStack(),
                    0.0f,
                    0.0f,
                    0.0f
            ));

            this.mc.thePlayer.inventory.currentItem = foodID - 1;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement());
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(old));
            this.mc.thePlayer.inventory.currentItem = old;
            this.useTimer.reset();
        }
//        }
    }

    private int getFreeSlot() {
        int id = 36;
        while (id < 45) {
            Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
            if (!currentSlot.getHasStack()) {
                return id;
            }
            ++id;
        }
        return -1;
    }

}
