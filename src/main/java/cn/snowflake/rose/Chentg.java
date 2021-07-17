package cn.snowflake.rose;

import cn.snowflake.rose.management.CommandManager;
import cn.snowflake.rose.management.FileManager;
import cn.snowflake.rose.management.FontManager;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.WORLD.irc.core.IRC;
import io.netty.util.internal.ConcurrentSet;


import java.lang.reflect.Field;

public class Chentg {
    public static boolean isAuthed = false;

    public Chentg(){
    }

    public Chentg(byte[] data,byte[] data2){
        Client.instance.data3 = data;
        Client.instance.data4 = data2;
    }


}
