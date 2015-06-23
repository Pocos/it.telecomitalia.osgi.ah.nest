package it.telecomitalia.osgi.ah.internal.nest;

import java.util.HashMap;

public class MetaData {
	
	public HashMap<String, MetaDataData> devices = new HashMap<String, MetaDataData>();
	public MetaData () {}
	
	public MetaDataData getDevice(String deviceId) {
		return devices.get(deviceId);
	}

	public MetaDataData createDevice(String key) {
		MetaDataData device = new MetaDataData();
		setDevice(key, device);
		return device;
	}
	
	public void setDevice(String key, MetaDataData device) {
		devices.put(key, device);
	}
	
	public String[] getDeviceIds () {
		String[] a = new String[]{};
		return devices.keySet().toArray(a);
	}
	
}