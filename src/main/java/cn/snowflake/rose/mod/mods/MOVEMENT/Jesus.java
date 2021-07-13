package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;


import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class Jesus extends Module {
    
    public Jesus() {
        super("Jesus","Jesus",  Category.MOVEMENT);
        setChinesename("\u6c34\u4e0a\u884c\u8d70");
    }

    @Override
    public String getDescription() {
        return "水上行走!";
    }

    public static boolean jesus;

    @Override
    public void set(boolean state) {
        if (state){
            jesus = true;
        }else{
            jesus = false;
        }
        super.set(state);
    }

    @EventTarget
    public void onUpdatePre(EventMotion event) {
        if (event.isPre()){
            if (isInLiquid()){
                mc.thePlayer.motionY = 0.1;
            }
        }
    }
    public static boolean isInLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return false;
        }
        boolean inLiquid = false;
        final int y = (int)(mc.thePlayer.boundingBox.minY + 0.02);
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlock(x,y,z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
}