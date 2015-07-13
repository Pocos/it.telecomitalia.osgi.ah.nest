package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Topaz;

public class DiscoveryThread implements Runnable {

	private JNest jn;
	private boolean termination;
	private final double TIMEOUT=5000;
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
					if (System.currentTimeMillis() > jn.getStatusResponse().getTracks().getTrack(id).last_connection + TIMEOUT) {
						System.out.println("State: OFFLINE");
					}else
						System.out.println("State: ONLINE");
					//
					// create a service for each device
					// createDevice(id, thermostats_list.getDevice(id));
				}
			}
			if (new_prot_list != null) {
				for (String id : new_prot_list.getTopazIds()) {
					System.out.println("Found Protect: " + id + " State: "
							+ jn.getStatusResponse().getWidget_track().getWidget_track(id).online);
					// create a service for each device
				}

			}
		}

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
	}

	private synchronized boolean getTermination() {
		return termination;
	}

	public synchronized void setTermination(boolean new_value) {
		termination = new_value;
	}

}
