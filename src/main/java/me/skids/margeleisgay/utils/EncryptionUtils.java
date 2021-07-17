package me.skids.margeleisgay.utils;


import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    public static final String KEY = "b8av$g!A";

    public static String doAES(String data, String key ) {
        try {
            byte[] content =  EncryptionUtils.parseHexStr2Byte(data);
            KeyGenerator kgen = KeyGenerator.getInstance((String)"AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance((String)"AES");
            cipher.init(2, (Key)keySpec);
            byte[] result = cipher.doFinal(content);
            return new String(result, "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; ++i) {
            int high = Integer.parseInt((String)hexStr.substring(i * 2, i * 2 + 1), (int)16);
            int low = Integer.parseInt((String)hexStr.substring(i * 2 + 1, i * 2 + 2), (int)16);
            result[i] = (byte)(high * 16 + low);
        }
        return result;
    }

}
