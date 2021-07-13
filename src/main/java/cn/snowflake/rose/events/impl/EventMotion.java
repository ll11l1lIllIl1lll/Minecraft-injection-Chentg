package cn.snowflake.rose.events.impl;


import com.darkmagician6.eventapi.types.EventType;
import com.darkmagician6.eventapi.events.Cancellable;
import com.darkmagician6.eventapi.events.Event;

public class EventMotion implements Event, Cancellable
{
    public static double y;
    public static float yaw;
    public static float pitch;
    public static boolean onGround;
    public boolean cancel;
    public EventType type;

    public static float RPITCH;
    public static float RPPITCH;

    public boolean isCancel() {
        return cancel;
    }
    public EventMotion(double y, float yaw, float pitch) {
        EventMotion.y = y;
        EventMotion.yaw = yaw;
        EventMotion.pitch = pitch;
        this.type = EventType.PRE;
    }

    public EventMotion(double y, float yaw, float pitch, boolean onGround) {
        EventMotion.y = y;
        EventMotion.yaw = yaw;
        EventMotion.pitch = pitch;
        EventMotion.onGround = onGround;
        this.type = EventType.PRE;
    }


    public EventType getEventType() {
        return this.type;
    }


    public EventMotion(EventType type,float pitch) {
        if (type == EventType.POST){
            //Memory prevPitch and Pitch for rotation animation
            RPPITCH = RPITCH;
            RPITCH = pitch;
        }
        this.type = type;
    }

    public void setRPITCH(float RPITCH) {
        EventMotion.RPITCH = RPITCH;
    }

    public void setRPPITCH(float RPPITCH) {
        EventMotion.RPPITCH = RPPITCH;
    }

    public EventMotion(EventType type) {
        this.type = type;
    }

    public boolean isPre() {
        return this.type == EventType.PRE;
    }
    public boolean isPost() {
        return this.type == EventType.POST;
    }
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public double getY() {
        return EventMotion.y;
    }

    public void setY(double y) {
        EventMotion.y = y;
    }

    public float getYaw() {
        return EventMotion.yaw;
    }

    public void setYaw(float yaw) {
        EventMotion.yaw = yaw;
    }

    public float getPitch() {
        return EventMotion.pitch;
    }

    public void setPitch(float pitch) {
        EventMotion.pitch = pitch;
    }

    public boolean isOnGround() {
        return EventMotion.onGround;
    }

    public void setOnGround(boolean onGround) {
        EventMotion.onGround = onGround;
    }

}

