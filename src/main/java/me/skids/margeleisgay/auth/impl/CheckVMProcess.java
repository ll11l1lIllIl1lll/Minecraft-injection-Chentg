package me.skids.margeleisgay.auth.impl;

import cn.snowflake.rose.NativeMethod;
import me.skids.margeleisgay.auth.AuthModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckVMProcess implements AuthModule {
	private ArrayList<String> targetProcess;
	private Process process = null;
	@Override
	public void onEnable() {
		targetProcess = new ArrayList<>();
		targetProcess.add("vmware.exe");
		targetProcess.add("VBoxService.exe");
		try {
			process = Runtime.getRuntime().exec("cmd.exe /c tasklist");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getTargetProcess() {
		return targetProcess;
	}

	public Process getProcess() {
		return process;
	}

	@Override
	public void onDisable() {
		process = null;
	}



	@Override
	public boolean run() {
		return NativeMethod.method1(this,false);
	}

}
