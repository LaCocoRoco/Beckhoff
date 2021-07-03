package twincat.ads.constants;

public enum AmsEvent {
    /*************************/
    /** constant attributes **/
    /*************************/
    
    ROUTER_STOP     (0x0000),
    ROUTER_START    (0x0001),
    ROUTERRE_MOVED  (0x0002),
    UNKNOWN         (0xFFFF); 
    
    /*************************/
    /*** global attributes ***/
    /*************************/ 
    
    public final int value;

    /*************************/
    /****** constructor ******/
    /*************************/
    
    private AmsEvent(int value) {
        this.value = value;
    }
    
    /*************************/
    /** public static final **/
    /*************************/
      
    public static final AmsEvent getByValue(int value) {
        for (AmsEvent event : AmsEvent.values()) {
            if (event.value == value) {
                return event;
            }
        }
        
        return AmsEvent.UNKNOWN;
    } 
    
    public static final AmsEvent getByString(String value) {
        for (AmsEvent event : AmsEvent.values()) {
            if (event.name().equalsIgnoreCase(value)) {
                return event;
            }
        }
        return AmsEvent.UNKNOWN;
    } 
}
