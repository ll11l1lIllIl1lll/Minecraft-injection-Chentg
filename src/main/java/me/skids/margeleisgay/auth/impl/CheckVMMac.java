package me.skids.margeleisgay.auth.impl;

import me.skids.margeleisgay.auth.AuthModule;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckVMMac implements AuthModule {
	private ArrayList<String> targetMac;
	@Override
	public void onEnable() {
		targetMac = new ArrayList<>();
		targetMac.add("00:05:69");
		targetMac.add("00:0c:29");
		targetMac.add("00:50:56");
		targetMac.add("00:03:ff");
		targetMac.add("08:00:27");
	}

	@Override
	public void onDisable() {
		targetMac = null;
	}

	@Override
	public boolean run() {
		try {
			for (String mac : getMacList()) {
				for (String target : targetMac) {
					if(mac.startsWith(target)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


    /**
     *
     * old name  getMacList
     * new name
     *
     * @return
     * @throws Exception
     */
    public static List<String> getMacList() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        while (en.hasMoreElements()) {
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (InterfaceAddress addr : addrs) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                sb.delete(0, sb.length());
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                tmpMacList.add(sb.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        /***去重，别忘了同一个网卡的ipv4,ipv6得到的mac都是一样的，肯定有重复，下面这段代码是。。流式处理***/
        List<String> unique = tmpMacList.stream().distinct().collect(Collectors.toList());
        return unique;
    }

}
