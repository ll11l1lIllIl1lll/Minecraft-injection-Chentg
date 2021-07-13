package cn.snowflake.rose.utils.client;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
public class BlockUtils {

    static Minecraft mc = Minecraft.getMinecraft();

    public static boolean collideBlock(AxisAlignedBB axisAlignedBB) {
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlock(x, (int) (axisAlignedBB.minY), z);
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
            }

        }
        return true;
    }
}
