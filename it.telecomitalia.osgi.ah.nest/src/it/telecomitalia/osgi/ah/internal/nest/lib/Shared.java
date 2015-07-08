package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.util.HashMap;

public class Shared {
	public HashMap<String, SharedData> shareds = new HashMap<String, SharedData>();
	public Shared () {}
	
	public SharedData getDevice(String deviceId) {
		return shareds.get(deviceId);
	}

	public SharedData createDevice(String key) {
		SharedData device = new SharedData();
		setDevice(key, device);
		return device;
	}
	
	public String[] getDeviceIds () {
		String[] a = new String[]{};
		return shareds.keySet().toArray(a);
	}

	public void setDevice(String key, SharedData device) {
		shareds.put(key, device);
	}
}
