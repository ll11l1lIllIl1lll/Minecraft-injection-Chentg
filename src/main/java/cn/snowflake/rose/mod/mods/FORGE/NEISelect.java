package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import cn.snowflake.rose.utils.client.ChatUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

public class NEISelect extends Module {
    public NEISelect() {
        super("NEISelect", "NEI Select", Category.FORGE);
        try {
            Class.forName("codechicken.nei.guihook.GuiContainerManager");
        } catch (Exception ex) {
            setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "从NEI中选择ItemStack储存(打开背包对着物品按TAB)!";
    }

    public static NBTTagCompound STATIC_NBT = new NBTTagCompound();
    public static ItemStack STATIC_ITEMSTACK = null;
    public static int STATIC_INT = 0;


    private boolean prevState = false;

    @EventTarget
    public void onTicks(EventUpdate eu) {
        boolean newState = Keyboard.isKeyDown(Keyboard.KEY_TAB);
        if (newState && !prevState) {
            prevState = newState;
            try {
                GuiContainer container = mc.currentScreen instanceof GuiContainer ? ((GuiContainer) mc.currentScreen) : null;
                if (container == null) {
                    return;
                }
                Object checkItem = Class.forName("codechicken.nei.guihook.GuiContainerManager")
                        .getDeclaredMethod("getStackMouseOver", GuiContainer.class)
                        .invoke(null, container);

                if (checkItem instanceof ItemStack) {
                    ItemStack item = (ItemStack) checkItem;
                    int count = GuiContainer.isShiftKeyDown() ? item.getMaxStackSize() : 1;
                    STATIC_ITEMSTACK = item.copy().splitStack(count);
                    STATIC_NBT = STATIC_ITEMSTACK.getTagCompound() == null ? new NBTTagCompound() : STATIC_ITEMSTACK.getTagCompound();
                }
                Client.instance.getNotificationManager().addNotification(this,"ItemStack selected!", Notification.Type.SUCCESS);
            } catch (Exception ignored) {

            }
        }
        prevState = newState;
    }

}
