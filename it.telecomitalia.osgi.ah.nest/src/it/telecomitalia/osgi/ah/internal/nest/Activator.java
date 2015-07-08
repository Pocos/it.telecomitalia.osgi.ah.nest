package it.telecomitalia.osgi.ah.internal.nest;


import it.telecomitalia.osgi.ah.internal.nest.lib.Credentials;
import it.telecomitalia.osgi.ah.internal.nest.lib.Device;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import it.telecomitalia.osgi.ah.internal.nest.lib.Track;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {

	private static BundleContext context;
	ServiceRegistration nestServiceReg = null;
	
	public static BundleContext getContext() {
		return context;
	}

	
	/**
	 * Establish a SSL protected connection with NEST servers by send the user credential
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Trying to interact with NEST Cloud!!");
		this.context=context;
//		nestServiceReg = context.registerService(NewDeviceService.class.getName(), this, null);
		}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping bundle");
//		nestServiceReg.unregister();
		//Implement logout

	}

}
