package cn.snowflake.rose.utils.client;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class RotationUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    // ex
    public static boolean canEntityBeSeen(Entity e){
        Vec3 vec1 = Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),mc.thePlayer.posZ);

        AxisAlignedBB box = e.boundingBox;
        Vec3 vec2 = Vec3.createVectorHelper(e.posX, e.posY + (e.getEyeHeight()/1.32F),e.posZ);
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY) ;
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see =  mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(maxx,miny,minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(minx,miny,minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;

        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(minx,miny,maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(maxx,miny,maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;

        vec2 = Vec3.createVectorHelper(maxx, maxy,minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;

        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(minx, maxy,minz);

        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(minx, maxy,maxz - 0.1);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;
        vec2 = Vec3.createVectorHelper(maxx, maxy,maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
        if(see)
            return true;


        return false;
    }




    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < - p_706633) {
            var4 = - p_706633;
        }
        return p_706631 + var4;
    }
    public static float[] getPlayerRotations(Entity player, double x, double y, double z) {
        double deltaX = x - player.posX;
        double deltaY = y - player.posY - player.getEyeHeight() - 0.1;
        double deltaZ = z - player.posZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        yawToEntity = wrapAngleTo180((float)yawToEntity);
        pitchToEntity = wrapAngleTo180((float)pitchToEntity);
        return new float[] { (float)yawToEntity, (float)pitchToEntity };
    }

    private static float wrapAngleTo180(float angle) {
        for (angle %= 360.0f; angle >= 180.0f; angle -= 360.0f) {}
        while (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f;
        return getRotationFromPosition(x, z, y);
    }
	public static boolean isVisibleFOV(EntityLivingBase e, float fov) {
		return (Math.abs(getRotations(e)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0F > 180.0F
				? 360.0F - Math.abs(getRotations(e)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0F
				: Math.abs(getRotations(e)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0F) <= fov;
	}
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }

    public static float[] getRotationsNeededBlock(double x, double y, double z) {
        double diffX = x + 0.5D - mc.thePlayer.posX;
        double diffZ = z + 0.5D - mc.thePlayer.posZ;
        double diffY = y + 0.5D - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
        return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
    }


}
