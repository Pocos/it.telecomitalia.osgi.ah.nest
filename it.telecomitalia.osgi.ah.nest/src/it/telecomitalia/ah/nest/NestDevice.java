package it.telecomitalia.ah.nest;

public interface NestDevice {
	public void set(String name, String value);

	public String get(String name);

	public String getId();
}

