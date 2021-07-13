package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.notification.Notification;
import com.darkmagician6.eventapi.EventTarget;

import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;

public class Notifications extends Module{

	public Notifications() {
		super("Notifications","Notifications", Category.RENDER);
	}


	@Override
	public String getDescription() {
		return "功能开关通知!";
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}

}
