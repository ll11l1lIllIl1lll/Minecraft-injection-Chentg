package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

public class FTBSatchelDupe extends Module {
    public FTBSatchelDupe() {
        super("FTBSatchelDupe", "FTB Satchel Dupe", Category.FORGE);
        if (!Loader.isModLoaded("ThermalExpansion") || !Loader.isModLoaded("FTBL")){
            this.working = false;
        }
    }


    @Override
    public String getDescription() {
        return "把物品放入热力背包,按下小键盘0翻倍物品!";
    }

    private boolean prevState = false;

    @EventTarget
    public void onKey(EventKey ek) {
        if (ek.getKey() == Keyboard.KEY_NUMPAD0) {
            try {
                if (Class.forName("cofh.thermalexpansion.gui.container.ContainerSatchel").isInstance(mc.thePlayer.openContainer)) {
                    Class.forName("ftb.lib.api.net.MessageLM")
                            .getMethod("sendToServer")
                            .invoke(Class.forName("ftb.lib.mod.net.MessageClientItemAction")
                                    .getConstructor(String.class, NBTTagCompound.class)
                                    .newInstance("", new NBTTagCompound()));
                    int dropCount = (int) Class.forName("cofh.thermalexpansion.item.ItemSatchel")
                            .getMethod("getStorageIndex", ItemStack.class)
                            .invoke(null, mc.thePlayer.getCurrentEquippedItem());
                    for(int slot = mc.thePlayer.inventory.mainInventory.length;
                        slot < mc.thePlayer.inventory.mainInventory.length + dropCount * 9;
                        slot++
                    ) {
                        dropSlot(slot, true);
                    }
                    mc.thePlayer.closeScreen();
                    Client.instance.getNotificationManager().addNotification(this,"Dupe!", Notification.Type.SUCCESS);
                }
                    } catch (Exception e) {
                     }
            }
    }


    public void dropSlot(int slot, boolean allStacks) {
        clickSlot(slot, allStacks ? 1 : 0, 4);
    }

    public void swapSlot(int from, int to) {
        clickSlot(from, to, 2);
    }

    public void clickSlot(int slot, int shift, int action) {
        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, slot, shift, action, mc.thePlayer);
    }
}
