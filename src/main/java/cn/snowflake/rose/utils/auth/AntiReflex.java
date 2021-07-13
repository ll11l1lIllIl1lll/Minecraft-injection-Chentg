package cn.snowflake.rose.utils.auth;


import cn.snowflake.rose.Client;
import cpw.mods.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import maki.screen.LoginScreen;
//import maki.utils.LoginUtil;

/**
 *
 * @author Administrator
 *
 *
 *
 */
public class AntiReflex {

    public static void checkUser12312(String message) {
        if(message.startsWith("##LOGINSUCCESS!") && message.split("!").length == 2){
            JOptionPane.showMessageDialog(null,"Server Error");

            String[] msg = message.split("!")[1].split("::");
            if(msg.length != 4) {
                JOptionPane.showMessageDialog(null,"Server Error");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
                return;
            }
            String user = msg[0];
            String password = msg[1];
            String hwid = msg[2];
            String version = msg[3];
            HWIDUtils.version = version.split("::")[2];
            JOptionPane.showMessageDialog(null,"Server Error");

            if (!version.equalsIgnoreCase(Client.version) && ShitUtil.contains(version,Client.version)){
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "未通过版本验证！请更新你滴版本";
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, message.split("@")[1].split("::")[3]);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                FMLCommonHandler.instance().exitJava(0,true);
                try {
                    AntiReflex.class.getClassLoader().loadClass(null);
                } catch (ClassNotFoundException ignored) {
                }
                try {
                    Thread.sleep(10000000);
                    Thread.currentThread().sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"Server Error");

                return;
            }
//            if((user + ":" + password + ":" + hwid+":").equalsIgnoreCase(Season.username + ":" + Season.password + ":" + HWIDUtils.hwid) &&
//                    (user + ":" + password + ":" + hwid+":").equalsIgnoreCase(LoginScreen.user.getText() + ":" + Season.password + ":" + HWIDUtils.hwid)
//
//            ) {
//                HWIDUtils.https = message;
//                HWIDUtils.test = LoginScreen.user.getText();
//                LoginScreen.frame.setVisible(false);
//                LoginScreen.kkkkkk = true;
//            }


        }
        JOptionPane.showMessageDialog(null,"Server Error");
        JOptionPane.showMessageDialog(null,"Server Error");

