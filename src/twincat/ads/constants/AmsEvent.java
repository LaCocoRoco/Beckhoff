package twincat.ads.constants;

public enum AmsEvent {
    /*************************/
    /** constant attributes **/
    /*************************/
    
    ROUTERSTOP    (0x0000),
    ROUTERSTART   (0x0001),
    ROUTERREMOVED (0x0002),
    UNKNOWN       (0xFFFF); 
    
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
<<<<<<< HEAD
        for (AmsEvent event : AmsEvent.values()) {
            if (event.value == value) {
                return event;
=======
        for (AmsEvent dataType : AmsEvent.values()) {
            if (dataType.value == value) {
                return dataType;
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
            }
        }
        
        return AmsEvent.UNKNOWN;
    } 
    
    public static final AmsEvent getByString(String value) {
<<<<<<< HEAD
        for (AmsEvent event : AmsEvent.values()) {
            if (event.name().equalsIgnoreCase(value)) {
                return event;
            }
        }
        return AmsEvent.UNKNOWN;
    } 
=======
        try {
            return AmsEvent.valueOf(value);
        } catch (IllegalArgumentException e) { 
            return AmsEvent.UNKNOWN;
        }
    }
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
}
