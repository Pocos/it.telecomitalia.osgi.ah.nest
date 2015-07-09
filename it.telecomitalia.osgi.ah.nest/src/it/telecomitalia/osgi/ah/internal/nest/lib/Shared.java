package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.util.HashMap;

public class Shared {
	public HashMap<String, SharedData> shareds = new HashMap<String, SharedData>();
	public Shared () {}
	
	public SharedData getDevice(String deviceId) {
		return shareds.get(deviceId);
	}

	public SharedData createShared(String key) {
		SharedData device = new SharedData();
		setShared(key, device);
		return device;
	}
	
	public String[] getSharedIds () {
		String[] a = new String[]{};
		return shareds.keySet().toArray(a);
	}

	public void setShared(String key, SharedData device) {
		shareds.put(key, device);
	}
}
