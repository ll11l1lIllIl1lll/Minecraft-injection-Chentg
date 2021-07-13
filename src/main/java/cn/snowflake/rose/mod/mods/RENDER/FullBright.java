package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {
    public Value<String> mode = new Value<String>("FullBright","Mode",0);

    public FullBright() {
        super("FullBright","Full Bright", Category.RENDER);
        this.mode.addValue("Potion");
        this.mode.addValue("Gamma");
        setChinesename("\u65e0\u9650\u591c\u89c6");
    }

    @Override
    public String getDescription() {
        return "无限夜视!";
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        setDisplayName("Potion");
        if (mode.isCurrentMode("Gamma")) {
            mc.gameSettings.gammaSetting = 15;
		}
        if (mode.isCurrentMode("Potion")) {
            mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5200, 0));
		}
    }

    @Override
    public void onDisable() {
    	if (mode.isCurrentMode("Potion")) {
            this.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
		}
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

}

