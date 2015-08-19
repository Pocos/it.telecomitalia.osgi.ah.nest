package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.osgi.ah.internal.nest.NestDeviceEnum.Type;
import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Topaz;

import org.osgi.service.device.Constants;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscoveryThread implements Runnable, NestDevice {

	private static Logger LOG = LoggerFactory.getLogger(DiscoveryThread.class);
	private JNest jn;
	private final double TIMEOUT = 5000;
	private Map<String, NestDevice> list_devices = new HashMap<String, NestDevice>();
	private Map<String, ServiceRegistration<?>> list_services = new HashMap<String, ServiceRegistration<?>>();

	public DiscoveryThread(JNest jn) {
		this.jn = jn;

	}

	@Override
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
						NestDeviceImpl dev=new NestDeviceImpl();
						ServiceRegistration<?> reg=dev.activate(this,id);
						list_devices.put(id,dev);

						// create a service for each device, and set the
						// properties
						// for the service
						list_services.put(id,reg);
					}
				}
				if (protects_list != null) {
					for (String id : protects_list.getTopazIds()) {
						LOG.debug("Found Protect: {}", id);

						if (list_devices.containsKey(id))
							continue;
						NestDeviceImpl dev=new NestDeviceImpl();
						ServiceRegistration<?> reg=dev.activate(this,id);
						list_devices.put(id, dev);
						
						list_services.put(id, reg);
					}

				}

				// At each iteration unregister the services related to devices
				// which are not in the JSON
				// elements in list_devices -- MUST BE PRESENT into -->
				// new_th_list.getDeviceIds();

				Set<String> removed_devices = new HashSet<String>(list_devices.keySet());
				Set<String> th_set = new HashSet<>();
				Set<String> pr_set = new HashSet<>();
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

				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				LOG.debug("Terminating DiscoveryThread");
				// Thread Stopped, unregister all the services
				for (String devId : list_services.keySet()) {
					list_services.get(devId).unregister();
				}
				return;
			}
		} // end while

		LOG.debug("Terminazione Thread");
		// Thread Stopped, unregister all the services
		for (String devId : list_services.keySet()) {
			list_services.get(devId).unregister();
		}
	}

	public String get(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
