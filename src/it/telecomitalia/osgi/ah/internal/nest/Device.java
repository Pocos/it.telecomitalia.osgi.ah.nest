package it.telecomitalia.osgi.ah.internal.nest;

import java.util.HashMap;

public class Device {
	
	public HashMap<String, DeviceData> devices = new HashMap<String, DeviceData>();
	public Device () {}
	
	public DeviceData getDevice(String deviceId) {
		return devices.get(deviceId);
	}

	public DeviceData createDevice(String key) {
		DeviceData device = new DeviceData();
		setDevice(key, device);
		return device;
	}
	
	public String[] getDeviceIds () {
		String[] a = new String[]{};
		return devices.keySet().toArray(a);
	}

	public void setDevice(String key, DeviceData device) {
		devices.put(key, device);
	}
	
	/*
	public String toString () {
		Field[] fields = Device.class.getFields();
		String string = "";
		String name;
		for (int i=0; i<fields.length; i++) {
			name = fields[i].getName();
			try {
				string += name + "\t" + fields[i].get(this) + System.getProperty("line.separator");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return string;
	}
	*/
	
}