package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.COMBAT.Aimbot;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

public class SimpleHUD extends Module {
	private ArrayList<Module> mods = new ArrayList<Module>();
	
	public Value<Double> posy = new Value<Double>("SimpleHUD_Y", 12.0D, 1.0D, 200.0D, 1.0D);
	
	public SimpleHUD() {
		super("SimpleHUD", Category.RENDER);
		setChinesename("\u7b80\u5355\u754c\u9762");
	}

	@Override
	public String getDescription() {
		return "简单界面!";
	}

	@Override
	public void onEnable() {
		super.onEnable();
		mods.add(Client.instance.modManager.getModByName("Aimbot"));
		mods.add(Client.instance.modManager.getModByName("Aura"));
		mods.add(Client.instance.modManager.getModByName("NoRecoil"));
		mods.add(Client.instance.modManager.getModByName("Blink"));
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mods.clear();
	}
	
	@EventTarget
	private void on2DEvent(EventRender2D event) {
		int y = posy.getValueState().intValue();
		for (Module module : mods) {
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(module.getName().contains("Aimbot") && Aimbot.target != null ? module.getName() + " ["+Aimbot.target.getCommandSenderName()+"]" : module.getName(), 3, y, module.isEnabled() ? new Color(147, 255, 59).getRGB() : new Color(255,0,0).getRGB());
			y += Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 1;
		}
	}
}
