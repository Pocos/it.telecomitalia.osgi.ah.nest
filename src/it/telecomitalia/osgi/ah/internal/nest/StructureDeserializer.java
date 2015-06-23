package it.telecomitalia.osgi.ah.internal.nest;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class StructureDeserializer implements JsonDeserializer<Structure> {

	public Structure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		Structure structures = new Structure();
    	for( Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet() ) {
    		StructureData structure = JNest.gson.fromJson(entry.getValue(), StructureData.class);
    		structures.setStructure(entry.getKey(), structure);
		}
    	return structures;
    }
	
}
