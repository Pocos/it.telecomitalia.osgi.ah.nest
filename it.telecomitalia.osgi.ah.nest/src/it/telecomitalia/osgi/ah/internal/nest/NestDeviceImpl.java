package it.telecomitalia.osgi.ah.internal.nest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Constants;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.ah.nest.NestDeviceListener;
import it.telecomitalia.ah.nest.NestHacDevice;
import it.telecomitalia.osgi.ah.internal.nest.NestDeviceEnum.Type;

public class NestDeviceImpl implements NestDevice, NestHacDevice {

	private DiscoveryThread discovery;
	private String deviceId;
	
	/*public NestDeviceImpl(DiscoveryThread t, String id) {
		this.discovery=t;
		this.id=id;
	}*/
	public ServiceRegistration<?> activate(DiscoveryThread t, String deviceId) {
		this.discovery=t;
		this.deviceId=deviceId;
		
		return createService(NestDeviceEnum.Type.THERMOSTAT, deviceId);

	}
	
	@Override
	public String set(Object json) {
		return discovery.set(this.deviceId,json);

	}

	@Override
	public Object get(String key) {
		return discovery.get(this.deviceId,key);
	}

	@Override
	public String getId() {
		return deviceId;
	}
	
	private ServiceRegistration<?> createService(Type type, String deviceId) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("service.pid", deviceId);
		props.put(Constants.DEVICE_CATEGORY, "Nest");
		props.put("org.nest.device.type", type.toString());
		props.put(Constants.DEVICE_SERIAL, deviceId);
		ServiceRegistration<?> sReg = Activator.getContext().registerService(NestDevice.class.getName(), this, props);
		return sReg;

	}

	
	@Override
	public String getPid() {
		// TODO Auto-generated method stub
		return deviceId;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noDriverFound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setListener(NestDeviceListener nestServiceCluster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeListener(NestDeviceListener nestServiceCluster) {
		// TODO Auto-generated method stub
		return false;
	}

}
