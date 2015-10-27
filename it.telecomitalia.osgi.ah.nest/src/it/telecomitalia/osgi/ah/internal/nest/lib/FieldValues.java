package it.telecomitalia.osgi.ah.internal.nest.lib;

/**
 * This class is used to serialize/deserialize Json for long polling request.
 * It contains the values for the field specified in object_key. It is an element of the json array of FieldContainer Class
 * @author Pocos
 *
 */
public class FieldValues {
	public String object_key;
	public long object_timestamp;
	public long object_revision;

	public FieldValues(){}
}
