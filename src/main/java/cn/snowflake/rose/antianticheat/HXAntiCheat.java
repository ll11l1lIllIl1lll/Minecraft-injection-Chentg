package cn.snowflake.rose.antianticheat;

import cn.snowflake.rose.events.impl.EventFMLChannels;
import cn.snowflake.rose.utils.antianticheat.HXAntiCheatHelper;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public class HXAntiCheat {

    public HXAntiCheat(){
        EventManager.register(this);
    }

    @EventTarget
    public void onFml(EventFMLChannels eventFMLChannels){
        //hx anticheat
        if (eventFMLChannels.fmlProxyPacket != null) {
            if (eventFMLChannels.fmlProxyPacket.channel().contains("HX:AntiCheat")) {
                eventFMLChannels.setCancelled(true);//À¹½Ø
                Object[] encrypt = HXAntiCheatHelper.getHXPacketData(this.getClass().getClassLoader());
                if (encrypt.length == 0) {
                    return;
                }
                ByteBuf data = HXAntiCheatHelper.writeData(
                        -1262767116,
                        (HXAntiCheatHelper.currentTimeMillistoHexString() + "$" + (new Gson().toJson(Arrays.asList(encrypt)))).getBytes());
                FMLProxyPacket fpp = new FMLProxyPacket(data, "HX:AntiCheat");
                eventFMLChannels.sendToServer(fpp);
            }
        }
    }

}
