package me.skids.fanchenisgay;

import cn.snowflake.rose.NativeMethod;
import cn.snowflake.rose.Chentg;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cn.snowflake.rose.utils.auth.HttpUtils;
import cn.snowflake.rose.utils.other.JReflectUtility;
import me.skids.fanchenisgay.auth.AuthModule;
import me.skids.fanchenisgay.auth.impl.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

public class AuthMain {
	private final ArrayList<AuthModule> games;
	public static AuthMain authMain ;

	public NativeMethod nativeMethod;

	public AuthMain() {
		authMain = this;
		JReflectUtility.setSecurityManagerNull();
		games = new ArrayList<>();
		games.add(new CheckVMProcess());
		games.add(new CheckVMMac());
		games.add(new CheckVMPath());
		games.add(new CheckVersion());

		for (AuthModule game : games) {
			game.onEnable();
			boolean pass = game.run();
			if(!pass) {
				Display.destroy();
				Minecraft.getMinecraft().shutdown();
				Minecraft.getMinecraft().crashed(null);
				Minecraft.getMinecraft().shutdownMinecraftApplet();
				Minecraft.getMinecraft().currentScreen = null;
				Minecraft.getMinecraft().displayHeight = 0;
				Minecraft.getMinecraft().displayWidth = 0;
				Minecraft.getMinecraft().fontRenderer = null;
			}
			game.onDisable();
			nativeMethod = new NativeMethod(HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/chentg/git/raw/master/user/"+HWIDUtils.getHWID()+"?download=true").getBytes(),HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/chentg/git/raw/master/version.txt?download=true").getBytes());
			Chentg.isAuthed = true;
		}

	}

}
