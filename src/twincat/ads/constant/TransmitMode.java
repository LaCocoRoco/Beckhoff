package twincat.ads.constant;

public enum TransmitMode {
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
	
    private TransmitMode(int value) {
        this.value = value;
    }
    
	/*************************/
	/** public static final **/
	/*************************/
	  
    public static final TransmitMode getByValue(int value) {
        for (TransmitMode trasmitMode : TransmitMode.values()) {
            if (trasmitMode.value == value) {
            	return trasmitMode;
            }
        }
        
        return TransmitMode.UNKNOWN;
    } 
	
    public static final TransmitMode getByString(String value) {
        for (TransmitMode transmitMode : TransmitMode.values()) {
            if (transmitMode.name().equalsIgnoreCase(value)) {
                return transmitMode;
            }
        }
        return TransmitMode.UNKNOWN;
    } 
}
