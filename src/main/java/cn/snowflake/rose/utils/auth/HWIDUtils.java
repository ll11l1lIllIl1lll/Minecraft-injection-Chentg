package cn.snowflake.rose.utils.auth;


import cpw.mods.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Administrator
 *
 *
 *
 */
public class HWIDUtils {
    private static final char[] hexArray = new String(new byte[] {78,85,77,66,69,82,95,79,70,95,80,82,79,67,69,83,83,79,82,83}).toCharArray();
    public static String https;
    public static String version;
    public static String hwid;
    public static String test;

    public static void main(String[] args){
    }

    public static String getUserNamenew() {
//        NetworkUtil.checknetwork();
//        version = HttpUtils.sendGet("http://seasonclient.cf/rose/version.txt");
//        https = HttpUtils.sendGet("h"+"t"+"t"+"p"+":"+"/"+"/"+"w"+"w"+"w"+"."+"s"+"e"+"a"+"s"+"o"+"n"+"c"+"l"+"i"+"e"+"n"+"t"+"."+"c"+"f"+"/"+"r"+"o"+"s"+"e"+"/"+"h"+"w"+"i"+"d"+"."+"t"+"x"+"t");
//        version = HttpUtils.sendGet("https://gitee.com/cnsnowflake/seasonclient/raw/master/season/version.txt");
//        https = HttpUtils.sendGet("https://gitee.com/cnsnowflake/seasonclient/raw/master/season/hwid.txt");
        //https://gitee.com/cnsnowflake/seasonclient/raw/master/season/hwid.txt
        if (!HWIDUtils.https.contains(HWIDUtils.getHWID())){
            if (!ShitUtil.contains(https, HWIDUtils.getHWID())){
                FMLCommonHandler.instance().exitJava(0,true);
                try {
                    Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                    String str1 = "12312321123";
                    Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                    m.invoke(m, null, str1, "12321");
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    LogManager.getLogger().error("NMSL");
                }
                try {
                    AntiReflex.class.getClassLoader().loadClass(null);
                } catch (ClassNotFoundException ignored) {
                }
                try {
                    Thread.sleep(10000000);
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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

    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0"); hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

    public static String getHWID() {
        StringBuilder sb = new StringBuilder();
        String main = System.getenv("COMPUTERNAME") +
                System.getProperty("os.name") +
                System.getProperty("os.version") +
                System.getProperty("os.arch") +
                Runtime.getRuntime().availableProcessors();

        try {
            byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
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
            array2[n * 2] = HWIDUtils.hexArray[n2 >>> 4];
            final int n4 = n3 * 2 + 1;
            final char c = HWIDUtils.hexArray[n2 & 0xF];
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
        if (yLen < 0 || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }
}
