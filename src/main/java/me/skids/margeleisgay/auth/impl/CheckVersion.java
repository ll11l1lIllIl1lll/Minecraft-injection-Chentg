package me.skids.margeleisgay.auth.impl;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.NativeMethod;
import cn.snowflake.rose.utils.auth.HttpUtils;
import cn.snowflake.rose.utils.auth.ShitUtil;
import me.skids.margeleisgay.auth.AuthModule;

public class CheckVersion implements AuthModule {
    public String version,selfversion;

    @Override
    public void onEnable() {
        selfversion = Client.version;
        version = NativeMethod.method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean run() {
        if (ShitUtil.contains(version,selfversion)){
            return true;
        }
        return false;
    }
}
