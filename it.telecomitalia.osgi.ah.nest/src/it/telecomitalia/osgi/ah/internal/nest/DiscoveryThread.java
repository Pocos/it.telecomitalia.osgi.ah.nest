package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.DeviceData;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.MetaDataData;
import it.telecomitalia.osgi.ah.internal.nest.lib.SharedData;
import it.telecomitalia.osgi.ah.internal.nest.lib.StructureData;
import it.telecomitalia.osgi.ah.internal.nest.lib.Topaz;
import it.telecomitalia.osgi.ah.internal.nest.lib.TopazData;
import it.telecomitalia.osgi.ah.internal.nest.lib.TrackData;







import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DiscoveryThread implements Runnable, NestDevice {

	private static Logger LOG = LoggerFactory.getLogger(DiscoveryThread.class);
	private JNest jn;
	private final int TIMEOUT = 1000;
	private Map<String, NestDevice> list_devices = new HashMap<String, NestDevice>();
	private Map<String, ServiceRegistration<?>> list_services = new HashMap<String, ServiceRegistration<?>>();

	public DiscoveryThread(JNest jn) {
		this.jn = jn;

	}
	
	public void run() {
		while (!Thread.interrupted()) {
			try {

				jn.getStatus();
				if (jn.getStatusResponse() == null)
					continue;
				/*
				 * While the thermostats are indexed by the Device class, the
				 * Protects are indexed by the Topaz class.
				 */
				Device thermostats_list = jn.getStatusResponse().getDevices();
				Topaz protects_list = jn.getStatusResponse().getTopazs();
				// REMEMBER: the online field of the JSONs is updated every 3/4
				// minutes. Needed a much lesser latency.
				// I should compare the last connection timestamp to the current
				// timestamp. There are no last_connection fields
				// for the Nest Protect
				if (thermostats_list != null) {
					for (String id : thermostats_list.getDeviceIds()) {
						LOG.debug("Found Thermostat: {}", id);

						/*
						 * if (System.currentTimeMillis() >
						 * jn.getStatusResponse().getTracks
						 * ().getTrack(id).last_connection + TIMEOUT) {
						 * System.out.println("State: OFFLINE"); }else
						 * System.out.println("State: ONLINE");
						 */

						// if the device_list not already contains the id, add
						// it
						if (list_devices.containsKey(id))
							continue;
						NestDeviceImpl dev = new NestDeviceImpl();
						ServiceRegistration<?> reg = dev.activate(this, id);
						list_devices.put(id, dev);

						// create a service for each device, and set the
						// properties
						// for the service
						list_services.put(id, reg);
					}
				}
				if (protects_list != null) {
					for (String id : protects_list.getTopazIds()) {
						LOG.debug("Found Protect: {}", id);

						if (list_devices.containsKey(id))
							continue;
						NestDeviceImpl dev = new NestDeviceImpl();
						ServiceRegistration<?> reg = dev.activate(this, id);
						list_devices.put(id, dev);

						list_services.put(id, reg);
					}

				}

				// At each iteration unregister the services related to devices
				// which are not in the JSON
				// elements in list_devices -- MUST BE PRESENT into -->
				// new_th_list.getDeviceIds();

				Set<String> removed_devices = new HashSet<String>(list_devices.keySet());
				Set<String> th_set = new HashSet<String>();
				Set<String> pr_set = new HashSet<String>();
				if (thermostats_list != null) {
					th_set = new HashSet<String>(Arrays.asList(thermostats_list.getDeviceIds()));
				}
				if (protects_list != null) {
					pr_set = new HashSet<String>(Arrays.asList(protects_list.getTopazIds()));
				}
				th_set.addAll(pr_set);
				removed_devices.removeAll(th_set);

				for (String remId : removed_devices) {
					list_devices.remove(remId);
					list_services.get(remId).unregister();
					list_services.remove(remId);
				}

				/*
				 * for (String devId : list_devices.keySet()) { int found_id =
				 * 0;
				 * 
				 * for (String real_id : thermostats_list.getDeviceIds()) { if
				 * (real_id.equals(devId)) { found_id = 1; break; } } if
				 * (found_id != 1) { list_devices.remove(devId);
				 * list_services.get(devId).unregister();
				 * list_services.remove(devId); } }
				 */

				Thread.sleep(TIMEOUT);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				LOG.debug("Terminating DiscoveryThread");
				// Thread Stopped, unregister all the services
				for (String devId : list_services.keySet()) {
					list_services.get(devId).unregister();
				}
				return;
			}catch (Exception e){
				LOG.debug("Error on discovery thread");
			}
		} // end while

		LOG.debug("Terminazione Thread");
		// Thread Stopped, unregister all the services
		for (String devId : list_services.keySet()) {
			list_services.get(devId).unregister();
		}
	}

	public String set(Object json) {
		return null;
	};

	public String get(String key) {
		return null;
	};

	/**
	 * Analyze the json sended by the appliance key-by-key, find which class owns the key and pass it to the set method of JNest.
	 * Note: Key-by-key because in the future I will support the composition of the Json with keys that are defined in different classes (e.g. 1 key of shareddata class, 1 key of structuredata class, ecc...)
	 * @param deviceId
	 * @param json
	 * @return
	 */
	public String set(String deviceId, Object json) {
		
		JSONObject json_send=(JSONObject)json;
		try{
		for(int i = 0; i<json_send.names().length(); i++){
//		    Log.v(TAG, "key = " + jobject.names().getString(i) + " value = " + jobject.get(jobject.names().getString(i)));
			String key=json_send.names().getString(i);
			Object value=json_send.get(key);
			JSONObject tmp=new JSONObject();
			tmp.put(key, value);
			DeviceData deviceData = jn.getStatusResponse().getDevices().getDevice(deviceId);
			if(findFieldValue(deviceData, key)!=null){
				jn.setParameter("device",deviceId,tmp);
				continue;
			}
			MetaDataData metaDataData = jn.getStatusResponse().getMetaData().getDevice(deviceId);
			if(findFieldValue(metaDataData, key)!=null){
				jn.setParameter("metaData",deviceId,tmp);
				continue;
			}
			SharedData sharedData = jn.getStatusResponse().getShareds().getDevice(deviceId);
			if(findFieldValue(sharedData, key)!=null){
				jn.setParameter("shared",deviceId,tmp);
				continue;
			}
			//In all the tests the JSon contains only one structureID
			StructureData structureData = jn.getStatusResponse().getStructures().getStructure(jn.getStatusResponse().getStructures().getStructureIds()[0]);
			if(findFieldValue(structureData, key)!=null){
				jn.setParameter("structure",jn.getStatusResponse().getStructures().getStructureIds()[0],tmp);
				continue;
			}
			TrackData trackData = jn.getStatusResponse().getTracks().getTrack(deviceId);
			if(findFieldValue(trackData, key)!=null){
				jn.setParameter("track",deviceId,tmp);
				continue;
			}
			TopazData topazData = jn.getStatusResponse().getTopazs().getTopaz(deviceId);
			if(findFieldValue(topazData, key)!=null){
				jn.setParameter("topaz",deviceId,tmp);
				continue;
			}
		}
		}catch(JSONException e){
			return "Some error occurred";
		}
		
		return "OK";
	}

	/**
	 * Get the value for the field specified
	 * @param deviceId The id of the selected device
	 * @param key The attribute for the specified device
	 * @return The value for the specified device
	 */
	public Object get(String deviceId, String key) {
		//Cycle for all the fields of the StatusResponse object. Those fields are populated by the JSON parser
		if (!list_devices.containsKey(deviceId))
			return null; // implement this to throw a not supported exception
		Object result=null;
		DeviceData deviceData = jn.getStatusResponse().getDevices().getDevice(deviceId);
		if((result=findFieldValue(deviceData, key))!=null){
			return result;
		}
		MetaDataData metaDataData = jn.getStatusResponse().getMetaData().getDevice(deviceId);
		if((result=findFieldValue(metaDataData, key))!=null){
			return result;
		}
		SharedData sharedData = jn.getStatusResponse().getShareds().getDevice(deviceId);
		if((result=findFieldValue(sharedData, key))!=null){
			return result;
		}
		//Usually the JSon contains only one structureID
		StructureData structureData = jn.getStatusResponse().getStructures().getStructure(jn.getStatusResponse().getStructures().getStructureIds()[0]);
		if((result=findFieldValue(structureData, key))!=null){
			return result;
		}
		TrackData trackData = jn.getStatusResponse().getTracks().getTrack(deviceId);
		if((result=findFieldValue(trackData, key))!=null){
			return result;
		}
		TopazData topazData = jn.getStatusResponse().getTopazs().getTopaz(deviceId);
		if((result=findFieldValue(topazData, key))!=null){
			return result;
		}
		return null; // implement this to throw a not supported exception
	}

	private Object findFieldValue(Object objectData, String key) {
		try {
			Field[] fields = objectData.getClass().getFields();
			for (Field f : fields) {
				if (f.getName().equals(key)) {
					return f.get(objectData);
				}
			}
		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage());
			return null;
		}
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
