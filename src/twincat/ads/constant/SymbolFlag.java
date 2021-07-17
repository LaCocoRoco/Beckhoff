package twincat.ads.constant;

public enum SymbolFlag {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

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

    /*********************************/
    /******** global variable ********/
    /*********************************/

    public final int value;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    private SymbolFlag(int value) {
        this.value = value;
    }
    
    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final SymbolFlag getByValue(int value) {
        for (SymbolFlag symbolFlag : SymbolFlag.values()) {
            if (symbolFlag.value == value) {
                return symbolFlag;
            }
        }
        
        return SymbolFlag.UNKNOWN;
    } 
    
    public static final SymbolFlag getByString(String value) {
        try {
            return SymbolFlag.valueOf(value);
        } catch (IllegalArgumentException e) { 
            return SymbolFlag.UNKNOWN;
        }
    }
}
