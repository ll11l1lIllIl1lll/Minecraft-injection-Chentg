package cn.snowflake.rose.utils.math;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;

public class PathUtils {

    public static List<Vector3d> findBlinkPath(final double tpX, final double tpY, final double tpZ) {
        final List<Vector3d> positions = new ArrayList<>();


        double curX = Minecraft.getMinecraft().thePlayer.posX;
        double curY = Minecraft.getMinecraft().thePlayer.posY;
        double curZ = Minecraft.getMinecraft().thePlayer.posZ;
        double distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);

        for (int count = 0; distance > 0.0D; count++) {
            distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);

            final double diffX = curX - tpX;
            final double diffY = curY - tpY;
            final double diffZ = curZ - tpZ;
            final double offset = (count & 1) == 0 ? 0.4D : 0.1D;

            final double minX = Math.min(Math.abs(diffX), offset);
            if (diffX < 0.0D) curX += minX;
            if (diffX > 0.0D) curX -= minX;

            final double minY = Math.min(Math.abs(diffY), 0.25D);
            if (diffY < 0.0D) curY += minY;
            if (diffY > 0.0D) curY -= minY;

            double minZ = Math.min(Math.abs(diffZ), offset);
            if (diffZ < 0.0D) curZ += minZ;
            if (diffZ > 0.0D) curZ -= minZ;

            positions.add(new Vector3d(curX, curY, curZ));
        }

        return positions;
    }

    public static List<Vector3d> findPath(final double tpX, final double tpY, final double tpZ, final double offset) {
        final List<Vector3d> positions = new ArrayList<>();
        final float yaw = (float) ((Math.atan2(tpZ - Minecraft.getMinecraft().thePlayer.posZ, tpX - Minecraft.getMinecraft().thePlayer.posX) * 180.0 / Math.PI) - 90F);
        final double steps = getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, tpX, tpY, tpZ) / offset;

        double curY = Minecraft.getMinecraft().thePlayer.posY;

        for(double d = offset; d < getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, tpX, tpY, tpZ); d += offset) {
            curY -= (Minecraft.getMinecraft().thePlayer.posY - tpY) / steps;
            positions.add(new Vector3d(Minecraft.getMinecraft().thePlayer.posX - (Math.sin(Math.toRadians(yaw)) * d), curY, Minecraft.getMinecraft().thePlayer.posZ + Math.cos(Math.toRadians(yaw)) * d));
        }

        positions.add(new Vector3d(tpX, tpY, tpZ));

        return positions;
    }

    private static double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double xDiff = x1 - x2;
        final double yDiff = y1 - y2;
        final double zDiff = z1 - z2;
        return MathHelper.sqrt_double(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
}
