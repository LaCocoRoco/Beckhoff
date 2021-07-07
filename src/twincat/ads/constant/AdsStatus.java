package twincat.ads.constant;

public enum AdsStatus {
	/*************************/
	/** constant attributes **/
	/*************************/
	
	INVALID 		(0x0000),
	IDLE 			(0x0001),
	RESET 			(0x0002),
	INIT 			(0x0003),
	START 			(0x0004),
	RUN 			(0x0005),
	STOP 			(0x0006),
	SAVECFG 		(0x0007),
	LOADCFG 		(0x0008),
	POWERFAILURE	(0x0009),
	POWERGOOD		(0x000A),
	ERROR			(0x000B),
	SHUTDOWN		(0x000C),
	SUSPEND			(0x000D),
	RESUME			(0x000E),
	UNKNOWN			(0xFFFF);
	
	/*************************/
	/*** global attributes ***/
	/*************************/

	public final int value;

	/*************************/
	/****** constructor ******/
	/*************************/

    private AdsStatus(int value) {
        this.value = value;
    }
    
	/*************************/
	/** public static final **/
	/*************************/
	  
    public static final AdsStatus getByValue(int value) {
        for (AdsStatus status : AdsStatus.values()) {
            if (status.value == value) {
            	return status;
            }
        }
        
        return AdsStatus.UNKNOWN;
    }
    
    public static final AdsStatus getByString(String value) {
        for (AdsStatus status : AdsStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return AdsStatus.UNKNOWN;
    } 
}
