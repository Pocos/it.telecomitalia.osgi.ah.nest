package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.util.HashMap;

public class Topaz {
	public HashMap<String, TopazData> topazs = new HashMap<String, TopazData>();
	/**
	 *  This class collects almost all the info on the Nest Protects registered to the cloud and it is indexed by their unique serial id.
	 *  Field of the Nest Protect only.
	 */
	public Topaz () {}
	
	public TopazData getTopaz(String deviceId) {
		return topazs.get(deviceId);
	}

	public TopazData createTopaz(String key) {
		TopazData topaz = new TopazData();
		setTopaz(key, topaz);
		return topaz;
	}
	
	public String[] getTopazIds () {
		String[] a = new String[]{};
		return topazs.keySet().toArray(a);
	}

	public void setTopaz(String key, TopazData topaz) {
		topazs.put(key, topaz);
	}
}
