package it.telecomitalia.osgi.ah.internal.nest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private static BundleContext context;
//	private static Logger LOG = LoggerFactory.getLogger(DiscoveryThread.class);
	
	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext context) throws Exception {
		System.setProperty("https.protocols", "TLSv1"); //used to allow tls1 authentication with Nest cloud
		Activator.context = context;
	}

	public void stop(BundleContext context) throws Exception {

	}
}
