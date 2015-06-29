package it.telecomitalia.osgi.ah.internal.nest;

import java.util.Properties;

public class Credentials {
	
	private String userName;
	private String password;
	
	/**
	 * Create an object that contains user credentials of the NEST account
	 * @param userName
	 * @param password
	 */
	public Credentials (String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Properties toProperties () {
		Properties properties = new Properties();
		properties.setProperty("username", userName);
		properties.setProperty("password", password);
		return properties;
	}

}
