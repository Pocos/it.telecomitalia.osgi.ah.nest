package it.telecomitalia.ah.nest.appliances;

import java.util.Vector;

import org.energy_home.jemma.ah.hac.lib.ApplianceFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private Vector<ApplianceFactory> applicationFactories = new Vector<ApplianceFactory>();

	static BundleContext getContext() {
		return context;
	}

	/**
	 * Start it.telecomitalia.ah.nest.appliances bundle. It adds
	 * NestThermostatApplianceFactory to applicationFactories, the list of
	 * ApplianceFactory
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		applicationFactories.add(new NestThermostatApplianceFactory());

		for (ApplianceFactory a : applicationFactories) {
			a.start(bundleContext);
		}
	}

	/**
	 * Stop it.telecomitalia.ah.nest.appliances bundle. It remove any entry from
	 * applicationFactories list
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		for (ApplianceFactory a : applicationFactories) {
			a.stop(bundleContext);
		}
		Activator.context = null;

	}

}
