package it.telecomitalia.ah.nest.appliances;

import java.util.Dictionary;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IHacDevice;
import org.energy_home.jemma.ah.hac.lib.DriverAppliance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestAppliance extends DriverAppliance{

	private static final Logger LOG=LoggerFactory.getLogger(NestAppliance.class);
	
	protected NestAppliance(String pid, Dictionary config) throws ApplianceException {
		super(pid, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attach(IHacDevice device) throws ApplianceException {
		LOG.debug("Attached Nest");
		
	}

	@Override
	public void detach(IHacDevice device) throws ApplianceException {
		// TODO Auto-generated method stub
		
	}

}
