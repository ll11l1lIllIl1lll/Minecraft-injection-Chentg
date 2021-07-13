package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import com.darkmagician6.eventapi.EventTarget;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.lwjgl.input.Keyboard;

public class MagicRecipe extends Module {
    public MagicRecipe() {
        super("MagicRecipe", "Magic Recipe", Category.FORGE);
        try {
            Class.forName("com.anotherera.magicrecipe.common.network.packet.MinecraftRecipeChangePacket");
        }catch (Throwable ex) {
            working = false;
        }
    }

    @Override
    public String getDescription() {
        return "另一个合成修改(打开工作台按0)!";
    }

    @EventTarget
    public void onKey(EventKey ek) {
        if (ek.getKey() == Keyboard.KEY_NUMPAD0) {
            if (mc.thePlayer.openContainer instanceof ContainerWorkbench) {
                ByteBuf buf = Unpooled.buffer();
                buf.writeBoolean(true);
                buf.writeBoolean(true);
                mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("anothermagicrecipe", buf));
                Client.instance.getNotificationManager().addNotification(this,"SendOP!", Notification.Type.SUCCESS);
            }
            set(false);
        }
    }
}
