package it.telecomitalia.osgi.ah.internal.nest;

public class NestDeviceImpl implements NestDevice {

	private DiscoveryThread discovery;
	private String id;

	public NestDeviceImpl(DiscoveryThread t, String id) {
		this.discovery=t;
		this.id=id;
	}
	
	@Override
	public void set(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String get(String name) {
		return discovery.get(this.id,name);
	}

	@Override
	public String getId() {
		return id;
	}

}
