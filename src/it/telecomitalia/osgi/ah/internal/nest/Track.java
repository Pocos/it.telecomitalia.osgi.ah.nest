package it.telecomitalia.osgi.ah.internal.nest;

import java.util.HashMap;

public class Track {
	
	public HashMap<String, TrackData> devices = new HashMap<String, TrackData>();
	public Track () {}
	
	public TrackData getDevice(String deviceId) {
		return devices.get(deviceId);
	}

	public TrackData createDevice(String key) {
		TrackData device = new TrackData();
		setDevice(key, device);
		return device;
	}
	
	public void setDevice(String key,TrackData device) {
		devices.put(key, device);
	}
	
	public String[] getDeviceIds () {
		String[] a = new String[]{};
		return devices.keySet().toArray(a);
	}
	
}