package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class SharedDeserializer implements JsonDeserializer<Shared>{
	
	public Shared deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		Shared shareds = new Shared();
    	for( Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet() ) {
    		SharedData shared = JNest.gson.fromJson(entry.getValue(), SharedData.class);
    		shareds.setShared(entry.getKey(), shared);
		}
    	return shareds;
    }
}
