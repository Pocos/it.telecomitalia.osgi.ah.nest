package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.ah.nest.NestDevice;

public class NestDeviceImpl implements NestDevice {

	private DiscoveryThread discovery;
	private String id;

	public NestDeviceImpl(DiscoveryThread t, String id) {
		this.discovery=t;
		this.id=id;
	}
	
	@Override
	public void set(String key, String value) {
		discovery.set(key, value);

	}

	@Override
	public String get(String key) {
		return discovery.get(this.id,key);
	}

	@Override
	public String getId() {
		return id;
	}

}
