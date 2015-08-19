package it.telecomitalia.ah.nest.appliances;

import java.util.Dictionary;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IApplianceDescriptor;
import org.energy_home.jemma.ah.hac.lib.Appliance;
import org.energy_home.jemma.ah.hac.lib.ApplianceDescriptor;
import org.energy_home.jemma.ah.hac.lib.DriverApplianceFactory;
import org.osgi.service.device.Driver;

public class NestThermostatApplianceFactory extends DriverApplianceFactory implements Driver {
	public static final String APPLIANCE_TYPE = "org.energy_home.jemma.ah.nest.thermostat";
	public static final String APPLIANCE_FRIENDLY_NAME = "Nest Thermostat";
//	public static final String DEVICE_TYPE = "Nest";

	public static final IApplianceDescriptor APPLIANCE_DESCRIPTOR = new ApplianceDescriptor(APPLIANCE_TYPE, null,
			APPLIANCE_FRIENDLY_NAME);

	@Override
	public IApplianceDescriptor getDescriptor() {
		return APPLIANCE_DESCRIPTOR;
	}

	@Override
	protected Appliance getInstance(String pid, Dictionary config) throws ApplianceException {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111\n");
		return new NestThermostatAppliance(pid, config);
	}

	public String deviceMatchFilterString() {
		return "(&(DEVICE_CATEGORY=Nest)(org.nest.device.type=THERMOSTAT))";
	}
}
