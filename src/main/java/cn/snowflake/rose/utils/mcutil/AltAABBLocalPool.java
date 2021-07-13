package cn.snowflake.rose.utils.mcutil;

import net.minecraft.util.AxisAlignedBB;

public final class AltAABBLocalPool extends ThreadLocal
{
    protected AxisAlignedBB createNewDefaultPool() {
        return AxisAlignedBB.getBoundingBox(300.0, 2000.0, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    protected Object initialValue() {
        return this.createNewDefaultPool();
    }
}
