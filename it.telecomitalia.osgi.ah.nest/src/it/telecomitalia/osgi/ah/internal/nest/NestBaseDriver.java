package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Credentials;
import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.DeviceData;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Track;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.component.ComponentContext;

public class NestBaseDriver {

	
	
	protected void activate(Map<String,String> props) {
		JNest jn=new JNest();
		Credentials cred=new Credentials(props.get("it.telecomitalia.osgi.ah.nest.username"),props.get("it.telecomitalia.osgi.ah.nest.password"));
		jn.setCredentials(cred);
		jn.login();
		jn.getStatus();
		Device device_list=jn.getStatusResponse().getDevices();
		for (String id : device_list.getDeviceIds()){
			//create a service for each device
			createDevice(id,device_list.getDevice(id));
		}
		
		System.out.println(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
		
		
		
		
		jn.setTemperature(22.0);
		
		Device device=jn.getStatusResponse().getDevices();
		System.out.println("PIPPO"+device);
		
		Track track = jn.getStatusResponse().getTracks();
		track.getDevice(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
	}

	private void createDevice(String id, DeviceData device_properties) {
		
		
	}

	protected void deactivate(ComponentContext ctxt) {

	}

	protected void modified(Map props) {
	}
}
