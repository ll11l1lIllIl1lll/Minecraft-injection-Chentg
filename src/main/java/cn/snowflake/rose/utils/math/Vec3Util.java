package cn.snowflake.rose.utils.math;


import cn.snowflake.rose.utils.mcutil.BlockPos;
import net.minecraft.util.Vec3;

public class Vec3Util
{
    public double x;
    public double y;
    public double z;

    public Vec3Util(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3Util(BlockPos blockPos) {
        this.x = blockPos.x;
        this.y = blockPos.y;
        this.z = blockPos.z;
    }

    public Vec3Util(Vec3i directionVec) {
        this.x = directionVec.x;
        this.y = directionVec.y;
        this.z = directionVec.z;
    }


    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Vec3Util addVector(final double n, final double n2, final double n3) {
        return new Vec3Util(this.x + n, this.y + n2, this.z + n3);
    }

    public Vec3Util floor() {
        return new Vec3Util(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }

    public double squareDistanceTo(final Vec3Util class235) {
        return Math.pow(class235.x - this.x, 2.0) + Math.pow(class235.y - this.y, 2.0) + Math.pow(class235.z - this.z, 2.0);
    }

    public Vec3Util add(final Vec3Util class235) {
        return this.addVector(class235.getX(), class235.getY(), class235.getZ());
    }

    public Vec3 toVec3() {
        return Vec3.createVectorHelper(x,y,z);
    }


    @Override
    public String toString() {
        return "[" + this.x + ";" + this.y + ";" + this.z + "]";
    }

    public Vec3Util scale(double p_186678_1_) {
        return new Vec3Util(this.x * p_186678_1_, this.y * p_186678_1_, this.z * p_186678_1_);
    }
}
