package twincat.ads.constants;

public enum AdsSymbolFlag {
    /*************************/
    /** constant attributes **/
    /*************************/
    
    NONE                (0x0000),
    PERSISTENT          (0x0001),
    BIT_VALUE           (0x0002),
    REFERENCE_TO        (0x0004),
    TYPE_GUIDE          (0x0008),
    TCOM_INTERFACE_PTR  (0x0010),
    READ_ONLY           (0x0020),
    ITF_METHOD_ACCESS   (0x0040),
    METHOD_DEREF        (0x0080),
    CONTEXT_MASK        (0x0F00),
    ATTRIBUTES          (0x1000),
    STATIC              (0x2000),
    INIT_ON_RESET       (0x4000),
    EXTENDED_FLAGS      (0x8000),
    UNKNOWN             (0xFFFF); 
    
    /*************************/
    /*** global attributes ***/
    /*************************/ 
    
    public final int value;

    /*************************/
    /****** constructor ******/
    /*************************/
    
    private AdsSymbolFlag(int value) {
        this.value = value;
    }
    
    /*************************/
    /** public static final **/
    /*************************/
      
    public static final AdsSymbolFlag getByValue(int value) {
        for (AdsSymbolFlag dataType : AdsSymbolFlag.values()) {
            if (dataType.value == value) {
                return dataType;
            }
        }
        
        return AdsSymbolFlag.UNKNOWN;
    } 
    
    public static final AdsSymbolFlag getByString(String value) {
        try {
            return AdsSymbolFlag.valueOf(value);
        } catch (IllegalArgumentException e) { 
            return AdsSymbolFlag.UNKNOWN;
        }
    }
}
