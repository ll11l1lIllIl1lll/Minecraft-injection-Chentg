package cn.snowflake.rose.antianticheat;

import cn.snowflake.rose.events.impl.EventFMLChannels;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AnotherAntiCheat {

    public AnotherAntiCheat(){
        EventManager.register(this);
    }

    @EventTarget
    public void onFml(EventFMLChannels eventFMLChannels){
//        try {
//            if (eventFMLChannels.iMessage.toString().contains("AntiCheatSTCPacketMessage")){
////                eventFMLChannels.setCancelled(true);//À¹½Ø
////                Constructor constructor = eventFMLChannels.iMessage.getClass().getConstructor(byte[][].class);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}
