package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.time.TickTimer;
import com.darkmagician6.eventapi.EventTarget;

import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.events.impl.EventMove;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.PlayerUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import cn.snowflake.rose.utils.time.WaitTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

import java.util.Random;

public class Fly extends Module {
    public Value<Double> boost = new Value<Double>("Fly_MoitonBoost", 4.5, 1.0, 7.0, 0.1);
    public Value<String> mode = new Value("Fly_Mode", "Mode", 0);
    public Value<Boolean> antikick = new Value<>("Fly_AntiKick",true);



    public Fly() {
        super("Fly","Fly", Category.MOVEMENT);
        this.mode.addValue("Motion");
        this.mode.addValue("Vanilla");
        this.mode.addValue("Bypass");
        this.mode.addValue("Creative");
        setChinesename("\u98de\u884c");
    }
    public TickTimer ticktimer = new TickTimer();

    @Override
    public void onEnable() {

        super.onEnable();
    }

    @Override
    public String getDescription() {
        return "飞行!";
    }

       @EventTarget(4)
	   private void onUpdate(EventMotion e) {
            if (e.isPre()) {
                this.setDisplayName(this.mode.getModeName());
                if (this.mode.isCurrentMode("Vanilla")) {
                    mc.thePlayer.capabilities.isFlying = false;
                    mc.thePlayer.motionY = 0.0;
                    if (mc.gameSettings.keyBindForward.getIsKeyPressed()
                            || mc.gameSettings.keyBindLeft.getIsKeyPressed()
                            || mc.gameSettings.keyBindRight.getIsKeyPressed()
                            || mc.gameSettings.keyBindBack.getIsKeyPressed()) {
                        PlayerUtil.setSpeed(this.boost.getValueState());
                    }

                    if (mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                        --mc.thePlayer.motionY;
                    }
                    else if (mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                        ++mc.thePlayer.motionY;
                    }
                    if (antikick.getValueState() ) {
                        mc.thePlayer.posY -= 0.05d;
                    }
                }

                if (this.mode.isCurrentMode("Creative")){
                    mc.thePlayer.capabilities.isFlying = true;
                }

                if (this.mode.isCurrentMode("Motion")) {
                    double mspeed = Math.max((double) boost.getValueState(), getBaseMoveSpeed());
                    mc.thePlayer.motionY = 0.0D;
                    if (mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                        mc.thePlayer.motionY = mspeed * 0.6D;
                    }
                    if (mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                        mc.thePlayer.motionY = -mspeed * 0.6D;
                    }
                }

                if (this.mode.isCurrentMode("Bypass")){
                    mc.thePlayer.capabilities.isFlying = true;
                    JReflectUtility.setTimerSpeed(0.6f);
                    ticktimer.update();
                }


                if (antikick.getValueState()){
                    if(!mc.thePlayer.onGround){
                        mc.thePlayer.motionY -= 0.05;
                    }
                }
            }
	   }

	   @EventTarget(4)
	   public void onMove(EventMove e) {
           double speed = (double)boost.getValueState();
           if (e.entity == mc.thePlayer) {
		      if (this.mode.isCurrentMode("Motion") ){
                 Fly.setMoveSpeed(e, speed);
              }
		      if (this.mode.isCurrentMode("Bypass")){
                  final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                  if (mc.gameSettings.keyBindForward.getIsKeyPressed()){
                      if (ticktimer.hasTimePassed(2)) {
                          if (mc.gameSettings.keyBindForward.getIsKeyPressed()
                                  || mc.gameSettings.keyBindLeft.getIsKeyPressed()
                                  || mc.gameSettings.keyBindRight.getIsKeyPressed()
                                  || mc.gameSettings.keyBindBack.getIsKeyPressed()) {
                              e.setX(-Math.sin(yaw) * this.boost.getValueState());
                              e.setZ(Math.cos(yaw) * this.boost.getValueState());
                          }
                          if (mc.gameSettings.keyBindJump.getIsKeyPressed()){
                              e.setY(2d);
                          }
                          ticktimer.reset();
                      } else {
                          e.setX(-Math.sin(yaw) * 0.2D);
                          e.setZ(Math.cos(yaw) * 0.2D);
                      }
                  }
              }
		  }
	   }

	   public static void setMoveSpeed(EventMove event, double speed) {
		      double forward = (double)mc.thePlayer.moveForward;
		      double strafe = (double)mc.thePlayer.moveStrafing;
		      float yaw = mc.thePlayer.rotationYaw;
		      if (forward == 0.0D && strafe == 0.0D) {
		         event.setX(0.0D);
		         event.setZ(0.0D);
		      } else {
		         if (forward != 0.0D) {
		            if (strafe > 0.0D) {
		               yaw += (float)(forward > 0.0D ? -45 : 45);
		            } else if (strafe < 0.0D) {
		               yaw += (float)(forward > 0.0D ? 45 : -45);
		            }

		            strafe = 0.0D;
		            if (forward > 0.0D) {
		               forward = 1.0D;
		            } else if (forward < 0.0D) {
		               forward = -1.0D;
		            }
		         }

		         event.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
		         event.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
		      }

		   }
	   
	   public static double getBaseMoveSpeed() {
	      double baseSpeed = 0.2873D;
	      if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
	         int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
	         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
	      }

	      return baseSpeed;
	   }


    @Override
    public void onDisable() {
        if (!mode.isCurrentMode("Bypass")) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }else {
            mc.thePlayer.capabilities.isFlying = false;
        }
        JReflectUtility.setTimerSpeed(1f);
        if (this.mode.isCurrentMode("Creative")){
            mc.thePlayer.capabilities.isFlying = false;
        }
        super.onDisable();
    }
}
