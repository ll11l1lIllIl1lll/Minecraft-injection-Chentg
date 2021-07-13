package cn.snowflake.rose;

import cn.snowflake.rose.transform.ClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class Agent {
	public static Instrumentation instrumentation;
	public static String args;


	public static void agentmain(String args, Instrumentation instrumentation) {
		try {
			Agent.args = args;
			if (!args.contains("--injection=chentg")){
				JOptionPane.showMessageDialog(null,"\u68c0\u6d4b\u5230\u6838\u5fc3\u88ab\u4fee\u6539\u0020\u0021");
			}
			if (args.contains("--chinese=true")){
				Client.chinese = true;
			}
			Agent.instrumentation = instrumentation;
//			if (!args.contains("--debug=true")){
				Agent.loadThisJar();
//			}
			Agent.forceloadclass();
			Agent.retransform();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void retransform(){
		ClassTransformer ct = new ClassTransformer();
		instrumentation.addTransformer(ct, true);
		for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
			if (!ClassTransformer.needTransform(clazz.getName())) continue;
			try {
				instrumentation.retransformClasses(clazz);
			}
			catch (UnmodifiableClassException e) {
//				LogManager.getLogger().log(Level.ERROR, ExceptionUtils.getStackTrace(e));
			}
		}
		instrumentation.removeTransformer(ct);
	}

	public static void loadThisJar() {
		LaunchClassLoader cl = Agent.getLaunchClassLoader();
		if (cl != null) {
			URL url = Agent.class.getProtectionDomain().getCodeSource().getLocation();
			cl.addURL(url);
		}
	}

	public static void forceloadclass() {
		LaunchClassLoader cl = Agent.getLaunchClassLoader();
		if (cl != null) {
			for (String name : ClassTransformer.classNameSet) {
				try {
					cl.loadClass(name);
				}
				catch (ClassNotFoundException ignored) {}
			}
		}
	}

	public static LaunchClassLoader getLaunchClassLoader() {
		for (Class<?> c : instrumentation.getAllLoadedClasses()) {
			if (c.getClassLoader() == null || !c.getClassLoader().getClass().getName().equals("net.minecraft.launchwrapper.LaunchClassLoader")) continue;
			ClassLoader cl = c.getClassLoader();
			return (LaunchClassLoader)cl;
		}
		return null;
	}



	public static void addToMinecraftClassLoader(Class<?> ... classes) {
		for (Class c : instrumentation.getAllLoadedClasses()) {
			if (c.getClassLoader() == null || !c.getClassLoader().getClass().getName().equals("net.minecraft.launchwrapper.LaunchClassLoader")) continue;
			ClassLoader cl = c.getClassLoader();
			try {
				Method addUrl = cl.getClass().getDeclaredMethod("addURL", URL.class);
				addUrl.setAccessible(true);
				for (Class clazz : classes) {
					addUrl.invoke(cl, clazz.getProtectionDomain().getCodeSource().getLocation());
				}
				break;
			}
			catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
