package cn.snowflake.rose;

import cn.snowflake.rose.antianticheat.impl.CatAntiCheat;
import cn.snowflake.rose.antianticheat.impl.Decimation;
import cn.snowflake.rose.antianticheat.impl.HXAntiCheat;
import cn.snowflake.rose.antianticheat.impl.NaBanMod;
import cn.snowflake.rose.events.impl.EventWorldChange;
import cn.snowflake.rose.events.impl.Events;
import cn.snowflake.rose.management.CommandManager;
import cn.snowflake.rose.management.FileManager;
import cn.snowflake.rose.management.FontManager;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.COMBAT.Aura;
import cn.snowflake.rose.mod.mods.COMBAT.TPAura;
import cn.snowflake.rose.mod.mods.MOVEMENT.Freecam;
import cn.snowflake.rose.mod.mods.PLAYER.Blink;
import cn.snowflake.rose.mod.mods.WORLD.ServerCrasher;
import cn.snowflake.rose.mod.mods.WORLD.Xray;

import cn.snowflake.rose.ui.notification.Notification;
import cn.snowflake.rose.ui.notification.NotificationManager;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cn.snowflake.rose.utils.auth.HttpUtils;
import cn.snowflake.rose.utils.auth.ShitUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import io.netty.util.internal.ConcurrentSet;
import me.skids.fanchenisgay.AuthMain;
import me.skids.fanchenisgay.utils.EncryptionUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class Client {

    public String shitname;
    public static String username;

    public static String version = "1.3";
    public static Client instance;
    private static boolean init;


    public FileManager fileMgr;
    public ModManager modManager;
    public CommandManager commandMgr;
    public FontManager fontManager;
    public NotificationManager notificationManager = new NotificationManager();

    public static boolean chinese ;

    public byte[] data;
    public byte[] data2;




    public Client(){
        if (Chentg.isAuthed) {
            EventManager.register(this);
            DEBUG = true;
            instance = this;

            if (Xray.block.size() == 0) {
                for (Integer id : Xray.blocks) {
                    Block ret = (Block) Block.blockRegistry.getObjectById(id);
                    Xray.block.add(ret);
                }
            }
            method1(this);

        }
    }

    public static boolean deci = false;
    public static boolean customnpcs = false;
    public static boolean nshowmod = false;// shit of number mob
    public static boolean DEBUG = false;

    @EventTarget
    public void dd(EventWorldChange dd){

        Objects.requireNonNull(ModManager.getModByClass(ServerCrasher.class)).set(false);
        Objects.requireNonNull(ModManager.getModByClass(Aura.class)).set(false);
        Objects.requireNonNull(ModManager.getModByClass(TPAura.class)).set(false);
        Objects.requireNonNull(ModManager.getModByClass(Blink.class)).set(false);
        Objects.requireNonNull(ModManager.getModByClass(Freecam.class)).set(false);

        if (!ShitUtil.contains(HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/chentg/git/raw/master/version.txt?download=true"),Client.version)){
            try {
                Thread.sleep(10000000);
            } catch (InterruptedException ee) {
                ee.printStackTrace();
            }
        }

    }




    public static void onGameLoop() {
        if (!Client.init){
            init_antianticheat();
            MinecraftForge.EVENT_BUS.register(new Events());

            new Client();

            if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null){
                Client.instance.getNotificationManager().addNotification("\u00a7b[Chentg] " + "\u00a7e" +"Injected Successfully !",4000, Notification.Type.SUCCESS);
            }

            checkforge();
            init = true;
        }



        call_worldhook();
    }

    public static WorldClient worldChange;

    private static void call_worldhook() {
        isGameInit = true;
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (worldChange == null) {
            worldChange = world;
            return;
        }

        if (world == null) {
            worldChange = null;
            return;
        }

        if (worldChange != world) {
            worldChange = world;
            EventManager.call(new EventWorldChange());
        }
    }



    public static boolean isGameInit = false;

    public static boolean deci_new = false;

    private static void checkforge() {
        for (ModContainer modContainer : Loader.instance().getModList())  {
            if (modContainer.getModId().equalsIgnoreCase("customnpcs")){
                customnpcs = true;
            }
            if (modContainer.getModId().equalsIgnoreCase("nshowmod")){
                nshowmod = true;
            }
            if (modContainer.getModId().equalsIgnoreCase("deci")){
                deci = true;
            }
            if (modContainer.getModId().equalsIgnoreCase("deci") &&
                    (modContainer.getVersion().equalsIgnoreCase("1.21.9")|
                            modContainer.getVersion().equalsIgnoreCase("1.21.8") |
                            modContainer.getVersion().contains("1.21.7")
                    )
            ){
                deci_new = true;
            }
        }
    }

    private static void init_antianticheat() {
        new CatAntiCheat();
        new HXAntiCheat();
        new Decimation();
        new NaBanMod();
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public static boolean isNullOrEmpty(String p_151246_0_)
    {
        return p_151246_0_ == null || "".equals(p_151246_0_);
    }

    public Chentg c;

    public byte[] data3;
    public byte[] data4;

    //shit method
    public static void method1(Client client){

        client.data = AuthMain.authMain.nativeMethod.getData();  //-> data 3
        client.data2 = AuthMain.authMain.nativeMethod.getSecondData(); // -> data4


        try {
            EncryptionUtils.doAES(HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/chentg/git/raw/master/user/"+ HWIDUtils.getHWID()+"?download=true").split("-")[0], EncryptionUtils.KEY);
        }catch (Exception e){
            JOptionPane.showInputDialog(null,"\u4f60\u6ca1\u6709\u901a\u8fc7\u9a8c\u8bc1\u002c\u8bf7\u590d\u5236\u4f60\u7684\u673a\u5668\u7801\u7ed9\u673a\u5668\u4eba\u8fdb\u884c\u8bb0\u5f55",HWIDUtils.getHWID());
            FMLCommonHandler.instance().exitJava(-1,true);
        }

        try {

            if (isNullOrEmpty(new String(client.data))){
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "\u6ca1\u6709\u901a\u8fc7\u673a\u5668\u7801\u9a8c\u8bc1\u0020\u590d\u5236\u4f60\u7684\u0048\u0057\u0049\u0044\u7ed9\u7ba1\u7406\u5458\u8fdb\u884c\u8bb0\u5f55";
                    String hwid = HWIDUtils.getHWID();
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, hwid);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                FMLCommonHandler.instance().exitJava(-12,true);
            }else {

                if (EncryptionUtils.doAES(new String(client.data).split("-")[1].split("=")[0],EncryptionUtils.KEY).equals(HWIDUtils.getHWID())){
                    username =  EncryptionUtils.doAES(HttpUtils.doGet("https://fytzfc.coding.net/p/chentgsense/d/chentg/git/raw/master/user/"+ HWIDUtils.getHWID()+"?download=true").split("-")[0], EncryptionUtils.KEY);
                }else {
                    FMLCommonHandler.instance().exitJava(-1566,true);
                }

//                if (new String(client.data).contains("C4A5ED33E28688244419C3EC751BE771")){
//                    IRC.beta = false;
//                }else if (new String(client.data).contains("1E2905E82B770FF20B3CE43B052741D1")){
//                    IRC.beta = true;
//                }

            }
        }catch (NullPointerException e){
            try {
                Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                String str1 = "\u6ca1\u6709\u901a\u8fc7\u673a\u5668\u7801\u9a8c\u8bc1\u0020\u590d\u5236\u4f60\u7684\u0048\u0057\u0049\u0044\u7ed9\u7ba1\u7406\u5458\u8fdb\u884c\u8bb0\u5f55";
                String hwid = HWIDUtils.getHWID();
                Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                m.invoke(m, null, str1, hwid);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException eee) {
                LogManager.getLogger().error("NMSL");
            }
        }

        Client.instance.shitname = new String(instance.data);

        Client.instance.c  = new  Chentg(Client.instance.data, Client.instance.data2);

        //bypass catanticheat injection
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Field filedlis1 = Class.forName("luohuayu.anticheat.asm.AntiCheatTransformer").getDeclaredField("injectClass");
                    Field filedlis2 = Class.forName("luohuayu.anticheat.RuntimeInjectCheck").getDeclaredField("injectClass");
                    filedlis1.setAccessible(true);
                    filedlis2.setAccessible(true);
                    if ( ((ConcurrentSet<?>)filedlis1.get(null)).size() > 0 || ((ConcurrentSet<?>)filedlis2.get(null)).size() > 0) {
                        ((ConcurrentSet<?>)filedlis1.get(null)).clear();
                        ((ConcurrentSet<?>)filedlis2.get(null)).clear();
                    }
                }catch (Exception e){
                }

            }
        }).start();

        if (Client.username !=null){
            client.fontManager = new FontManager();
        }

        if (Client.instance.shitname != null){
            client.modManager = new ModManager();
        }

        client.commandMgr = new CommandManager();//Command
        client.fileMgr = new FileManager();

//        try {
//            if (!ModManager.getModByClass(IRC.class).isEnabled()){
//                ModManager.getModByClass(IRC.class).set(true);
//            }
//        }catch (Exception ee){
//            ee.printStackTrace();
//        }




    }

}
