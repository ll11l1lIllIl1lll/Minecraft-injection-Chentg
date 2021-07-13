package cn.snowflake.rose.mod.mods.PLAYER;


import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.other.JReflectUtility;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class AutoTool extends Module {

    public AutoTool() {
        super("AutoTool","Auto Tool", Category.PLAYER);
        setChinesename("\u81ea\u52a8\u5de5\u5177");
    }

    @Override
    public String getDescription() {
        return "挖矿自动切换工具!";
    }

    @EventTarget
    public void onClickBlock(EventMotion e) {
        if (e.isPost()) {
            if (!mc.thePlayer.isEating() && JReflectUtility.getIsHittingBlock() && !(mc.objectMouseOver.blockX == 0 && mc.objectMouseOver.blockY == 0 && mc.objectMouseOver.blockZ == 0)) {
                this.bestTool(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
            }
        }
    }
    
    public void bestTool(int x, int y, int z) {
        int getIdFromBlock = Block.getIdFromBlock(mc.theWorld.getBlock(x, y, z));
        int currentItem = 0;
        float getStrVsBlock = -1.0f;
        for (int i = 36; i < 45; ++i) {
            try {
                ItemStack getStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if ((getStack.getItem() instanceof ItemTool || getStack.getItem() instanceof ItemSword || getStack.getItem() instanceof ItemShears) && getStack.func_150997_a(Block.getBlockById(getIdFromBlock)) > getStrVsBlock) {
                    currentItem = i - 36;
                    getStrVsBlock = getStack.func_150997_a(Block.getBlockById(getIdFromBlock));
                }
            }
            catch (Exception ex) {}
        }
        if (getStrVsBlock != -1.0f) {
            mc.thePlayer.inventory.currentItem = currentItem;
            mc.playerController.updateController();
        }
    }
}
