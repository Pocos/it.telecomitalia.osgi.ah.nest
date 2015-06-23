package it.telecomitalia.osgi.ah.internal.nest;

public enum FanModeEnum {
	
    ON("on"),
    AUTO("auto");
    
    private final String value;

    FanModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public String toString () {
    	return this.getValue();
    }
}
