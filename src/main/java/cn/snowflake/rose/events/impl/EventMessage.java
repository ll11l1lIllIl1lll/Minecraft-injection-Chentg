package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Event;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class EventMessage implements Event {
    public Object simpleChannelHandlerWrapper;
    private IMessage imessage;
    public EventMessage(Object simpleChannelHandlerWrapper,Object imessage){
        this.simpleChannelHandlerWrapper = simpleChannelHandlerWrapper;
        this.imessage = (IMessage) imessage;
    }

    public IMessage getImessage() {
        return imessage;
    }


}
