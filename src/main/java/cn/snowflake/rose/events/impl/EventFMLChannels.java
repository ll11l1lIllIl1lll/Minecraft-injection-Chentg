package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Event;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelFutureListener;

import java.util.EnumMap;

/**
 *
 * Author : CNSnowFlake
 *
 */
public class EventFMLChannels implements Event {
    public IMessage iMessage;
    public EnumMap<Side, FMLEmbeddedChannel> channels;
    private boolean cancelled;

    public FMLProxyPacket fmlProxyPacket;

    public EventFMLChannels(Object object, EnumMap channels ){
        if (object instanceof IMessage){
            this.iMessage = (IMessage) object;
        }
        if (object instanceof FMLProxyPacket){
            this.fmlProxyPacket = (FMLProxyPacket) object;
        }
        this.channels = channels;
    }

    public EventFMLChannels(EnumMap channels){
        this.channels = channels;
    }




    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    /**
     *
     * @return shit
     */
    public EnumMap<Side, FMLEmbeddedChannel> getChannels() {
        return channels;
    }

    public void sendToServer(IMessage message)
    {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public void sendToServer(FMLProxyPacket pkt) {
        ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        ((FMLEmbeddedChannel)this.channels.get(Side.CLIENT)).writeAndFlush(pkt).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

}
