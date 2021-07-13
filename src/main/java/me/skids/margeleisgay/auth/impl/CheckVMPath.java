package me.skids.margeleisgay.auth.impl;

import java.io.File;
import java.util.ArrayList;

import me.skids.margeleisgay.auth.AuthModule;

public class CheckVMPath implements AuthModule {
	private ArrayList<File> targetPath;
	@Override
	public void onEnable() {
		targetPath = new ArrayList<>();
		targetPath.add(new File("C:\\Program Files\\VMware\\VMware Tools\\"));
		targetPath.add(new File("C:\\Program Files\\Oracle\\VirtualBox Guest Additions\\"));
	}

	@Override
	public void onDisable() {
		targetPath = null;
	}


	@Override
	public boolean run() {
		for (File path : targetPath) {
			if(path.exists()) {
				return false;
			}
		}
		return true;
	}

}
