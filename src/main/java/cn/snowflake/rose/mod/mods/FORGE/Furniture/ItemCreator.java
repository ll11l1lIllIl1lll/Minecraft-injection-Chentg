package cn.snowflake.rose.mod.mods.FORGE.Furniture;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.FORGE.NEISelect;
import cn.snowflake.rose.notification.Notification;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class ItemCreator extends Module {

    public ItemCreator(){
        super("ItemCreator","Item Creator", Category.FORGE);
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessagePackage");
        } catch (Exception ex) {
           setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "使用NEISelect储存要刷的物品后(按小键盘0刷取)!";
    }

    @EventTarget
    public void onKey(EventKey ek) {
        if (ek.getKey() == Keyboard.KEY_NUMPAD0) {
            if (NEISelect.STATIC_ITEMSTACK == null) {
                Client.instance.getNotificationManager().addNotification(this,"You have not selected an item!", Notification.Type.INFO);
                return;
            }
            giveItem(NEISelect.STATIC_ITEMSTACK);
            Client.instance.getNotificationManager().addNotification(this,"Gived!", Notification.Type.INFO);
        }
    }

    public void giveItem(ItemStack stack) {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(10);

        ItemStack mail = new ItemStack(Items.stick);
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < 6; i++) {
            NBTTagCompound item = new NBTTagCompound();
            item.setByte("Slot", (byte) i);
            stack.writeToNBT(item);
            tagList.appendTag(item);
        }

        NBTTagCompound inv = new NBTTagCompound();
        inv.setTag("Items", tagList);
        inv.setString("UniqueID", UUID.randomUUID().toString());
        mail.stackTagCompound = new NBTTagCompound();
        mail.stackTagCompound.setTag("Package", inv);
        ByteBufUtils.writeItemStack(buf, mail);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("cfm", buf);
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

}
