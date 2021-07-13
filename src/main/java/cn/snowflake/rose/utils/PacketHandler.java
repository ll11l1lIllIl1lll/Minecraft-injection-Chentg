package cn.snowflake.rose.utils;

import cn.snowflake.rose.mod.mods.FORGE.ScreenProtect;
import cn.snowflake.rose.utils.asm.Mappings;
import com.darkmagician6.eventapi.EventManager;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.channel.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.injection.ClientLoader;
import net.minecraft.network.NetworkManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PacketHandler extends ChannelDuplexHandler
{
    public static String AntiCatAntiCheatScreenShot = "";

    public PacketHandler()
    {
        try
        {
            NetworkManager networkManager = Minecraft.getMinecraft().getNetHandler().getNetworkManager();
            Field field = networkManager.getClass().getDeclaredField(Mappings.channel);
            field.setAccessible(true);
            ChannelPipeline pipeline = ((Channel)field.get(networkManager)).pipeline();;
            pipeline.addBefore("packet_handler", "PacketHandler", this);
        }catch (Exception e){}
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!onPacket(packet, Side.IN)) return;
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!onPacket(packet, Side.OUT)) return;
        super.write(ctx, packet, promise);
    }

    public enum Side
    {
        IN,
        OUT
    }

    public boolean onPacket(Object packet, PacketHandler.Side side) {
            boolean suc = true;
            if (ScreenProtect.mode2.isCurrentMode("close")){

                if(packet instanceof FMLProxyPacket) {
                    //ÈÆ¹ýDecimationf½ØÍ¼Azrael
                    if (side == Side.IN && ((FMLProxyPacket) packet).channel().equalsIgnoreCase("ctx")) {
                        if (((FMLProxyPacket) packet).payload().array().length < 3) {
                            String str = "";
                            for (byte b : ((FMLProxyPacket) packet).payload().array()) {
                                str += b;
                            }
                            if (str.contains("51")) {
                                EventManager.close = true;
                                Minecraft.getMinecraft().thePlayer.removePotionEffect(16);
                                Minecraft.getMinecraft().displayGuiScreen(new GuiScreen());
                                Minecraft.getMinecraft().thePlayer.removePotionEffect(3);
                                Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
                                try {
                                    Thread.sleep(1400);
                                } catch (Exception e1) {
                                }
                                new Thread() {
                                    public void run() {
                                        try {
                                            sleep(3000);
                                            EventManager.close = false;

                                            return;
                                        } catch (Exception e) {
                                        }
                                    }

                                    ;
                                }.start();
                            }
                        }
                    }
                }
            }
            return suc;
        }
    }