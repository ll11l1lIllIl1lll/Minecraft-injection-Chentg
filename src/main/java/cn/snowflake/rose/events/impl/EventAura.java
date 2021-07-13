package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.EntityLivingBase;

public class EventAura implements Event{
    private  EntityLivingBase target;
    public EventAura(EntityLivingBase target) {
        this.target = target;
    }

    public EntityLivingBase getTarget() {
        return target;
    }
}