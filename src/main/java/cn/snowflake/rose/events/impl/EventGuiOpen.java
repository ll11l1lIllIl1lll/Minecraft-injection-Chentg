package cn.snowflake.rose.events.impl;

import com.darkmagician6.eventapi.events.Cancellable;
import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.gui.GuiScreen;

public class EventGuiOpen implements Event, Cancellable {

    public GuiScreen gui;
    public EventGuiOpen(GuiScreen gui)
    {
        this.gui = gui;
    }

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
