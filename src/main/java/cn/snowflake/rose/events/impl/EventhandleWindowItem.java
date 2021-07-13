package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Cancellable;
import com.darkmagician6.eventapi.events.Event;

public class EventhandleWindowItem implements Event, Cancellable {

    private boolean cancelled;


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }




}
