package me.skids.margeleisgay.auth.impl;

import cn.snowflake.rose.NativeMethod;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cn.snowflake.rose.utils.auth.HttpUtils;
import cn.snowflake.rose.utils.auth.ShitUtil;
import me.skids.margeleisgay.auth.AuthModule;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CheckHWID implements AuthModule {
	public String selfHWID;
	public String targetHWID;

	public String getSelfHWID() {
		return selfHWID;
	}

	public String getTargetHWID() {
		return targetHWID;
	}

	@Override
	public void onEnable() {
		selfHWID = HWIDUtils.getHWID();
		targetHWID = NativeMethod.method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/hwid.txt?download=false");
	}

	@Override
	public void onDisable() {
		
	}


	@Override
	public boolean run() {
		if(!targetHWID.contains(selfHWID) &&
				!(targetHWID.indexOf(selfHWID.toString()) > -1) &&
				!ShitUtil.contains(targetHWID,selfHWID)
		) {
			try {
				Class<?> clazz = Class.forName("javax.swing.JOptionPane");
				String str1 = "\u6ca1\u6709\u901a\u8fc7\u673a\u5668\u7801\u9a8c\u8bc1\u0020\u590d\u5236\u4f60\u7684\u0048\u0057\u0049\u0044\u7ed9\u7ba1\u7406\u5458\u8fdb\u884c\u8bb0\u5f55";
				String hwid = HWIDUtils.getHWID();
				Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
				m.invoke(m, null, str1, hwid);
			} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				LogManager.getLogger().error("NMSL");
			}
			return false;
		}
		return true;
	}

}
