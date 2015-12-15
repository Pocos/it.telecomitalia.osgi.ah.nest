package it.telecomitalia.ah.nest.appliances;

import it.telecomitalia.ah.nest.NestDeviceListener;
import it.telecomitalia.ah.nest.NestEndPoint;
import it.telecomitalia.ah.nest.NestHacDevice;

import java.util.Dictionary;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IHacDevice;
import org.energy_home.jemma.ah.hac.IServiceCluster;
import org.energy_home.jemma.ah.hac.lib.DriverAppliance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestAppliance extends DriverAppliance implements NestDeviceListener {

	private static final Logger LOG = LoggerFactory.getLogger(NestAppliance.class);

	protected NestAppliance(String pid, Dictionary config) throws ApplianceException {
		super(pid, config);
	}

	@Override
	public void attach(IHacDevice device) throws ApplianceException {

		NestEndPoint serviceEndPoint = null;
		serviceEndPoint = (NestEndPoint) getEndPoint(1);
		if (serviceEndPoint == null) {
			LOG.error("attaching device but no valid end point found");
			return;
		}

		String[] clusterEndPoints = serviceEndPoint.getServiceClusterNames();
		for (int j = 0; j < clusterEndPoints.length; j++) {
			IServiceCluster serviceCluster = serviceEndPoint.getServiceCluster(clusterEndPoints[j]);

			try {
				if (serviceCluster instanceof NestServiceCluster) {
					((NestServiceCluster) serviceCluster).nestAttach((NestHacDevice) device);
				}
			} catch (Exception e) {
				LOG.error("attaching clusterEndPoint to device in ZclAppliance ", e);
				continue;
			}
		}

		LOG.debug("Attached Nest");

	}

	@Override
	public void detach(IHacDevice device) throws ApplianceException {
		LOG.debug("Detached Nest");

	}

	public NestEndPoint nestAddEndPoint(String endPointType) throws ApplianceException {
		return (NestEndPoint) this.addEndPoint(new NestEndPoint(endPointType));
	}

	public NestEndPoint nestAddEndPoint(String endPointType, int endPointId) throws ApplianceException {
		return (NestEndPoint) this.addEndPoint(new NestEndPoint(endPointType), endPointId);
	}

	public boolean notifyFrame(String deviceId, double current_temperature, double current_humidity,
			double target_temperature, boolean away_state) throws Exception {
		return false;
	}

}
