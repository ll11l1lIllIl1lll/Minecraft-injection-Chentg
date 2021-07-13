package cn.snowflake.rose.utils.antianticheat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class HXAntiCheatHelper {



    public static String sha1Hex(InputStream object) {
        try {
            return DigestUtils.sha1Hex(object).toLowerCase();
        } catch (IOException e) {
        }
        return null;
    }

    public static String currentTimeMillistoHexString() {
        return Long.toHexString(System.currentTimeMillis());
    }

    public static ByteBuf writeData(int n2, byte[] data) {
        return Unpooled.buffer().writeInt(n2).writeBytes(toByteArray(data));
    }

    public static  byte[] toByteArray(byte[] arrby) {
        byte[] arrby2 = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = null;
            gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(arrby);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            arrby2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrby2;
    }

    public static Object[] getHXPacketData(Object classloader) {
        try {
            Field ucp = Class.forName( "java.net.URLClassLoader").getDeclaredField("ucp");
            ucp.setAccessible(true);
            Object urlclassPath = ucp.get(classloader);
            Field path = urlclassPath.getClass().getDeclaredField("path");
            path.setAccessible(true);
            ArrayList<URL> pathList = (ArrayList<URL>) path.get(urlclassPath);// URL类型的List
            pathList.removeIf(url ->
                    url.getFile().endsWith(".tmp") ||
                    url.getFile().toLowerCase().endsWith("-skipverify.jar")
            );
            Object[] sha1Hexlist1 = pathList.toArray();
            Object[] sha1Hexlist = new Object[sha1Hexlist1.length];

            for (int i = 0; i < sha1Hexlist1.length; ++i) {
                Object sha1Hex = HXAntiCheatHelper.sha1Hex(((URL)sha1Hexlist1[i]).openStream());//加密input
                sha1Hexlist[i] = sha1Hex;
            }
            return sha1Hexlist;
        }catch (Exception e){
        }
        return null;
    }
}
