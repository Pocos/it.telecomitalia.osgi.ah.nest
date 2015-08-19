package it.telecomitalia.ah.nest.appliances;

import java.util.Dictionary;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestThermostatAppliance extends NestAppliance{

	private static final Logger LOG=LoggerFactory.getLogger(NestThermostatAppliance.class);
	
	protected NestThermostatAppliance(String pid, Dictionary config) throws ApplianceException {
		super(pid, config);

		this.setAvailability(true);
	}
	
	protected void attached() {
		LOG.debug("Attached Nest Thermostat");
	}

	protected void detached() {
		LOG.debug("detached");
	}

}
