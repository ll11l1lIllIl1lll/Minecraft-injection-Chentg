package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class OneWayTicket extends Module {
    public OneWayTicket() {
        super("OneWayTicket", "One Way Ticket", Category.FORGE);
        if (!Loader.isModLoaded("Railcraft")){
            working = false;
        }
    }

    @Override
    public void onEnable() {
        ItemStack ckeckItem = mc.thePlayer.getCurrentEquippedItem();
        try {
            if (Class.forName("mods.railcraft.common.util.network.IEditableItem").isInstance(ckeckItem.getItem())) {
                NBTTagCompound nbt = NEISelect.STATIC_NBT;
                if (!nbt.hasNoTags()) {
                    Class.forName("mods.railcraft.common.util.network.PacketDispatcher")
                            .getMethod("sendToServer", Class.forName("mods.railcraft.common.util.network.RailcraftPacket"))
                            .invoke(null, Class.forName("mods.railcraft.common.util.network.PacketCurrentItemNBT")
                            .getConstructor(EntityPlayer.class, ItemStack.class).newInstance(mc.thePlayer, item(ckeckItem, nbt)));
                }
            }
        } catch(Exception e) {

        }
        super.onEnable();
    }
    public ItemStack item(ItemStack toItem, NBTTagCompound tag) {
        if (toItem != null && tag != null) {
            toItem.setTagCompound(collideNbt(toItem.hasTagCompound() ? toItem.stackTagCompound : new NBTTagCompound(), tag));
        }
        return toItem;
    }
    public NBTTagCompound collideNbt(NBTTagCompound toCollide, NBTTagCompound in) {
        Map outMap = nbtMap(toCollide);
        outMap.putAll(nbtMap(in));
        setNbtMap(toCollide, outMap);
        return toCollide;
    }
    public Map<String, NBTBase> nbtMap(NBTTagCompound tag){
        return ReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, 1);
    }
    public void setNbtMap(NBTTagCompound tag, Map map) {
        ReflectionHelper.setPrivateValue(NBTTagCompound.class, tag, map, 1);
    }

}
