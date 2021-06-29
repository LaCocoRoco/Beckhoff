package twincat.ads.constants;

public enum AdsDataTypeFlag {
    /*************************/
    /** constant attributes **/
    /*************************/
    
    DATA_TYPE           (0x000001),
    DATA_ITEM           (0x000002),
    REFERENCE_TO        (0x000004),
    METHOD_DEREF        (0x000008),
    OVERSAMPLE          (0x000010),
    BIT_VALUES          (0x000020),
    PROP_ITEM           (0x000040),
    TYPE_GUIDE          (0x000080),
    PERSISTENT          (0x000100),
    COPY_MASK           (0x000200),
    TCOM_INTERFACE_PTR  (0x000400),
    METHOD_INFOS        (0x000800),
    ATTRIBUTES          (0x001000),
    ENUM_INFOS          (0x002000), 
    ALIGNED             (0x010000),
    STATIC              (0x020000),
    SP_LEVELS           (0x040000),
    IGNORE_PERSIST      (0x080000),
    ANY_SIZE_ARRAY      (0x100000),
    PERSISTANT_DATATYPE (0x200000),
    INIT_ON_RESULT      (0x400000),
    UNKNOWN             (0xFFFFFF); 
    
    /*************************/
    /*** global attributes ***/
    /*************************/ 
    
    public final int value;

    /*************************/
    /****** constructor ******/
    /*************************/
    
    private AdsDataTypeFlag(int value) {
        this.value = value;
    }
    
    /*************************/
    /** public static final **/
    /*************************/
      
    public static final AdsDataTypeFlag getByValue(int value) {
<<<<<<< HEAD
        for (AdsDataTypeFlag dataTypeFlag : AdsDataTypeFlag.values()) {
            if (dataTypeFlag.value == value) {
                return dataTypeFlag;
=======
        for (AdsDataTypeFlag dataType : AdsDataTypeFlag.values()) {
            if (dataType.value == value) {
                return dataType;
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
            }
        }
        
        return AdsDataTypeFlag.UNKNOWN;
    } 
<<<<<<< HEAD
   
    public static final AdsDataTypeFlag getByString(String value) {
        for (AdsDataTypeFlag dataTypeFlag : AdsDataTypeFlag.values()) {
            if (dataTypeFlag.name().equalsIgnoreCase(value)) {
                return dataTypeFlag;
            }
        }
        return AdsDataTypeFlag.UNKNOWN;
    }   
=======
    
    public static final AdsDataTypeFlag getByString(String value) {
        try {
            return AdsDataTypeFlag.valueOf(value);
        } catch (IllegalArgumentException e) { 
            return AdsDataTypeFlag.UNKNOWN;
        }
    }
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
}
