package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventLivingUpdate implements Event {
    public Entity entity;

    public EventLivingUpdate(Entity entity){
        this.entity = entity;
    }


    public Entity getEntity() {
        return entity;
    }
}
