package it.telecomitalia.osgi.ah.internal.nest.lib;

public enum TemperatureModeEnum {
	
    OFF("off"),
    HEAT("heat"),
    COOL("cool");
    
    private final String value;

    TemperatureModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public String toString () {
    	return this.getValue();
    }
}
