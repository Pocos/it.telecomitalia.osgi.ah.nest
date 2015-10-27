package it.telecomitalia.ah.nest;

public interface NestDeviceListener {
public boolean notifyFrame(String deviceId,
		double current_temperature,
		double current_humidity,
		double target_temperature,
		boolean away_state) throws Exception;
}
