package it.telecomitalia.ah.nest.appliances;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.ah.nest.NestDeviceListener;

import org.energy_home.jemma.ah.cluster.nest.general.ThermostatServer;
import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IEndPointRequestContext;

public class NestTemperatureMeasurementServer extends NestServiceCluster implements ThermostatServer,NestDeviceListener {

	public NestTemperatureMeasurementServer() throws ApplianceException {
		super();
	}

	public int getTemperatureValue(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		ab.set("prova", "prova2");
		int a = 1;
		a += 1;
		return 0;
	}
	
	public boolean notifyFrame(String message) throws Exception {
		return false;
	}

}
