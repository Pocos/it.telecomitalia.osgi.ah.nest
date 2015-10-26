package it.telecomitalia.osgi.ah.internal.nest;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.ah.nest.NestDeviceListener;
import it.telecomitalia.ah.nest.NestHacDevice;
import it.telecomitalia.osgi.ah.internal.nest.NestDeviceEnum.Type;

public class NestDeviceImpl implements NestDevice, NestHacDevice {

	private DiscoveryThread discovery;
	private String deviceId;
	private List listeners = new ArrayList();
	private static Logger LOG = LoggerFactory.getLogger(NestDeviceImpl.class);

	/*
	 * public NestDeviceImpl(DiscoveryThread t, String id) { this.discovery=t;
	 * this.id=id; }
	 */
	public ServiceRegistration<?> activate(DiscoveryThread t, String deviceId) {
		this.discovery = t;
		this.deviceId = deviceId;

		return createService(NestDeviceEnum.Type.THERMOSTAT, deviceId);

	}

	public String set(Object json) {
		return discovery.set(this.deviceId, json);

	}

	public Object get(String key) {
		return discovery.get(this.deviceId, key);
	}

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

	public String getPid() {
		// TODO Auto-generated method stub
		return deviceId;
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

	public void noDriverFound() {
		// TODO Auto-generated method stub

	}

	public boolean setListener(NestDeviceListener listener) {
		listeners.add(listener);
		return true;
	}

	public boolean removeListener(NestDeviceListener listener) {
		return listeners.remove(listener);
	}

	public void notifyFrame(String message) {
		LOG.error("Notify NestDeviceImpl");
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
		{
			NestDeviceListener listener=(NestDeviceListener)iterator.next();
			try {
				listener.notifyFrame(message);
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
	}
}
