package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class TrackDeserializer implements JsonDeserializer<Track> {

    public Track deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    	Track tracks = new Track();
    	for( Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet() ) {
    		TrackData track = JNest.gson.fromJson(entry.getValue(), TrackData.class);
    		tracks.setTrack(entry.getKey(), track);
		}
    	return tracks;
    }

} 
