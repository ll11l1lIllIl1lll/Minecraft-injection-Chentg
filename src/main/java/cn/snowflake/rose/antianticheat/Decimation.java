package cn.snowflake.rose.antianticheat;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventFMLChannels;
import cn.snowflake.rose.events.impl.EventMessage;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.mods.FORGE.ScreenProtect;
import cn.snowflake.rose.utils.PacketHandler;
import cn.snowflake.rose.utils.antianticheat.ScreenshotUtil;
import cn.snowflake.rose.utils.client.ChatUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Decimation {
    Minecraft mc = Minecraft.getMinecraft();

    public Decimation(){
        EventManager.register(this);
    }


        @EventTarget
        public void onFml(EventFMLChannels eventFMLChannels) {
            if (!ScreenProtect.mode2.isCurrentMode("close")){
                    if (Client.deci_new ? eventFMLChannels.iMessage.toString().contains("deci.aE.a$af") : //true
                            eventFMLChannels.iMessage.toString().contains("deci.aE.a$ab") //false
                    ) {
                    Constructor<?> constructor = null;
                    try {
                       constructor = eventFMLChannels.iMessage.getClass().getConstructor(int.class,int.class,ByteBuf.class);

                       Field fceiling_float_int =
                               Client.deci_new ? eventFMLChannels.iMessage.getClass().getDeclaredField("ayR")
                               :
                               eventFMLChannels.iMessage.getClass().getDeclaredField("axM")
                               ;

                       Field fi = Client.deci_new ? eventFMLChannels.iMessage.getClass().getDeclaredField("ayQ") : eventFMLChannels.iMessage.getClass().getDeclaredField("axL");

                       Field fbytebuf = Client.deci_new ? eventFMLChannels.iMessage.getClass().getDeclaredField("ayZ") : eventFMLChannels.iMessage.getClass().getDeclaredField("axU");

                       fceiling_float_int.setAccessible(true);
                       fi.setAccessible(true);
                       fbytebuf.setAccessible(true);


                       int max = fceiling_float_int.getInt(eventFMLChannels.iMessage);
                       int step = fi.getInt(eventFMLChannels.iMessage);

                       if (max != step){



                           eventFMLChannels.setCancelled(true);

                           if ((max - 1) == step){
                               //图片数据
                               BufferedImage bufferedImage = null;
                               if (ScreenProtect.mode2.isCurrentMode("Custom")){
                                   if (ScreenshotUtil.bufferedImage != null){
                                       bufferedImage = ScreenshotUtil.bufferedImage;//从计算机中获取图片
                                   }else {
                                       ScreenshotUtil.bufferedImage = ScreenshotUtil.customScreenshot();
                                       bufferedImage = ScreenshotUtil.bufferedImage;
                                   }
                               }

                               ByteBuf buffer = Unpooled.buffer();
                               if (bufferedImage.getData().getDataBuffer().getSize() / 1024 < 5000) {
                                   ImageIO.write(bufferedImage, "PNG", new ByteBufOutputStream(buffer));
                                   for (int ceiling_float_int = MathHelper.ceiling_float_int(buffer.readableBytes() / 32763.0f), i = 0; i < ceiling_float_int; ++i) {
                                       int n = i * 32763;
                                       eventFMLChannels.sendToServer((IMessage) constructor.newInstance(
                                               ceiling_float_int,
                                               i,
                                               buffer.slice(n, Math.min(buffer.readableBytes() - n, 32763)) )
                                       );
                                   }
                               }

                           }

                       }
                   } catch (Exception e) {
                        ChatUtil.sendClientMessage(e.getMessage());
                   }
                }
            }
        }

        @EventTarget
        public void inServer(EventUpdate eventUpdate){

            if (Minecraft.getMinecraft().theWorld != null){
                new PacketHandler();
            }

        }

}
