package it.telecomitalia.ah.nest;

public interface NestDevice {
	public String set(Object json);

	public Object get(String key);

	public String getId();
}

