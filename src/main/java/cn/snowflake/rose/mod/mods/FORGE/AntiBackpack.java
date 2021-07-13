package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.events.impl.EventGuiOpen;
import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.events.impl.EventhandleWindowItem;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.ChatUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class AntiBackpack extends Module {

    public static boolean close = false;


    public AntiBackpack() {
        super("AntiBackpack", "Anti Backpack", Category.FORGE);

    }


    @Override
    public String getDescription() {
        return "\u597d\u5947\u754c\u5237\u7269\u54c1";
    }

    @EventTarget
    public void guiOpen(EventGuiOpen eventGuiOpen){
        if (eventGuiOpen.gui instanceof GuiContainer){
            GuiContainer container = (GuiContainer) eventGuiOpen.gui;
            if (container.inventorySlots.inventorySlots.size() == 45){
                if (!(eventGuiOpen.gui instanceof GuiInventory)){
                    eventGuiOpen.setCancelled(true);
                    AntiBackpack.close = true;
                }
            }
        }
    }
    @EventTarget
    public void packet(EventPacket eventPacket){
        if ( (eventPacket.getPacket() instanceof S30PacketWindowItems) || (eventPacket.getPacket() instanceof S2FPacketSetSlot)){
            eventPacket.setCancelled(true);
        }
    }

    @EventTarget
    public void packet(EventhandleWindowItem eventhandleWindowItem){
         eventhandleWindowItem.setCancelled(true);
    }

    @Override
    public void onDisable() {
        close = false;
        super.onDisable();
    }
}
