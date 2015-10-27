package it.telecomitalia.osgi.ah.internal.nest.lib;
/**
 * This class is used to serialize/deserialize Json for long polling request. 
 * It contains all the field value of the element FieldValues of root FieldContainer
 * FieldContainer class
 * 	|
 * timeout
 * 	|
 * session
 * 	|
 * objects
 * 		|
 * 		FieldValues class
 * 			|
 * 			object_revision
 * 			|
 * 			object_timestamp
 * 			|
 * 			object_key
 * 			|
 * 			value
 * 				|
 * 				FieldElement class
 * 					|
 * 					away
 * 					|
 * 					current_temperature
 * 					|
 * 					...
 * @author Pocos
 *
 */
public class FieldElement {
	public double target_temperature;//<from SharedData
	public double current_temperature;//<from SharedData
	public double current_humidity; //<from DeviceData
	public boolean away;//<from StructureData
}