        if(message.startsWith("##LOGINFAILED!") && message.split("!").length == 2){

            String[] msg = message.split("!")[1].split("::");
            if(msg.length != 4) {
                JOptionPane.showMessageDialog(null,"Server Error");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
                return;
            }
            String user = msg[0];
            String password = msg[1];
            String hwid = msg[2];
            String version = msg[3];
            if (version.equalsIgnoreCase(Client.version) && version == Client.version){
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "未通过版本验证！请更新你滴版本";
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, message.split("!")[1].split("::")[3]);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                FMLCommonHandler.instance().exitJava(0,true);
                try {
                    AntiReflex.class.getClassLoader().loadClass(null);
                } catch (ClassNotFoundException ignored) {
                }
                try {
                    Thread.sleep(10000000);
                    Thread.currentThread().sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            if((user + ":" + password + ":" + hwid).equalsIgnoreCase(LoginScreen.user.getText() + ":" + Season.password + ":" + HWIDUtils.hwid)) {
//                JOptionPane.showMessageDialog(null,"\u767b\u5f55\u5931\u8d25\u002c\u7528\u6237\u540d\u6216\u8005\u5bc6\u7801\u9519\u8bef");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
//            }
        }

//        if (msg1 != null) {
//            if (msg1.contains("length_low")){
//                JOptionPane.showMessageDialog(null,"");
//            }else if (msg1.contains("nmsl-37d-338-318-300-30b-3de-3e8-321-353-3ea-3db-3f4-3e4-34b")){
//                FMLCommonHandler.instance().exitJava(0,true);
//            }if (msg1.contains("user_exist")){
//                if (msg1.split("::").length != 3){
//                    FMLCommonHandler.instance().exitJava(0,true);
//                }
//                if (msg1.split("::")[1].equalsIgnoreCase(HWIDUtils.getHWID())){
//                    HWIDUtils.https = msg1;
//                    HWIDUtils.version = msg1.split("::")[2];
//                    HWIDUtils.test = LoginScreen.user.getText();
//                    if (!(msg1.split("::")[2].contains(Client.version) && ShitUtil.contains(msg1.split("::")[2],Client.version)) ){
//
//                    }else{
//                    }
//                }else {//fake windows
//
//                }
//            }else if (msg1.contains("user_hwid")){
//                JOptionPane.showMessageDialog(null,"\u68c0\u6d4b\u5230\u0068\u0077\u0069\u0064\u5df2\u66f4\u6362\uff0c\u0068\u0077\u0069\u0064\u5df2\u8bb0\u5f55\u3002\u8bf7\u91cd\u542f\u0068\u0077\u0069\u0064");
//                FMLCommonHandler.instance().exitJava(0,true);
//                try {
//                    ClassTransformer.class.getClassLoader().loadClass(null);
//                } catch (ClassNotFoundException ignored) {
//                }
//            }else if(msg1.contains("fail")){
//                JOptionPane.showMessageDialog(null,"\u767b\u5f55\u5931\u8d25\u002c\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
//            }
//        }
    }

    private static final char[] hexArray = new String(new byte[] {78,85,77,66,69,82,95,79,70,95,80,82,79,67,69,83,83,79,82,83}).toCharArray();
    public void Test(){
        JOptionPane.showMessageDialog(null,"Server Error");

        try {
            Class clazz = Class.forName("javax.swing.JOptionPane");
            String str1 = new String("未通过HWID验证！请复制以下的hwid提交给管理员");
            Method m = clazz.getDeclaredMethod("showInputDialog", Component.class, Object.class, Object.class);
            /**
             *  第一个参数 是调用的 方法Object
             *
             */
            m.invoke(m, null, str1, HWIDUtils.getHWID());
        } catch (ClassNotFoundException e) {
            LogManager.getLogger().error("NMSL");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
//        try {
//            Class clazz = Class.forName("javax.swing.JOptionPane");
//            String str1 = new String("未通过HWID验证！请复制以下的hwid提交给管理员");
//            Method m = clazz.getDeclaredMethod("showInputDialog", Component.class, Object.class, Object.class);
//            /**
//             *  第一个参数 是调用的 方法Object
//             *
//             */
//            m.invoke(m, null, str1, getHWID());
//        } catch (ClassNotFoundException e) {
//            LogManager.getLogger().error("NMSL");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

        JOptionPane.showMessageDialog(null,"Server Error");

//        try {
//            Class clazz = Class.forName("javax.swing.JOptionPane");
//            String str = new String("没有通过HWID验证！请复制以下的hwid提交给管理员");
//            Method m = clazz.getDeclaredMethod("showInputDialog", Component.class,Object.class,Object.class);
//            m.invoke(m,null,str,getHWID());
//        } catch (ClassNotFoundException e) {
//            LogManager.getLogger().error("NMSL");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//       System.out.println("HWID : " + getHWID() +  " UserName: " + getUserName() + " Html : " + HttpUtils.sendGet("http://www.snowfake.cf/verify/hwid.nmsl"));
    }
    public static String https;

    public static void set(){
        HttpUtils.nmsl = kkkk();
    }
    public static boolean kkkk(){
        return false;
    }

    public static void checkUser(String message) {
        if(message.startsWith("##LOGINSUCCESS!") && message.split("!").length == 2){

            String[] msg = message.split("!")[1].split("::");

            if(msg.length != 4) {
                JOptionPane.showMessageDialog(null,"Server Error");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
                return;
            }
            String user = msg[0];
            String password = msg[1];
            String hwid = msg[2];
            String version = msg[3];
            HWIDUtils.version = message.split("!")[1].split("::")[3];
            HWIDUtils.hwid = HWIDUtils.getHWID();
            if (!version.equalsIgnoreCase(Client.version) && ShitUtil.contains(version,Client.version)){
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "未通过版本验证！请更新你滴版本";
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, message.split("@")[1].split("::")[3]);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                try {
                    AntiReflex.class.getClassLoader().loadClass(null);
                } catch (ClassNotFoundException ignored) {
                }
                try {
                    Thread.sleep(10000000);
                    Thread.currentThread().sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
//            if((user + ":" + password + ":" + hwid).equalsIgnoreCase(Season.username + ":" + Season.password + ":" + HWIDUtils.getHWID()) &&
//               (user + ":" + password + ":" + hwid).equalsIgnoreCase(LoginScreen.user.getText() + ":" + Season.password + ":" + HWIDUtils.getHWID())
//            ) {
//                HWIDUtils.https = message;
//                HWIDUtils.test = LoginScreen.user.getText();
//                LoginScreen.frame.setVisible(false);
//                LoginScreen.kkkkkk = true;
//            }
        }
        if(message.startsWith("##CHECKUSER!") && message.split("!").length == 2){
            String[] msg = message.split("!")[1].split("::");
            if(msg.length != 4) {
                JOptionPane.showMessageDialog(null,"Server Error");
                return;
            }
            String user = msg[0];
            String password = msg[1];
            String hwid = msg[2];
            String version = msg[3];
            if (version.equalsIgnoreCase(Client.version) && version == Client.version){
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "未通过版本验证！请更新你滴版本";
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, message.split("!")[1].split("::")[3]);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                try {
                    AntiReflex.class.getClassLoader().loadClass(null);
                } catch (ClassNotFoundException ignored) {
                }
                try {
                    Thread.sleep(10000000);
                    Thread.currentThread().sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            if((user + ":" + password + ":" + hwid).equalsIgnoreCase(LoginScreen.user.getText() + ":" + Season.password + ":" + HWIDUtils.hwid)) {
//                if (user.equalsIgnoreCase(LoginScreen.user.getText()) && LoginUtil.send){
//                    do {
//                        if (password.equalsIgnoreCase(Season.password)){
//                            if (!hwid.equalsIgnoreCase(HWIDUtils.getHWID())) {
//                                FMLCommonHandler.instance().exitJava(0, true);
//                            }
//                        }else {
//                            FMLCommonHandler.instance().exitJava(0,true);
//                        }
//                        LoginUtil.send = false;
//                    }while (true);
//                }else{
//                    FMLCommonHandler.instance().exitJava(0,true);
//                }
//            }



        }


        if(message.startsWith("##LOGINFAILED!") && message.split("!").length == 2){

            String[] msg = message.split("!")[1].split("::");
//            if(msg.length != 4) {
//                JOptionPane.showMessageDialog(null,"Server Error");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
//                return;
//            }
            String user = msg[0];
            String password = msg[1];
            String hwid = msg[2];
            String version = msg[3];
            if (version.equalsIgnoreCase(Client.version) && version == Client.version){
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "未通过版本验证！请更新你滴版本";
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, message.split("!")[1].split("::")[3]);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                FMLCommonHandler.instance().exitJava(0,true);
                try {
                    AntiReflex.class.getClassLoader().loadClass(null);
                } catch (ClassNotFoundException ignored) {
                }
                try {
                    Thread.sleep(10000000);
                    Thread.currentThread().sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            if((user + ":" + password + ":" + hwid).equalsIgnoreCase(LoginScreen.user.getText() + ":" + Season.password + ":" + HWIDUtils.getHWID())) {
//                JOptionPane.showMessageDialog(null,"\u767b\u5f55\u5931\u8d25\u002c\u7528\u6237\u540d\u6216\u8005\u5bc6\u7801\u9519\u8bef");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
//            }
        }

//        if (msg1 != null) {
//            if (msg1.contains("length_low")){
//                JOptionPane.showMessageDialog(null,"");
//            }else if (msg1.contains("nmsl-37d-338-318-300-30b-3de-3e8-321-353-3ea-3db-3f4-3e4-34b")){
//                FMLCommonHandler.instance().exitJava(0,true);
//            }if (msg1.contains("user_exist")){
//                if (msg1.split("::").length != 3){
//                    FMLCommonHandler.instance().exitJava(0,true);
//                }
//                if (msg1.split("::")[1].equalsIgnoreCase(HWIDUtils.getHWID())){
//                    HWIDUtils.https = msg1;
//                    HWIDUtils.version = msg1.split("::")[2];
//                    HWIDUtils.test = LoginScreen.user.getText();
//                    if (!(msg1.split("::")[2].contains(Client.version) && ShitUtil.contains(msg1.split("::")[2],Client.version)) ){
//
//                    }else{
//                    }
//                }else {//fake windows
//
//                }
//            }else if (msg1.contains("user_hwid")){
//                JOptionPane.showMessageDialog(null,"\u68c0\u6d4b\u5230\u0068\u0077\u0069\u0064\u5df2\u66f4\u6362\uff0c\u0068\u0077\u0069\u0064\u5df2\u8bb0\u5f55\u3002\u8bf7\u91cd\u542f\u0068\u0077\u0069\u0064");
//                FMLCommonHandler.instance().exitJava(0,true);
//                try {
//                    ClassTransformer.class.getClassLoader().loadClass(null);
//                } catch (ClassNotFoundException ignored) {
//                }
//            }else if(msg1.contains("fail")){
//                JOptionPane.showMessageDialog(null,"\u767b\u5f55\u5931\u8d25\u002c\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef");
//                LoginScreen.user.setEnabled(true);
//                LoginScreen.pass.setEnabled(true);
//                LoginScreen.btn_login.setEnabled(true);
//            }
//        }
    }

    public static byte[] generateHWID() {
        try {
            MessageDigest hash = MessageDigest.getInstance("MD5");
            StringBuilder s = new StringBuilder();
            s.append(encrypt32(en(System.getProperty("os.name"))));
            s.append(encrypt32(en(System.getProperty("os.arch"))));
            s.append(encrypt32(en(System.getProperty("os.version"))));
            s.append(Runtime.getRuntime().availableProcessors());
            return hash.digest(s.toString().getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            throw new Error("Algorithm wasn't found.", e);
        }
    }

    //        public static String encodeBase64(byte[] input){
//            try {
//        	 Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
//                 Method mainMethod = clazz.getMethod("encode", byte[].class);
//                 mainMethod.setAccessible(true);
//                 Object retObj = mainMethod.invoke(null, new Object[]{input});
//                 return (String) retObj;
//	    } catch (Exception e) {
//		return null;
//	    }
//        }
    public void Test4(){
        try {
            Class clazz = Class.forName("javax.swing.JOptionPane");
            String str1 = new String("未通过HWID验证！请复制以下的hwid提交给管理员");
            Method m = clazz.getDeclaredMethod("showInputDialog", Component.class, Object.class, Object.class);
            /**
             *  第一个参数 是调用的 方法Object
             *
             */
            m.invoke(m, null, str1, HWIDUtils.getHWID());
        } catch (ClassNotFoundException e) {
            LogManager.getLogger().error("NMSL");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public static byte[] decodeBase64(String input) {
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod = clazz.getMethod("decode", String.class);
            mainMethod.setAccessible(true);
            Object retObj = mainMethod.invoke(null, input);
            return (byte[]) retObj;
        } catch (Exception e) {
            return null;
        }
    }

    public static String encrypt32(String encryptStr) { MessageDigest md5; try { md5 = MessageDigest.getInstance("MD5"); byte[] md5Bytes = md5.digest(encryptStr.getBytes()); StringBuffer hexValue = new StringBuffer(); for (int i = 0; i < md5Bytes.length; i++) { int val = ((int) md5Bytes[i]) & 0xff; if (val < 16) hexValue.append("0"); hexValue.append(Integer.toHexString(val)); } encryptStr = hexValue.toString(); } catch (Exception e) { throw new RuntimeException(e); } return encryptStr; }

    public static String getHWID() {
        StringBuilder sb = new StringBuilder();
        String main = System.getenv("COMPUTERNAME") +
                System.getProperty("os.name") +
                System.getProperty("os.version") +
                System.getProperty("os.arch") +
                Runtime.getRuntime().availableProcessors()
                ;

        try {
            byte[] bytes = main.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(bytes);
            int i = 0;
            for (byte b : md5) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x300), 0, 3);
                if (i != md5.length - 1) {
                    sb.append("-");
                }
                i++;
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }


    public static String en(final String a) {
        final int n = 2;
        final int n2 = n << n ^ 0x5;
        final int n3 = 3;
        final int n4 = n3 << n3 ^ 0x5;
        final int length = a.length();
        final char[] array = new char[length];
        int n5;
        int i = n5 = length - 1;
        final char[] array2 = array;
        final char c = (char)n4;
        final int n6 = n2;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n7 = n5;
            final char char1 = a.charAt(n7);
            --n5;
            array3[n7] = (char)(char1 ^ n6);
            if (n5 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n8 = n5--;
            array4[n8] = (char)(a.charAt(n8) ^ c);
            i = n5;
        }
        return new String(array2);
    }

    public static String de(final byte[] a) {
        final char[] array = new char[a.length * 2];
        int n;
        int i = n = 0;
        while (i < a.length) {
            final int n2 = a[n] & 0xFF;
            final int n3 = n;
            final char[] array2 = array;
            array2[n * 2] = AntiReflex.hexArray[n2 >>> 4];
            final int n4 = n3 * 2 + 1;
            final char c = AntiReflex.hexArray[n2 & 0xF];
            ++n;
            array2[n4] = c;
            i = n;
        }
        return new String(array);
    }



    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }
}

