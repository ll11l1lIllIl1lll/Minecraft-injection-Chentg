package cn.snowflake.rose.mod.mods.WORLD;

import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

public class SecretClose extends Module {
    public SecretClose() {
        super("SecretClose", "Secret Close", Category.FORGE);
        setChinesename("\u79d8\u5bc6\u5173\u95ed");
    }

    @Override
    public String getDescription() {
        return "秘密关闭!";
    }
    @EventTarget
    public void onpacket(EventPacket ep){
        if (ep.getType() == EventType.SEND) {
            if (ep.getPacket() instanceof C0DPacketCloseWindow) {
                ep.setCancelled(true);
            }
        }
    }

}
