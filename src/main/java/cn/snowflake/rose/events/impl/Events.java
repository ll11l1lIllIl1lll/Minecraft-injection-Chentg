package cn.snowflake.rose.events.impl;

import cn.snowflake.rose.ui.GuiMultiplayerLogin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;



public class Events {

    @SubscribeEvent
    public void drawPost(GuiOpenEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (event.gui instanceof GuiMultiplayer) {
            event.gui = (GuiScreen)new GuiMultiplayerLogin(null);
        }
    }


	
}
