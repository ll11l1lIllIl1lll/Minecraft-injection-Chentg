package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventRenderPlayer;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;

public class Chams extends Module {
    public Chams() {
        super("Chams","Chams", Category.RENDER);
        setChinesename("\u900f\u89c6");
    }

    @Override
    public String getDescription() {
        return "透视!";
    }

    public static boolean chams;

    @Override
    public void onDisable() {
        chams = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onupdate(EventUpdate e){
        chams = this.isEnabled();
    }

    @EventTarget
    public void onrenderplayer(EventRenderPlayer e){
//        System.out.println(e.getAgeInTicks() + "  " + e.getLimbSwing() + " " + e.getRotationYawHead() + " " + e.getRotationPitch());
    }
}