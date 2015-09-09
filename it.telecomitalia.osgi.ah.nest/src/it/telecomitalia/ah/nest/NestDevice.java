package it.telecomitalia.ah.nest;

public interface NestDevice {
	public void set(String key, String value);

	public Object get(String key);

	public String getId();
}

