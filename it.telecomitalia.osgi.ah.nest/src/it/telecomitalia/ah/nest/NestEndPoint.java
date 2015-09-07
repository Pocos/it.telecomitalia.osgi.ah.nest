package it.telecomitalia.ah.nest;

import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.lib.EndPoint;

public class NestEndPoint extends EndPoint{

	public NestEndPoint(String type) throws ApplianceException{
		super(type);
	}
}
