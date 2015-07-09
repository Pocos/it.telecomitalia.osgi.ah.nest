package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Credentials;
import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.DeviceData;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Topaz;
import it.telecomitalia.osgi.ah.internal.nest.lib.Track;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.component.ComponentContext;

public class NestBaseDriver {

	public Thread DiscoveryThread() = new Thread();
	protected void activate(Map<String, String> props) {
		JNest jn = new JNest();
		Credentials cred = new Credentials(props.get("it.telecomitalia.osgi.ah.nest.username"),
				props.get("it.telecomitalia.osgi.ah.nest.password"));
		jn.setCredentials(cred);
		jn.login();
		
		jn.getStatus();

		/*
		 * While the thermostats are indexed by the Device class, the Protects
		 * are indexed by the Topaz class.
		 */

		Device thermostats_list = jn.getStatusResponse().getDevices();
		Topaz protects_list = jn.getStatusResponse().getTopazs();

		if(thermostats_list ==null && protects_list==null)
		{
			//no registered devices
			return;
		}
		if (thermostats_list != null) {
			for (String id : thermostats_list.getDeviceIds()) {
				System.out.println("Found Thermostat: "+id);
				// create a service for each device
				//createDevice(id, thermostats_list.getDevice(id));
			}
		}
		if (protects_list!=null)
		{
			for (String id : protects_list.getTopazIds()) {
				System.out.println("Found Protect: "+id);
				// create a service for each device
				//createDevice(id, thermostats_list.getDevice(id));
			}
		}
		
		
		/*
		System.out.println(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);

		jn.setTemperature(22.0);

		Device device = jn.getStatusResponse().getDevices();
		System.out.println("PIPPO" + device);

		Track track = jn.getStatusResponse().getTracks();
		track.getTrack(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
		*/
	}

	private void createDevice(String id, DeviceData device_properties) {

	}

	protected void deactivate(ComponentContext ctxt) {

	}

	protected void modified(Map props) {
	}
}
