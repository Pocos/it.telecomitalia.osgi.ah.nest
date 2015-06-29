package it.telecomitalia.osgi.ah.internal.nest;


import java.nio.charset.Charset;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {

	/**
	 * Establish a SSL protected connection with NEST servers by send the user credential
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Trying to interact with NEST Cloud!!");
		JNest jn=new JNest();
		Credentials cred=new Credentials("tonicmimosa@gmail.com","mimos@123");
		jn.setCredentials(cred);
		jn.login();
		jn.getStatus();
		System.out.println(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
		jn.getStatusResponse().getDevices();
		
		jn.setTemperature(19.0);
		
		Device device=jn.getStatusResponse().getDevices();
		System.out.println(device);
		
		Track track = jn.getStatusResponse().getTracks();
		track.getDevice(jn.getStatusResponse().getMetaData().getDeviceIds()[0]);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping bundle");
		//Implement logout

	}

}
