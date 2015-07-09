package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.util.HashMap;

public class Track {
	
	public HashMap<String, TrackData> devices = new HashMap<String, TrackData>();
	/**
	 *  This class show the online flag of all the Nest Thermostats registered to the cloud and it is indexed by their unique serial id.
	 *  Field of the Nest Thermostat only.
	 */
	public Track () {}
	
	public TrackData getTrack(String deviceId) {
		return devices.get(deviceId);
	}

	public TrackData createTrack(String key) {
		TrackData device = new TrackData();
		setTrack(key, device);
		return device;
	}
	
	public void setTrack(String key,TrackData device) {
		devices.put(key, device);
	}
	
	public String[] getTrackIds () {
		String[] a = new String[]{};
		return devices.keySet().toArray(a);
	}
	
}