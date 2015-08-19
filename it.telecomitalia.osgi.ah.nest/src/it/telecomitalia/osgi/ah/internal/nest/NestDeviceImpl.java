package it.telecomitalia.osgi.ah.internal.nest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Constants;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.ah.nest.NestHacDevice;
import it.telecomitalia.osgi.ah.internal.nest.NestDeviceEnum.Type;

public class NestDeviceImpl implements NestDevice, NestHacDevice {

	private DiscoveryThread discovery;
	private String id;
	
	/*public NestDeviceImpl(DiscoveryThread t, String id) {
		this.discovery=t;
		this.id=id;
	}*/
	public ServiceRegistration<?> activate(DiscoveryThread t, String id) {
		this.discovery=t;
		this.id=id;
		
		return createService(NestDeviceEnum.Type.THERMOSTAT, id);

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
	
	private ServiceRegistration<?> createService(Type type, String id) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("service.pid", id);
		props.put(Constants.DEVICE_CATEGORY, "Nest");
		props.put("org.nest.device.type", type.toString());
		props.put(Constants.DEVICE_SERIAL, id);
		ServiceRegistration<?> sReg = Activator.getContext().registerService(NestDevice.class.getName(), this, props);
		return sReg;

	}

	
	@Override
	public String getPid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noDriverFound() {
		// TODO Auto-generated method stub
		
	}

}
