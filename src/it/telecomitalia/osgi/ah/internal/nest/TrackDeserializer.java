package it.telecomitalia.osgi.ah.internal.nest;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class TrackDeserializer implements JsonDeserializer<Track> {

    public Track deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    	Track track = new Track();
    	for( Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet() ) {
    		TrackData device = JNest.gson.fromJson(entry.getValue(), TrackData.class);
    		track.setDevice(entry.getKey(), device);
		}
    	return track;
    }

} 
