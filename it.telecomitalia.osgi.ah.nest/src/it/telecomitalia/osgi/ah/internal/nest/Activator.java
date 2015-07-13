package it.telecomitalia.osgi.ah.internal.nest;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {

	private static BundleContext context;
	
	public static BundleContext getContext() {
		return context;
	}

	
	/**
	 * Establish a SSL protected connection with NEST servers by send the user credential
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Trying to interact with NEST Cloud!!");
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
