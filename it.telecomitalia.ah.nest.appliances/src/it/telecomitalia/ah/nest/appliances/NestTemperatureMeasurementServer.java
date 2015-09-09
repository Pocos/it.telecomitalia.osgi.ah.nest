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

	public Boolean canCool(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Boolean)ab.get("can_cool");
	}

	public Boolean canHeat(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Boolean)ab.get("can_heat");
	}

	public Boolean hasEmergencyHeat(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Boolean)ab.get("has_emer_heat");
	}

	public Boolean hasFan(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Boolean)ab.get("has_fan");
	}

	public String temperatureScale(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (String)ab.get("temperature_scale");
	}

	public Double currentHmidity(IEndPointRequestContext context) throws ApplianceException {
		context.getClass().getCanonicalName();
		NestDevice ab=(NestDevice)getDevice();
		return (Double)ab.get("current_humidity");
	}


}
