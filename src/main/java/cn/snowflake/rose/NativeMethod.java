package cn.snowflake.rose;

import cn.snowflake.rose.transform.transforms.TransformMinecraft;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import me.skids.margeleisgay.AuthMain;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NativeMethod {
    private byte[] data;
    private byte[] secondData;

    public NativeMethod(byte[] data,byte[] secondData){
        if (data.length ==  0){
            try {
                Class<?> clazz = Class.forName("javax.swing.JOptionPane");
                String str1 = "\u6ca1\u6709\u901a\u8fc7\u673a\u5668\u7801\u9a8c\u8bc1\u0020\u590d\u5236\u4f60\u7684\u0048\u0057\u0049\u0044\u7ed9\u7ba1\u7406\u5458\u8fdb\u884c\u8bb0\u5f55";
                String hwid = HWIDUtils.getHWID();
                Method m = clazz.getMethod("showInputDialog", Component.class, Object.class, Object.class);
                m.invoke(m, null, str1, hwid);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LogManager.getLogger().error("NMSL");
            }
            FMLCommonHandler.instance().exitJava(-166,true);
        }
        this.data = data;
        this.secondData = secondData;
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("error");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public NativeMethod(byte[] bytes) {
        this.data = bytes;
    }

    public byte[] getSecondData() {
        return secondData;
    }

    public byte[] getData() {
        return data;
    }


}
