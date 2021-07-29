package cn.snowflake.rose.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;


import java.util.ArrayList;

public class PlayerUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
            return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }
    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

	 public static boolean Moving() {
	        if (PlayerUtil.mc.gameSettings.keyBindForward.getIsKeyPressed()) return true;
	        if (PlayerUtil.mc.gameSettings.keyBindBack.getIsKeyPressed()) return true;
	        if (PlayerUtil.mc.gameSettings.keyBindLeft.getIsKeyPressed()) return true;
	        if (PlayerUtil.mc.gameSettings.keyBindRight.getIsKeyPressed()) return true;
	        if (PlayerUtil.mc.thePlayer.movementInput.moveForward != 0.0f) return true;
	        if (PlayerUtil.mc.thePlayer.movementInput.moveStrafe != 0.0f) return true;
	        return false;
	    }
    
    public static boolean MovementInput() {
        return PlayerUtil.mc.gameSettings.keyBindForward.getIsKeyPressed() || PlayerUtil.mc.gameSettings.keyBindLeft.getIsKeyPressed() || PlayerUtil.mc.gameSettings.keyBindRight.getIsKeyPressed() || PlayerUtil.mc.gameSettings.keyBindBack.getIsKeyPressed();
    }

    public static String getDisplayName(ItemStack itemstack) {
        String s = itemstack.getItem().getItemStackDisplayName(itemstack);
        if (itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("display", 10)) {
            NBTTagCompound nbttagcompound = itemstack.stackTagCompound.getCompoundTag("display");
            if (nbttagcompound.hasKey("Name", 8)) {
                s = nbttagcompound.getString("Name");
            }
        }
        return s;
    }


    public static void setSpeed(double speed) {
        PlayerUtil.mc.thePlayer.motionX = - Math.sin((double)PlayerUtil.getDirection()) * speed;
        PlayerUtil.mc.thePlayer.motionZ = Math.cos((double)PlayerUtil.getDirection()) * speed;
    }

    public static float getDirection() {
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float forward = Minecraft.getMinecraft().thePlayer.moveForward;
        float strafe = Minecraft.getMinecraft().thePlayer.moveStrafing;
        yaw += (forward < 0.0F ? 180 : 0);
        if (strafe < 0.0F) {
            yaw += (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }
        if (strafe > 0.0F) {
            yaw -= (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }
        return yaw * 0.017453292F;
    }
    public static float getSpeed() {
        return (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }
    public static void strafe() {
        strafe(getSpeed());
    }
    public static void strafe(final float speed) {
        if(!isMoving())
            return;

        final double yaw = getDirection();
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }
}
