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

	public Double getCurrentTemperature(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Double)ab.get("current_temperature");
	}
	
	public boolean notifyFrame(String message) throws Exception {
		return false;
	}

	public Double getTargetTemperature(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Double)ab.get("target_temperature");
	}

}
