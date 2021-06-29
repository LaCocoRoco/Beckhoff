package twincat.ads.constants;

public enum AdsIndexGroup {
    /*************************/
    /** constant attributes **/
    /*************************/
    
    PLC_RWIB                    (0x4000, 0xFFFF),
    PLC_RWOB                    (0x4010, 0xFFFF),
    PLC_RWMB                    (0x4020, 0xFFFF),
    PLC_RWRB                    (0x4030, 0xFFFF),
    PLC_RWDB                    (0x4040, 0xFFFF),
    SYMBOL_TABLE                (0xF000, 0xFFFF),
    SYMBOL_NAME                 (0xF001, 0xFFFF),
    SYMBOL_VAL                  (0xF002, 0xFFFF),
    SYMBOL_HANDLE_BY_NAME       (0xF003, 0xFFFF),
    SYMBOL_VALUE_BY_NAME        (0xF004, 0xFFFF),
    SYMBOL_VALUE_BY_HANDLE      (0xF005, 0xFFFF),
    SYMBOL_RELEASE_HANDLE       (0xF006, 0xFFFF),
    SYMBOL_INFO_BY_NAME         (0xF007, 0xFFFF),
    SYMBOL_VERSION              (0xF008, 0xFFFF),
    SYMBOL_INFO_BYNAME_EX       (0xF009, 0xFFFF),
    SYMBOL_DOWNLOAD             (0xF00A, 0xFFFF),
    SYMBOL_UPLOAD               (0xF00B, 0xFFFF),
    SYMBOL_UPLOAD_INFO          (0xF00C, 0x0008),
    SYMBOL_DOWNLOAD_2           (0xF00D, 0xFFFF),
    SYMBOL_DATA_TYPE_UPLOAD     (0xF00E, 0x0024),
    SYMBOL_UPLOAD_INFO_2        (0xF00F, 0xFFFF),
    SYMBOL_NOTE                 (0xF010, 0xFFFF),
    DATA_TYPE_INFO_BY_NAME_EX   (0xF011, 0xFFFF),
    IO_IMAGE_RWIB               (0xF020, 0xFFFF),
    IO_IMAGE_RWIX               (0xF021, 0xFFFF),
    IO_IMAGE_RWOB               (0xF030, 0xFFFF),
    IO_IMAGE_RWOX               (0xF031, 0xFFFF),
    IO_IMAGE_CLEARI             (0xF040, 0xFFFF),
    IO_IMAGE_CLEARO             (0xF050, 0xFFFF),
    SUM_COMMAND_READ            (0xF080, 0xFFFF),
    SUM_COMMAND_WRITE           (0xF081, 0xFFFF),
    SUM_COMMAND_READ_WRITE      (0xF082, 0xFFFF),
    SUM_COMMAND_READ_EX         (0xF083, 0xFFFF),
    SUM_COMMAND_READ_EX_2       (0xF084, 0xFFFF),
    SUM_COMMAND_ADD_DEV_NOTE    (0xF085, 0xFFFF),
    SUM_COMMAND_DEL_DEV_NOTE    (0xF086, 0xFFFF),
    DEVICE_DATA                 (0xF100, 0xFFFF),
    UNKNOWN                     (0xFFFF, 0xFFFF);

    /*************************/
    /*** global attributes ***/
    /*************************/ 
    
    public final int value;

    public final int size;
    
    /*************************/
    /****** constructor ******/
    /*************************/
    
    private AdsIndexGroup(int value, int size) {
        this.value = value;
        this.size = size;
    }
    
    /*************************/
    /** public static final **/
    /*************************/
      
    public static final AdsIndexGroup getByValue(int value) {
<<<<<<< HEAD
        for (AdsIndexGroup indexGroup : AdsIndexGroup.values()) {
            if (indexGroup.value == value) {
                return indexGroup;
=======
        for (AdsIndexGroup dataType : AdsIndexGroup.values()) {
            if (dataType.value == value) {
                return dataType;
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
            }
        }
        
        return AdsIndexGroup.UNKNOWN;
    }
    
    public static final AdsIndexGroup getByString(String value) {
<<<<<<< HEAD
        for (AdsIndexGroup indexGroup : AdsIndexGroup.values()) {
            if (indexGroup.name().equalsIgnoreCase(value)) {
                return indexGroup;
            }
        }
        return AdsIndexGroup.UNKNOWN;
    } 
=======
        try {
            return AdsIndexGroup.valueOf(value);
        } catch (IllegalArgumentException e) { 
            return AdsIndexGroup.UNKNOWN;
        }
    }
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
}
