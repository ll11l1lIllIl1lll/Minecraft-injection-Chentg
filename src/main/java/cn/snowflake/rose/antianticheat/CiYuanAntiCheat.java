package cn.snowflake.rose.antianticheat;

import cn.snowflake.rose.events.impl.EventFMLChannels;
import cn.snowflake.rose.utils.client.ChatUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CiYuanAntiCheat {


    public CiYuanAntiCheat(){
        EventManager.register(this);
    }

//    @EventTarget
//    public void onFml(EventFMLChannels eventFMLChannels) {
//
//            IMessage iMessage = eventFMLChannels.iMessage;
//            if (iMessage.getClass().toString().contains("ciyuanwutuobang"))
//            {
//                try {
//                    Constructor<?> constructor = iMessage.getClass().getConstructor(List.class,int.class);
//                    if (constructor != null)
//                    {
//                        eventFMLChannels.setCancelled(true);
//
//                        int salt = 0;
//                        List<String> list = new ArrayList();
//
//                        for (Field field : iMessage.getClass().getDeclaredFields())
//                        {
//                            field.setAccessible(true);
//                            try
//                            {
//                                if (field.getType().equals(List.class))
//                                {
//                                    list = (List<String>) field.get(iMessage);
//                                }
//                                else if (field.getType().equals(int.class))
//                                {
//                                    salt = field.getInt(iMessage);
//                                }
//                            }
//                            catch (Exception e)
//                            {
//                                ChatUtil.sendClientMessage(e.getMessage());
//                            }
//                        }
//
//                    }
//                } catch (NoSuchMethodException e) {
//
//                }
//                System.out.println("iMessage = " + Arrays.toString(iMessage.getClass().getConstructors()));
//            }
//
//    }

}
