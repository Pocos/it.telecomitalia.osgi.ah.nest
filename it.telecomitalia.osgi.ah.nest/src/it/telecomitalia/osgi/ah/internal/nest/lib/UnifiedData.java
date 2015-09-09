package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.lang.reflect.Field;
import java.util.List;

public class UnifiedData {

	public List<String> unified_list=null;
	
	public UnifiedData(String deviceId, StatusResponse statusResponse) {
		Field [] fields=DeviceData.class.getDeclaredFields();
		unified_list.add(fields.toString());
		fields=MetaDataData.class.getDeclaredFields();
		unified_list.add(fields.toString());
		fields=SharedData.class.getDeclaredFields();
		unified_list.add(fields.toString());
		fields=StructureData.class.getDeclaredFields();
		unified_list.add(fields.toString());
		fields=TrackData.class.getDeclaredFields();
		unified_list.add(fields.toString());
		fields=TopazData.class.getDeclaredFields();
		unified_list.add(fields.toString());
		/*
		DeviceData deviceData= statusResponse.getDevices().getDevice(deviceId);
		MetaDataData metadataData= statusResponse.getMetaData().getDevice(deviceId);
		SharedData sharedData= statusResponse.getShareds().getDevice(deviceId);
		StructureData structureData= statusResponse.getStructures().getStructure(deviceId);
		TrackData trackData=statusResponse.getTracks().getTrack(deviceId);
		TopazData topazData=statusResponse.getTopazs().getTopaz(deviceId);*/
	}

}
