package twincat.ads.constants;

public enum AdsTransmitMode {
	/*************************/
	/** constant attributes **/
	/*************************/
	
	NO_TRANSMIT        (0x0000),
	CLIENT_CYCLE       (0x0001),
	CLIENT_REQ         (0x0002),
	SERVER_CYCLE  	   (0x0003),
	SERVER_ON_CHANGE   (0x0004),
	UNKNOWN            (0xFFFF);

	/*************************/
	/*** global attributes ***/
	/*************************/	
	
	public final int value;

	/*************************/
	/****** constructor ******/
	/*************************/
	
    private AdsTransmitMode(int value) {
        this.value = value;
    }
    
	/*************************/
	/** public static final **/
	/*************************/
	  
    public static final AdsTransmitMode getByValue(int value) {
        for (AdsTransmitMode trasmitMode : AdsTransmitMode.values()) {
            if (trasmitMode.value == value) {
            	return trasmitMode;
            }
        }
        
        return AdsTransmitMode.UNKNOWN;
    } 
	
    public static final AdsTransmitMode getByString(String value) {
        for (AdsTransmitMode transmitMode : AdsTransmitMode.values()) {
            if (transmitMode.name().equalsIgnoreCase(value)) {
                return transmitMode;
            }
        }
        return AdsTransmitMode.UNKNOWN;
    } 
}
