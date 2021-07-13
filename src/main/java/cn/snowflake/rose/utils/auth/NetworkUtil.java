package cn.snowflake.rose.utils.auth;


import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NetworkUtil {


    public static void checknetwork() {
        URL url = null;
        Boolean bon = false;
        try {
            url = new URL("http://baicu.com/");
            InputStream in = url.openStream();//打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream
            in.close();//关闭此输入流并释放与该流关联的所有系统资源。
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null,"您还没有连接网络哦！");
        }
    }


}