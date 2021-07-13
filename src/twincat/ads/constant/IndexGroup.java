package twincat.ads.constant;

public enum IndexGroup {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    SYSTEM_IP_HOST_NAME         (0x02BE, 0x0100),   // 00702
    SYSTEM_ENUM_REMOTE          (0x0323, 0x082C),   // 00803
    
    PLC_RWIB                    (0x4000, 0xFFFF),   // 16384
    PLC_RWOB                    (0x4010, 0xFFFF),   // 16400
    PLC_RWMB                    (0x4020, 0xFFFF),   // 16416
    PLC_RWRB                    (0x4030, 0xFFFF),   // 16432
    PLC_RWDB                    (0x4040, 0xFFFF),   // 16448
    
    SYMBOL_TABLE                (0xF000, 0xFFFF),   // 61440
    SYMBOL_NAME                 (0xF001, 0xFFFF),   // 61441
    SYMBOL_VAL                  (0xF002, 0xFFFF),   // 61442
    SYMBOL_HANDLE_BY_NAME       (0xF003, 0xFFFF),   // 61443
    SYMBOL_VALUE_BY_NAME        (0xF004, 0xFFFF),   // 61444
    SYMBOL_VALUE_BY_HANDLE      (0xF005, 0xFFFF),   // 61445
    SYMBOL_RELEASE_HANDLE       (0xF006, 0xFFFF),   // 61446
    SYMBOL_INFO_BY_NAME         (0xF007, 0xFFFF),   // 61447
    SYMBOL_VERSION              (0xF008, 0xFFFF),   // 61448
    SYMBOL_INFO_BYNAME_EX       (0xF009, 0xFFFF),   // 61449
    SYMBOL_DOWNLOAD             (0xF00A, 0xFFFF),   // 61450
    SYMBOL_UPLOAD               (0xF00B, 0xFFFF),   // 61451
    SYMBOL_UPLOAD_INFO          (0xF00C, 0x0008),   // 61452
    SYMBOL_DOWNLOAD_2           (0xF00D, 0xFFFF),   // 61453
    SYMBOL_DATA_TYPE_UPLOAD     (0xF00E, 0x0018),   // 61454
    SYMBOL_UPLOAD_INFO_2        (0xF00F, 0xFFFF),   // 61455
    SYMBOL_NOTE                 (0xF010, 0xFFFF),   // 61456
    
    DATA_TYPE_INFO_BY_NAME_EX   (0xF011, 0xFFFF),   // 61457
    
    IO_IMAGE_RWIB               (0xF020, 0xFFFF),   // 61472
    IO_IMAGE_RWIX               (0xF021, 0xFFFF),   // 61473
    IO_IMAGE_RWOB               (0xF030, 0xFFFF),   // 61488
    IO_IMAGE_RWOX               (0xF031, 0xFFFF),   // 61489
    IO_IMAGE_CLEARI             (0xF040, 0xFFFF),   // 61504
    IO_IMAGE_CLEARO             (0xF050, 0xFFFF),   // 61520
    
    SUM_COMMAND_READ            (0xF080, 0xFFFF),   // 61568
    SUM_COMMAND_WRITE           (0xF081, 0xFFFF),   // 61569
    SUM_COMMAND_READ_WRITE      (0xF082, 0xFFFF),   // 61570
    SUM_COMMAND_READ_EX         (0xF083, 0xFFFF),   // 61571
    SUM_COMMAND_READ_EX_2       (0xF084, 0xFFFF),   // 61572
    SUM_COMMAND_ADD_DEV_NOTE    (0xF085, 0xFFFF),   // 61573
    SUM_COMMAND_DEL_DEV_NOTE    (0xF086, 0xFFFF),   // 61574
    
    DEVICE_DATA                 (0xF100, 0xFFFF),   // 61696
    
    UNKNOWN                     (0xFFFF, 0xFFFF);   // 65535

    /*********************************/
    /******** global variable ********/
    /*********************************/

    public final int value;

    public final int size;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    private IndexGroup(int value, int size) {
        this.value = value;
        this.size = size;
    }

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final IndexGroup getByValue(int value) {
        for (IndexGroup indexGroup : IndexGroup.values()) {
            if (indexGroup.value == value) {
                return indexGroup;
            }
        }
        
        return IndexGroup.UNKNOWN;
    }
    
    public static final IndexGroup getByString(String value) {
        for (IndexGroup indexGroup : IndexGroup.values()) {
            if (indexGroup.name().equalsIgnoreCase(value)) {
                return indexGroup;
            }
        }
        return IndexGroup.UNKNOWN;
    } 
}
