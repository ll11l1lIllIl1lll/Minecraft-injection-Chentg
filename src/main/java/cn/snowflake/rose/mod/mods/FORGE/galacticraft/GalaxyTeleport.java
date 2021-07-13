package cn.snowflake.rose.mod.mods.FORGE.galacticraft;

import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GalaxyTeleport extends Module {

    public GalaxyTeleport(){
        super("GalaxyTeleport", "Galaxy Teleport", Category.FORGE);
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
        } catch (Exception e) {
            setWorking(false);
        }

    }

    @Override
    public String getDescription() {
        return "强制打开星系的界面,任意传送星球!";
    }

    @Override
    public void onEnable() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
            List<Object> objects = new ArrayList<>();
            objects.addAll(((Map<String, Object>) Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredPlanets").invoke(null)).values());
            objects.addAll(((Map<String, Object>) Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredMoons").invoke(null)).values());
            objects.addAll(((Map<String, Object>) Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredSatellites").invoke(null)).values());
            Object screen = Class.forName("micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection").getConstructor(Boolean.TYPE, List.class).newInstance(false, objects);
            mc.displayGuiScreen((GuiScreen) screen);
            this.set(false);
        } catch (Exception ex) {
            this.set(false);
        }
    }

    @EventTarget
    public void onTicks(EventKey e) {
        if (e.getKey() == Keyboard.KEY_ESCAPE) {
            EventManager.unregister(this);
            mc.displayGuiScreen(null);
        }
    }

}
