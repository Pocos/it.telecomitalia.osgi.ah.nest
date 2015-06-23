package it.telecomitalia.osgi.ah.internal.nest;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EnumSerializer implements JsonSerializer<Enum<?>> {
	public JsonElement serialize(Enum<?> src, Type typeOfSrc, JsonSerializationContext context) {
	    return new JsonPrimitive(src.toString());
	  }
}