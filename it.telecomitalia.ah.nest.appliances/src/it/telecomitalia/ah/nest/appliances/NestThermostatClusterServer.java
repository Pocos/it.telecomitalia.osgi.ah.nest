package it.telecomitalia.ah.nest.appliances;

import it.telecomitalia.ah.nest.NestDevice;
import it.telecomitalia.ah.nest.NestDeviceListener;

import org.energy_home.jemma.ah.cluster.nest.general.NestThermostatServer;
import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IEndPointRequestContext;
import org.energy_home.jemma.ah.hac.lib.AttributeValue;
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
		} else
			throw new ApplianceException("Not attached");

	}

	public Boolean canCool(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("can_cool");
		} else
			throw new ApplianceException("Not attached");

	}

	public Boolean canHeat(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("can_heat");
		} else
			throw new ApplianceException("Not attached");

	}

	public Boolean hasEmergencyHeat(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("has_emer_heat");
		} else
			throw new ApplianceException("Not attached");

	}

	public Boolean hasFan(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (Boolean) ab.get("has_fan");
		} else
			throw new ApplianceException("Not attached");

	}

	public String temperatureScale(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return (String) ab.get("temperature_scale");
		} else
			throw new ApplianceException("Not attached");

	}

	public double getcurrentHumidity(IEndPointRequestContext context) throws ApplianceException {
		NestDevice ab = (NestDevice) getDevice();
		if (ab != null) {
			return ((Double) ab.get("current_humidity")).doubleValue();
		} else
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
			} else
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
		} else
			throw new ApplianceException("Not attached");
	}

	public String toggleAwayState(IEndPointRequestContext context) throws ApplianceException {
		try {
			NestDevice ab = (NestDevice) getDevice();
			if (ab != null) {
				boolean last_state = ((Boolean) ab.get("away")).booleanValue();
				JSONObject jo = new JSONObject();
				jo.put("away", !last_state);

				return (String) ab.set(jo);
			} else
				throw new ApplianceException("Not attached");

		} catch (JSONException e) {
			return e.getMessage();
			// e.printStackTrace();
		}
	}

	/**
	 * Notify Jemma that a changes is performed on the Nest Thermostat. A call
	 * of notifyAttributeValue is made for a set of predefined parameters
	 * 
	 * @param deviceId
	 *            The deviceId that uniquely identifies the thermostat
	 * @param current_temperature
	 *            The current temperature of the thermostat. Could be
	 *            represented as Celsius or Farenheit, depending on which
	 *            configuration is selected
	 * @param current_humidity
	 *            The current humidity. Expressed in percent
	 * @param target_temperature
	 *            The desidered temperature of the thermostat
	 * @param away_state
	 *            A boolean value that shows if the thermostat is set as away
	 *            (no cooling or heating, energy saver) or home (cooling and/or
	 *            heating enabled
	 */
	public boolean notifyFrame(String deviceId, double current_temperature, double current_humidity,
			double target_temperature, boolean away_state) throws Exception {
		LOG.error("Update on device:" + deviceId + "Away:" + away_state + "Current Temperature:" + current_temperature
				+ "Current Humidity:" + current_humidity + "Target Temperature:" + target_temperature);

		Double target_temp = new Double(target_temperature);
		Boolean away = new Boolean(away_state);
		Double current_hum = new Double(current_humidity);
		Double current_temp = new Double(current_temperature);

		AttributeValue away_attrValue = new AttributeValue(away, System.currentTimeMillis());
		notifyAttributeValue(NestThermostatServer.ATTR_Away_NAME, away_attrValue);

		AttributeValue current_hum_attrValue = new AttributeValue(current_hum, System.currentTimeMillis());
		notifyAttributeValue(NestThermostatServer.ATTR_currentHmidity_NAME, current_hum_attrValue);

		AttributeValue target_temp_attrValue = new AttributeValue(target_temp, System.currentTimeMillis());
		notifyAttributeValue(NestThermostatServer.ATTR_TargetTemperature_NAME, target_temp_attrValue);

		AttributeValue current_temp_attrValue = new AttributeValue(current_temp, System.currentTimeMillis());
		notifyAttributeValue(NestThermostatServer.ATTR_CurrentTemperature_NAME, current_temp_attrValue);

		return true;
	}
}
