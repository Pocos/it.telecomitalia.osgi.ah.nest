package it.telecomitalia.osgi.ah.internal.nest;

import java.util.HashMap;

public class Structure {

	public HashMap<String, StructureData> structures = new HashMap<String, StructureData>();
	public Structure () {}
	
	public StructureData getStructure(String structureId) {
		return structures.get(structureId);
	}

	public StructureData createStructure(String key) {
		StructureData structure = new StructureData();
		setStructure(key, structure);
		return structure;
	}
	
	public void setStructure(String key, StructureData structure) {
		structures.put(key, structure);
	}
	
	public String[] getStructureIds () {
		String[] a = new String[]{};
		return structures.keySet().toArray(a);
	}
	
}
