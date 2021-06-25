package twincat.ads.enums;

public enum AdsTransMode {
	/*************************/
	/** constant attributes **/
	/*************************/
	
	NOTRANS 	 	(0x0000),
	CLIENTCYCLE		(0x0001),
	CLIENT1REQ 	 	(0x0002),
	SERVERCYCLE  	(0x0003),
	SERVERONCHA  	(0x0004),
	UNKNOWN			(0xFFFF);

	/*************************/
	/*** global attributes ***/
	/*************************/	
	
	public final int value;

	/*************************/
	/****** constructor ******/
	/*************************/
	
    private AdsTransMode(int value) {
        this.value = value;
    }
    
	/*************************/
	/** public static final **/
	/*************************/
	  
    public static final AdsTransMode getByValue(int value) {
        for (AdsTransMode dataType : AdsTransMode.values()) {
            if (dataType.value == value) {
            	return dataType;
            }
        }
        
        return AdsTransMode.UNKNOWN;
    } 
	
	public static final AdsTransMode getByString(String value) {
		try {
			return AdsTransMode.valueOf(value);
		} catch (IllegalArgumentException e) { 
			return AdsTransMode.UNKNOWN;
		}
	}
}
