package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.util.ArrayList;

/**
 * This class is used to serialize/deserialize Json for long polling request. It contains all the fields of type FieldValues 
 * @author Pocos
 *
 */
public class FieldContainer {

	public ArrayList<FieldValues> objects;
	
	public FieldContainer(ArrayList<FieldValues> fields){
		this.objects=fields;
	}
	
	public FieldContainer() {
	}

	public String session;
	public String timeout;
	
}
