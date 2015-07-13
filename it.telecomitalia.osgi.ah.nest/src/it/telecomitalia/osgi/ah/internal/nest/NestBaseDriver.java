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

	public DiscoveryThread discovery=null;
	protected void activate(Map<String, String> props) {
		
		JNest jn = new JNest();
		Credentials cred = new Credentials(props.get("it.telecomitalia.osgi.ah.nest.username"),
				props.get("it.telecomitalia.osgi.ah.nest.password"));
		jn.setCredentials(cred);
		jn.login();
		
		discovery= new DiscoveryThread(jn);
		
		discovery.run();
		
		/*
		System.out.println(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);

		jn.setTemperature(22.0);

		Device device = jn.getStatusResponse().getDevices();
		System.out.println("PIPPO" + device);

		Track track = jn.getStatusResponse().getTracks();
		track.getTrack(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
		*/
	}

	/*
	private void createDevice(String id, DeviceData device_properties) {

	}
*/
	protected void deactivate(ComponentContext ctxt) {
		discovery.setTermination(true);
		discovery=null;
	}

	protected void modified(Map<String, String> props) {
		discovery.setTermination(true);
		JNest jn = new JNest();
		Credentials cred = new Credentials(props.get("it.telecomitalia.osgi.ah.nest.username"),
				props.get("it.telecomitalia.osgi.ah.nest.password"));
		jn.setCredentials(cred);
		jn.login();
		
		discovery= new DiscoveryThread(jn);
		
		discovery.run();
	}
}
