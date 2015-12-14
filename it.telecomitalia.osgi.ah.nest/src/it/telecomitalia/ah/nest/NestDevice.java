package it.telecomitalia.ah.nest;
/**
 * This interface is used to provide an API for the class that wish to manage and control a Nest Device
 * 
 *
 */
public interface NestDevice {
	/**
	 * Send a message to the Nest Cloud
	 * @param json The json to send to Nest Cloud
	 * @return The return value from the Nest Cloud
	 */
	public String set(Object json);

	/**
	 * Get some parameter (by key) from Nest Cloud
	 * @param key The parameter-key used to retrieve value
	 * @return The value for the requested key
	 */
	public Object get(String key);

	/**
	 * Get the id number of the current device from Nest Cloud 
	 * @return the id number of the current device
	 */
	public String getId();
}

