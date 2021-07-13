package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.types.EventType;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class EventFromByte implements Event {
    public ByteBuf byteBuf;
    public IMessage iMessage;
    private EventType eventType;

    public EventFromByte(ByteBuf byteBuf, IMessage iMessage,EventType eventType){
        this.byteBuf = byteBuf;
        this.iMessage = iMessage;
        this.eventType = eventType;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public IMessage getMessage() {
        return iMessage;
    }

    public EventType getEventType() {
        return eventType;
    }
}
