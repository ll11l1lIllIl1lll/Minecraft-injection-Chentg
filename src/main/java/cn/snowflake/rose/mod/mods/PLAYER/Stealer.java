package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.time.TimeHelper;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

import java.util.Random;

public class Stealer extends Module {
    public Stealer() {
        super("ChestStealer","Chest Stealer", Category.PLAYER);
        setChinesename("\u81ea\u52a8\u62ff\u7269\u54c1");
    }
    public Value<Double> delay = new Value("ChestStealer_Delay", 65d, 0d, 200d, 1d);
    public Value<Boolean> cchest = new Value("ChestStealer_CloseChest", true);
    TimeHelper time = new TimeHelper();

    @Override
    public String getDescription() {
        return "自动拿物品!";
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName("D :"+ delay.getValueState().doubleValue());
        Container container = mc.thePlayer.openContainer;
        if (container != null && container instanceof ContainerChest) {
            ContainerChest containerchest = (ContainerChest)container;
            for (int i = 0; i < containerchest.getLowerChestInventory().getSizeInventory(); ++i) {
                if (containerchest.getLowerChestInventory().getStackInSlot(i) != null && this.time.delay((float)this.delay.getValueState().doubleValue())) {
                    if(new Random().nextInt(100) > 80) {
                        continue;
                    }
//                    String GuiName = containerchest.getLowerChestInventory().getInventoryName();
//                    String[] LIST = new String[] {"器","游戏菜单","空岛","我","收藏品","大厅","职业","等级","井","神","礼","商店","确认","参加"};
//                    for(String str : LIST) {
//                        if(GuiName.contains(str))
//                            return;
//                    }
                    mc.playerController.windowClick(containerchest.windowId, i, 0, 1, mc.thePlayer);
                    this.time.reset();
                }
            }
            if (this.isContainerEmpty(container) && this.cchest.getValueState().booleanValue()) {
                mc.thePlayer.closeScreen();
            }
        }
    }

    private boolean isContainerEmpty(Container container) {
        boolean flag = true;
        int i = 0;
        for (int j = container.inventorySlots.size() == 90 ? 54 : 27; i < j; ++i) {
            if (container.getSlot(i).getHasStack()) {
                flag = false;
            }
        }
        return flag;
    }

}
