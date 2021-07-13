package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Event;

public class EventRender2D implements Event {
    public float partialTicks;

    public EventRender2D(float partialTicks){
        this.partialTicks = partialTicks;
    }

    public EventRender2D(){
    }
//    public float getPartialTicks() {
//        return partialTicks;
//    }
}
