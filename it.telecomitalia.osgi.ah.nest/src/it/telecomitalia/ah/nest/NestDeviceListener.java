package it.telecomitalia.ah.nest;

/**
 * Used as protocol for the obverser pattern. It defines the method to whom getting subscribed
 * 
 *
 */
public interface NestDeviceListener {
	/**
	 * Invoked on the subscribers only when the following parameters changed on the Nest Thermostat.
	 * @param deviceId The deviceId of the current device
	 * @param current_temperature The actual temperature on the current device
	 * @param current_humidity The actual humidity on the current device
	 * @param target_temperature The desidered temperature on the current device
	 * @param away_state A boolean that says if <b>Away state</b> is enabled on the current device
	 * @return Not used
	 * @throws Exception A generic Exception raised from Jemma architecture
	 */
public boolean notifyFrame(String deviceId,
		double current_temperature,
		double current_humidity,
		double target_temperature,
		boolean away_state) throws Exception;
}
