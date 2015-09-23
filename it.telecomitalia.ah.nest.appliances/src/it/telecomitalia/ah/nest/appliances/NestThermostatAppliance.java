package it.telecomitalia.ah.nest.appliances;

import it.telecomitalia.ah.nest.NestEndPoint;

import java.util.Dictionary;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IEndPointTypes;
import org.energy_home.jemma.ah.hac.IHacDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestThermostatAppliance extends NestAppliance{

	private NestEndPoint endPoint1=null;
	
	private static final Logger LOG=LoggerFactory.getLogger(NestThermostatAppliance.class);
	
	protected NestThermostatAppliance(String pid, Dictionary config) throws ApplianceException {
		super(pid, config);
		
		this.setAvailability(true);
		endPoint1=this.nestAddEndPoint(IEndPointTypes.NEST_THERMOSTAT);
		endPoint1.addServiceCluster(new NestTemperatureMeasurementServer());
	}
	
	
	/*@Override
	public void attach(IHacDevice device) throws ApplianceException {
		LOG.debug("Attached Nest Thermostat");
	}

	@Override
	public void detach(IHacDevice device) throws ApplianceException {
		LOG.debug("Detached Nest Thermostat");
	}*/
}
