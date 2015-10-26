package it.telecomitalia.ah.nest.appliances;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.ah.nest.NestDeviceListener;

import org.energy_home.jemma.ah.cluster.nest.general.NestThermostatServer;
import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IEndPointRequestContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestThermostatClusterServer extends NestServiceCluster implements NestThermostatServer, NestDeviceListener {

	private static final Logger LOG = LoggerFactory.getLogger(NestThermostatClusterServer.class);
	
	public NestThermostatClusterServer() throws ApplianceException {
		super();
	}

	public double getCurrentTemperature(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return ((Double) ab.get("current_temperature")).doubleValue();
		} else
			throw new ApplianceException("Not attached");
	}

	public double getTargetTemperature(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return ((Double) ab.get("target_temperature")).doubleValue();
		}else
			throw new ApplianceException("Not attached");
		
	}

	public Boolean canCool(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("can_cool");
		}else
			throw new ApplianceException("Not attached");
		
	}

	public Boolean canHeat(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("can_heat");
		}else
			throw new ApplianceException("Not attached");
		
	}

	public Boolean hasEmergencyHeat(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("has_emer_heat");
		}else
			throw new ApplianceException("Not attached");
		
	}

	public Boolean hasFan(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("has_fan");
		}else
			throw new ApplianceException("Not attached");
		
	}

	public String temperatureScale(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (String) ab.get("temperature_scale");
		}else
			throw new ApplianceException("Not attached");
		
	}

	public double getcurrentHumidity(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return ((Double) ab.get("current_humidity")).doubleValue();
		}else
			throw new ApplianceException("Not attached");
		
	}

	public String setTargetTemperature(double targetTemp, IEndPointRequestContext context) throws ApplianceException {
		try {
			NestDevice ab = (NestDevice) getDevice();
			if (ab != null) {
				JSONObject jo = new JSONObject();
				jo.put("target_change_pending", true);
				jo.put("target_temperature", targetTemp);
				
				return (String) ab.set(jo);
			}else
				throw new ApplianceException("Not attached");
				
			
		} catch (JSONException e) {
			return e.getMessage();
			// e.printStackTrace();
		}
	}

	public boolean getAwayState(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return ((Boolean) ab.get("away")).booleanValue();
		}else
			throw new ApplianceException("Not attached");
	}
	
	public String toggleAwayState(IEndPointRequestContext context) throws ApplianceException {
		try {
			NestDevice ab = (NestDevice) getDevice();
			if (ab != null) {
				boolean last_state=((Boolean) ab.get("away")).booleanValue();
				JSONObject jo = new JSONObject();
				jo.put("away", !last_state);
				
				return (String) ab.set(jo);
			}else
				throw new ApplianceException("Not attached");
				
			
		} catch (JSONException e) {
			return e.getMessage();
			// e.printStackTrace();
		}
	}
	
	public boolean notifyFrame(String message) throws Exception {
		LOG.error(message);
		return true;
	}
}
