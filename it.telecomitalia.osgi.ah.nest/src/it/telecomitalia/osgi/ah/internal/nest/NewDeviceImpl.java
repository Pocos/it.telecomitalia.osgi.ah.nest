package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Credentials;
import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Track;

public class NewDeviceImpl implements NewDeviceService {

	@Override
	public void listDevices() {
		JNest jn=new JNest();
		Credentials cred=new Credentials("tonicmimosa@gmail.com","mimos@123");
		jn.setCredentials(cred);

		jn.getStatus();
		System.out.println(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
		jn.getStatusResponse().getDevices();
		
		jn.setTemperature(19.0);
		
		Device device=jn.getStatusResponse().getDevices();
		System.out.println("PIPPO"+device);
		
		Track track = jn.getStatusResponse().getTracks();
		track.getDevice(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
		
	}

}
