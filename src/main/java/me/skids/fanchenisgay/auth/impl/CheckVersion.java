package me.skids.fanchenisgay.auth.impl;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.utils.auth.HttpUtils;
import cn.snowflake.rose.utils.auth.ShitUtil;
import me.skids.fanchenisgay.auth.AuthModule;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CheckVersion implements AuthModule {
    public String version,selfversion;

    @Override
    public void onEnable() {
        selfversion = Client.version;
        version = HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/chentg/git/raw/master/version.txt?download=true");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean run() {
        if (ShitUtil.contains(version,selfversion)){
            return true;
        }
        try {
            Class<?> clazz = Class.forName("javax.swing.JOptionPane");
            String str1 = "\u4f60\u6ca1\u6709\u901a\u8fc7\u7248\u672c\u9a8c\u8bc1";//version check info
            String leastversion = HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false");
            Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
            m.invoke(m, null, str1, leastversion);
            Thread.sleep(10000000);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
            LogManager.getLogger().error("NMSL");
        }
        return false;
    }
}
