package it.telecomitalia.ah.nest.appliances;

import it.telecomitalia.ah.nest.NestDeviceListener;
import it.telecomitalia.ah.nest.NestHacDevice;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IServiceCluster;
import org.energy_home.jemma.ah.hac.ServiceClusterException;
import org.energy_home.jemma.ah.hac.lib.ServiceCluster;

public class NestServiceCluster extends ServiceCluster implements NestDeviceListener {
	protected NestHacDevice device;

	public NestServiceCluster() throws ApplianceException {
		super();
	}

	public NestHacDevice getDevice() {
		return device;
	}

	public void nestAttach(NestHacDevice device) {
		device.setListener(this);
		this.device = device;
	}

	public void nestDetach(NestHacDevice device) {
		device.removeListener(this);
		this.device = null;
	}

	protected IServiceCluster getSinglePeerCluster(String name) throws ServiceClusterException {
		IServiceCluster[] serviceClusters = endPoint.getPeerServiceClusters(name);

		if (serviceClusters == null) {
			throw new ServiceClusterException("No appliances connected");
		} else if (serviceClusters.length > 1) {
			// FIXME: we can relax this by checking if the command don't need
			// any response.
			// throw new
			// ServiceClusterException("More than one appliance connected.");
		} else if (serviceClusters.length == 0) {
			throw new ServiceClusterException("Service Clusters List is empty!!!");
		}
		return serviceClusters[0];
	}

	protected IServiceCluster getSinglePeerClusterNoException(String name) throws ServiceClusterException {
		IServiceCluster[] serviceClusters = endPoint.getPeerServiceClusters(name);

		if (serviceClusters == null) {
			throw new ServiceClusterException("No appliances connected");
		} else if (serviceClusters.length > 1) {
			// FIXME: we can relax this by checking if the command don't need
			// any response.
			// throw new
			// ServiceClusterException("More than one appliance connected.");
		} else if (serviceClusters.length == 0) {
			return null;
		}
		return serviceClusters[0];
	}

	public boolean notifyFrame(String deviceId, double current_temperature, double current_humidity,
			double target_temperature, boolean away_state) throws Exception {
		return false;
	}
}
