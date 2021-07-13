package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.events.impl.EventMove;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.PlayerUtil;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

import java.util.List;

public class Speed extends Module {
    public Value<Boolean> bypass = new Value<>("Speed_Bypass",true);
    public static Value<String> mode = new Value("Speed", "Mode", 0);
    public Value<Double> boost = new Value<Double>("Speed_MoitonBoost", 2.15d, 0d, 7.0, 0.01);
    public Value<Double> speedtimer = new Value<Double>("Speed_SpeedTimer", 0.2d, 0d, 1.0, 0.01);

    public boolean shouldslow = false;
    boolean collided;
    boolean lessSlow;
    private int stage;
    double less;
    double stair;
    public double speed;
	private double distance;
    TimeHelper timer = new TimeHelper();
    TimeHelper lastCheck = new TimeHelper();
    public int time = 0;

    public Speed() {
        super("Speed","Speed",  Category.MOVEMENT);
        mode.mode.add("Vanilla");
        mode.mode.add("New");
        mode.mode.add("Bhop");
        mode.addValue("Bhop1");
        mode.addValue("Bhop2");
        mode.addValue("YportNCP");
        mode.addValue("OnGround");
        setChinesename("\u53d8\u901f");
    }
    private int jumps;

    @Override
    public String getDescription() {
        return "加速!";
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        JReflectUtility.setjumpticks(4);
        if (mode.isCurrentMode("Bhop")){
            if(PlayerUtil.MovementInput()) {
                if(this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                }
                PlayerUtil.setSpeed(boost.getValueState());
            }
        }

    }
    @EventTarget
    public void motion(EventMotion eventMotion){
        if (eventMotion.isPre()){
    		double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
			double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
			this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
            if (mode.isCurrentMode("YportNCP")){
                if(mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() ||!PlayerUtil.isMoving() || mc.thePlayer.isInWater()){
                    return;
                }
                if(jumps >= 4 && mc.thePlayer.onGround)
                    jumps = 0;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = jumps <= 1 ? 0.42F : 0.4F;

                    float f = mc.thePlayer.rotationYaw * 0.017453292F;
                    mc.thePlayer.motionX -= MathHelper.sin(f) * 0.2F;
                    mc.thePlayer.motionZ += MathHelper.cos(f) * 0.2F;

                    jumps++;
                }else if(jumps <= 1)
                    mc.thePlayer.motionY = -5D;

                PlayerUtil.strafe();
            }

        }
    }

    @EventTarget
    public void OnMove(EventMove e) {
        if (e.getEntity() != mc.thePlayer){
            return;
        }

        this.setDisplayName(mode.getModeName());

        switch (mode.getModeName()) {
		case "Vanilla": {
		if (PlayerUtil.Moving()) {
			switch (this.stage) {

			case 2:
				if (mc.thePlayer.onGround&& mc.thePlayer.isCollidedVertically) {
					e.setY(mc.thePlayer.motionY = getJumpBoostModifier(0.42));
					double d = boost.getValueState().doubleValue() + 0.149;
					this.speed = this.defaultSpeed() * d;
				}
				break;
			case 3:
				this.speed = this.distance - 0.022 * (this.distance - this.defaultSpeed());
				break;
			default:
				 List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.getOffsetBoundingBox(0.0, this.mc.thePlayer.motionY, 0.0));
                 collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.getOffsetBoundingBox(0.0D, mc.thePlayer.motionY, 0.0D));
                 if ((collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) && stage > 0) {
                    stage = mc.thePlayer.movementInput.moveForward == 0.0F && mc.thePlayer.movementInput.moveStrafe == 0.0F ? 0 : 1;
                 }
//				if ((mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround)) {
//					this.stage = 1;
//				}
				this.speed = this.distance - this.distance / 159.0D;
			}
			
			this.speed = Math.max(this.speed, this.defaultSpeed());
			
			if ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
				setMotion(e, speed);
			}
			++this.stage;
			break;
				}
			}
            case "test": {
                switch (stage) {
                    case 0:
                        ++stage;
//                        distance = 0.0D;
                        speed = this.CustomSpeed();
                        break;
                    case 2:
                        distance = 0.0D;
                        if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically)) {
                                e.setY(mc.thePlayer.motionY = getJumpBoostModifier(0.42));
                                double d = 2.149;
                                speed = this.CustomSpeed() * d;

                        }
                        break;
                    case 3:
                        double boost = 0.22D;
                        speed = distance - boost * (distance - CustomSpeed());
                        break;
                    default:
                        ++stage;
                        if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.getOffsetBoundingBox(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0
                                || mc.thePlayer.isCollidedVertically) && stage > 0) {
                            stage = mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F ? 0 : 1;
                        }
                        speed = distance - distance / 159D;
                        break;
                }
                speed = Math.max(speed, CustomSpeed());
                if ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    setMotion(e, speed);
                }
                ++stage;
                break;
            }
		}
        final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        if (mode.isCurrentMode("OnGround")){
            if (bypass.getValueState()){
                JReflectUtility.setTimerSpeed(speedtimer.getValueState().floatValue());
            }
            if (mc.gameSettings.keyBindForward.getIsKeyPressed()
                    || mc.gameSettings.keyBindLeft.getIsKeyPressed()
                    || mc.gameSettings.keyBindRight.getIsKeyPressed()
                    || mc.gameSettings.keyBindBack.getIsKeyPressed()) {
                setMotion(e, boost.getValueState());
            }
        }

        if (this.mode.isCurrentMode("New")) {
            if (!mc.thePlayer.isInWater() && mc.thePlayer.onGround && this.isMoving2()) {
                if (this.stage >= 0) {
                    this.stage = 0;
//                    if (this.stair == 0.0) {
                        mc.thePlayer.jump();

                    e.setY(mc.thePlayer.motionY = getJumpBoostModifier(0.42));

                }
            }
            this.speed = this.getHypixelSpeed(this.stage) + 0.01 + Math.random() / 200.0;
            this.speed *= boost.getValueState().doubleValue();
//            if (this.stair > 0.0) {
//                this.speed *= 0.8 - (double)this.getSpeedEffect() * 0.1;
//            }
//            if (this.stage < 0) {
//                this.speed = defaultSpeed();
//            }
//            if (this.lessSlow) {
//                this.speed *= 1.05;
//            }
//            if (mc.thePlayer.isInWater()) {
//                this.speed = 0.12;
//            }
            if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
                this.setMotion(e, this.speed);
                ++this.stage;
            }
        } else if (this.mode.isCurrentMode("Bhop1")) {
            if (mc.thePlayer.isCollidedHorizontally) {
                this.collided = true;
            }
            if (this.collided) {
                this.stage = -1;
            }
            if (this.stair > 0.0) {
                this.stair -= 0.25;
            }
            this.less -= this.less > 1.0 ? 0.12 : 0.11;
            if (this.less < 0.0) {
                this.less = 0.0;
            }
            if (!mc.thePlayer.isInWater() && mc.thePlayer.onGround && this.isMoving2()) {
                this.collided = mc.thePlayer.isCollidedHorizontally;
                if (this.stage >= 0 || this.collided) {
                    this.stage = 0;
                    double a = 0.41999742;
                    double motY = a + (double)this.getJumpEffect() * 0.1;
                    if (this.stair == 0.0) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = motY;
                        e.setY(mc.thePlayer.motionY);
                    }
                    this.less += 1.0;
                    this.lessSlow = this.less > 1.0 && !this.lessSlow;
                    if (this.less > 1.12) {
                        this.less = 1.12;
                    }
                }
            }
            this.speed = this.getHypixelSpeed(this.stage) + 0.01 + Math.random() / 200.0;
            this.speed *= 02.97;
            if (this.stair > 0.0) {
                this.speed *= 0.8 - (double)this.getSpeedEffect() * 0.1;
            }
            if (this.stage < 0) {
                this.speed = defaultSpeed();
            }
            if (this.lessSlow) {
                this.speed *= 1.05;
            }
            if (mc.thePlayer.isInWater()) {
                this.speed = 0.12;
            }
            if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
                this.setMotion(e, this.speed);
                ++this.stage;
            }
        } else if (this.mode.isCurrentMode("Bhop2")) {
            if (mc.thePlayer.isCollidedHorizontally) {
                this.collided = true;
            }
            if (this.collided) {
                this.stage = -1;
            }
            if (this.stair > 0.0) {
                this.stair -= 0.25;
            }
            this.less -= this.less > 1.0 ? 0.12 : 0.11;
            if (this.less < 0.0) {
                this.less = 0.0;
            }
            if (!mc.thePlayer.isInWater() && mc.thePlayer.onGround && this.isMoving2()) {
                this.collided = mc.thePlayer.isCollidedHorizontally;
                if (this.stage >= 0 || this.collided) {
                    this.stage = 0;
                    double a = 0.41999742;
                    double motY = a + (double)this.getJumpEffect() * 0.1;
                    if (this.stair == 0.0) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = motY;
                        e.setY(mc.thePlayer.motionY);
                    }
                    this.less += 1.0;
                    this.lessSlow = this.less > 1.0 && !this.lessSlow;
                    if (this.less > 1.12) {
                        this.less = 1.12;
                    }
                }
            }
            this.speed = this.getHypixelSpeed(this.stage) + 0.01 + Math.random() / 500.0;
            this.speed *= 0.87;
            if (this.stair > 0.0) {
                this.speed *= 0.7 - (double)this.getSpeedEffect() * 0.1;
            }
            if (this.stage < 0) {
                this.speed = defaultSpeed();
            }
            if (this.lessSlow) {
                this.speed *= 0.95;
            }
            if (mc.thePlayer.isInWater()) {
                this.speed = 0.12;
            }
            if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
                this.setMotion(e, this.speed * 1.5d);
                ++this.stage;
            }
        }
        
    }


    private double getHypixelSpeed(int stage2) {
        double value = defaultSpeed() + 0.028 * (double)this.getSpeedEffect() + (double)this.getSpeedEffect() / 15.0;
        double firstvalue = 0.4145 + (double)this.getSpeedEffect() / 12.5;
        double thirdvalue = 0.3945 + (double)this.getSpeedEffect() / 12.5;
        double decr = (double)stage / 500.0 * 3.0;
        if (stage == 0) {
            if (this.timer.delay(300.0f)) {
                this.timer.reset();
            }
            if (!this.lastCheck.delay(500.0f)) {
                if (!this.shouldslow) {
                    this.shouldslow = true;
                }
            } else if (this.shouldslow) {
                this.shouldslow = false;
            }
            value = 0.64 + ((double)this.getSpeedEffect() + 0.028 * (double)this.getSpeedEffect()) * 0.134;
        } else if (stage == 1) {
            value = firstvalue;
        } else if (stage == 2) {
            value = thirdvalue;
        } else if (stage >= 3) {
            value = thirdvalue - decr;
        }
        if (this.shouldslow || !this.lastCheck.delay(500.0f) || this.collided) {
            value = 0.2;
            if (stage == 0) {
                value = 0.0;
            }
        }
        return Math.max(value, this.shouldslow ? value : defaultSpeed() + 0.028 * (double)this.getSpeedEffect());
    }

    public boolean isMoving2() {
        return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
    }

	   
    @Override
    public void onEnable() {
    	stage= 0;
    	distance = 0;
		speed = defaultSpeed();
        super.onEnable();
    }

    @Override
    public void onDisable() {
    	this.mc.thePlayer.motionX *= 1.0;
		this.mc.thePlayer.motionZ *= 1.0;
		JReflectUtility.setTimerSpeed(1f);
        super.onDisable();
    }

	public double getJumpBoostModifier(double baseJumpHeight) {
		if (mc.thePlayer.isPotionActive(Potion.jump)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
			baseJumpHeight += ((amplifier + 1) * 0.1F);
		}
		return baseJumpHeight;
	}
	
    public int getJumpEffect() {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }

    public int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public double CustomSpeed() {
        double baseSpeed = 0.94873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }


    public double defaultSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }


    private void setMotion(EventMove em, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            em.setX(0.0);
            em.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
}
