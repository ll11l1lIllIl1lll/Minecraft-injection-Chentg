package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.entity.Entity;

public class EventMove implements Event
{
    public double x;
    public double y;
    public double z;
    private boolean onground;
    public Entity entity;

    public EventMove(Object entity,final double a, final double b, final double c) {
        this.entity = (Entity) entity;
        this.x = a;
        this.y = b;
        this.z = c;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getX() {
        return this.x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public void setGround(boolean ground) {
        this.onground = ground;
    }
    public void setOnGround(boolean onground) {
        this.onground = onground;
    }
}
