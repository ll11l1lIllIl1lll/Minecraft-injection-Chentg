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
    
    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + (double)(ent.getEyeHeight() / 2.0F);
        return getRotationFromPosition(x, z, y);
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var6;
        double var4 = target.posX - mc.thePlayer.posX;
        double var8 = target.posZ - mc.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase)target;
            var6 = var10.posY + (double)var10.getEyeHeight() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.boundingBox.minY + target.boundingBox.maxY) / 2.0 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)((- Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14)) * 180.0 / 3.141592653589793);
        float pitch = RotationUtil.changeRotation(mc.thePlayer.rotationPitch, var13, p_706253);
        float yaw = RotationUtil.changeRotation(mc.thePlayer.rotationYaw, var12, p_706252);
        return new float[]{yaw, pitch};
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

    public static float[] getBowAngles(final Entity entity) {
        final double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
        final double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
        double d = Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(entity);
        d -= d % 0.8;
        double xMulti = 1.0;
        double zMulti = 1.0;
        final boolean sprint = entity.isSprinting();
        xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
        zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
        final double x = entity.posX + xMulti - Minecraft.getMinecraft().thePlayer.posX;
        final double z = entity.posZ + zMulti - Minecraft.getMinecraft().thePlayer.posZ;
        final double y = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight());
        final double dist = Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(entity);
        final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        double d1 = MathHelper.sqrt_double(x * x + z * z);
        final float pitch =  (float) - (Math.atan2(y, d1) * 180.0D / Math.PI) + (float)dist*0.11f;

        return new float[]{yaw, -pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle = Math.abs(angle1 - angle2) % 360.0F;
        if (angle > 180.0F) {
            angle = 360.0F - angle;
        }
        return angle;
    }

    public static float[] teleportRot(Entity entity, Entity target, float p_706252, float p_706253) {
        double y;
        double xDist = target.posX - entity.posX;
        double zDist = target.posZ - entity.posZ;
        if (target instanceof EntityLivingBase) {
            y = target.posY + (double)target.getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
        } else {
            y = (target.boundingBox.minY + target.boundingBox.maxY) / 2.0 - (entity.posY + (double)entity.getEyeHeight());
        }
        double distance = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
        float realYaw = (float)(Math.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        float realPitch = (float)(- Math.atan2(y - (target instanceof EntityPlayer ? 0.25 : 0.0), distance) * 180.0 / 3.141592653589793);
        float yaw = RotationUtil.changeRotation(entity.rotationYaw, realYaw, p_706252);
        float pitch = RotationUtil.changeRotation(entity.rotationPitch, realPitch, p_706253);
        return new float[] {yaw, pitch};
    }
}
