package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class Widget_trackDeserializer implements JsonDeserializer<Widget_track>{
	public Widget_track deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		Widget_track widgets = new Widget_track();
    	for( Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet() ) {
    		Widget_trackData widget = JNest.gson.fromJson(entry.getValue(), Widget_trackData.class);
    		widgets.setWidget_track(entry.getKey(), widget);
		}
    	return widgets;
    }
}
