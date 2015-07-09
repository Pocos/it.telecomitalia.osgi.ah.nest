package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class TopazDeserializer implements JsonDeserializer<Topaz> {
	public Topaz deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    	Topaz topazs = new Topaz();
    	for( Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet() ) {
    		TopazData topaz = JNest.gson.fromJson(entry.getValue(), TopazData.class);
    		topazs.setTopaz(entry.getKey(), topaz);
		}
    	return topazs;
    }
}
