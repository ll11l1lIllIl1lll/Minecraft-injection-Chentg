package cn.snowflake.rose.utils.auth;


import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ShitUtil {
    public void Test1(){
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
    public void Test2(){
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
    public static boolean contains(String target,CharSequence str) {
        return target.indexOf(str.toString()) > -1;
    }

    public static void exit(int status) {

    }

}
