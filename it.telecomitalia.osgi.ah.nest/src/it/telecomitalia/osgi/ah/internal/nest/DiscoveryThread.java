package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Topaz;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class DiscoveryThread implements Runnable, NestDevice {

	private JNest jn;
	private boolean termination;
	private final double TIMEOUT=5000;
	private Map<String,NestDevice> list_devices=new HashMap<String,NestDevice>();
	private Map<String,ServiceRegistration<?>> list_services=new HashMap<String,ServiceRegistration<?>>();
	public DiscoveryThread(JNest jn) {
		this.jn = jn;
		this.termination = false;

	}

	@Override
	public void run() {
		while (getTermination() != true) {
			jn.getStatus();

			/*
			 * While the thermostats are indexed by the Device class, the
			 * Protects are indexed by the Topaz class.
			 */

			Device new_th_list = jn.getStatusResponse().getDevices();
			Topaz new_prot_list = jn.getStatusResponse().getTopazs();
			// REMEMBER: the online field of the JSONs is updated every 3/4
			// minutes. Needed a much lesser latency.
			// I should compare the last connection timestamp to the current
			// timestamp. There are no last_connection fields
			// for the Nest Protect
			if (new_th_list != null) {
				for (String id : new_th_list.getDeviceIds()) {
					System.out.println("Found Thermostat: " + id);
					
					/*if (System.currentTimeMillis() > jn.getStatusResponse().getTracks().getTrack(id).last_connection + TIMEOUT) {
						System.out.println("State: OFFLINE");
					}else
						System.out.println("State: ONLINE");
					*/
					
					//add the device to a list
					if(list_devices.containsKey(id))
						continue;
					list_devices.put(id, new NestDeviceImpl(this,id));
					
					// create a service for each device, and set the properties for the service
					Dictionary<String,Object> props=new Hashtable<String,Object>();
					props.put(id,jn.getStatusResponse().getTracks().getTrack(id).online);
					ServiceRegistration<?> sReg = Activator.getContext().registerService(NestDevice.class.getName(), this, props);
					list_services.put(id, sReg);
				}
			}
			if (new_prot_list != null) {
				for (String id : new_prot_list.getTopazIds()) {
					System.out.println("Found Protect: " + id + " State: "
							+ jn.getStatusResponse().getWidget_track().getWidget_track(id).online);
					// create a service for each device
				}

			}
			
			//At each iteration unregister the services related to devices which are not in the JSON
			// elements in list_devices -- MUST BE PRESENT into --> new_th_list.getDeviceIds(); 
			
			for (Map.Entry<String, NestDevice> dev : list_devices.entrySet())
			{
				int found_id=0;
				for(String real_id:new_th_list.getDeviceIds()){
					if(real_id.equals(dev.getKey())){
						found_id=1;
						break;
					}
				}
				if(found_id!=1){
					list_devices.remove(dev.getKey());
					list_services.get(dev.getKey()).unregister();
					list_services.remove(dev.getKey());
				}
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
		
		System.out.println("Terminazione Thread");
		//Thread Stopped, unregister all the services
		for (Map.Entry<String, ServiceRegistration<?>> dev : list_services.entrySet())
		{
				list_services.get(dev.getKey()).unregister();
		}
	}

	private synchronized boolean getTermination() {
		return termination;
	}

	public synchronized void setTermination(boolean new_value) {
		termination = new_value;
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
