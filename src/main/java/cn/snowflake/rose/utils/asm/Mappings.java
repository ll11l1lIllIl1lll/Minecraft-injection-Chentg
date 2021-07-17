package cn.snowflake.rose.utils.asm;

import net.minecraft.injection.ClientLoader;

public class Mappings {
    public static String channel = ClientLoader.runtimeObfuscationEnabled ? "field_150746_k": "channel";
    public static String debugFPS = ClientLoader.runtimeObfuscationEnabled ? "field_71470_ab": "debugFPS";

}
