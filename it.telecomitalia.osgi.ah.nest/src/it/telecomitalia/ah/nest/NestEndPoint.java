package it.telecomitalia.ah.nest;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.lib.EndPoint;

public class NestEndPoint extends EndPoint{

	private NestHacDevice device=null;

	public NestEndPoint(String type) throws ApplianceException{
		super(type);
	}
	
	public NestHacDevice zclGetZigBeeDevice(){
		return device;
	}
	
	public void zclSetZigBeeDevice(NestHacDevice device){
		this.device=device;
	}
}
