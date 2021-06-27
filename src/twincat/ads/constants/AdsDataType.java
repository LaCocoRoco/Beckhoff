package twincat.ads.constants;

public enum AdsDataType {
	/*************************/
	/** constant attributes **/
	/*************************/

	BIT			(0x0021, 0x01),
	BOOL		(0x0021, 0x01),
	INT8 		(0x0010, 0x01),
	SINT		(0x0010, 0x01),
	UINT8		(0x0011, 0x01),
	USINT		(0x0011, 0x01),
	BYTE		(0x0011, 0x01),
	INT16		(0x0002, 0x02),
	INT			(0x0002, 0x02),
	UINT16		(0x0012, 0x02),
	UINT		(0x0012, 0x02),
	WORD		(0x0012, 0x02),
	INT32		(0x0003, 0x04),
	DINT		(0x0003, 0x04),
	UINT32		(0x0013, 0x04),
	UDINT		(0x0013, 0x04),
	DWORD		(0x0013, 0x04),
	REAL32		(0x0004, 0x04),
	REAL		(0x0004, 0x04),
	REAL64		(0x0005, 0x08),
	LREAL		(0x0005, 0x08),
	STRING		(0x001E, 0xFF),
	INT64		(0x0014, 0x00),
	UINT64		(0x0015, 0x00),
	WSTRING		(0x001F, 0x00),
	REAL80		(0x0020, 0x00),
	BIGTYPE		(0x0041, 0x00),
	MAXTYPE		(0x0043, 0x00),
	UNKNOWN		(0xFFFF, 0x00);

	/*************************/
	/*** global attributes ***/
	/*************************/	
	
	public final int value;

	public final int size;
	
	/*************************/
	/****** constructor ******/
	/*************************/
	
    private AdsDataType(int value, int size) {
        this.value = value;
        this.size = size;
    }
    
	/*************************/
	/** public static final **/
	/*************************/
	  
    public static final AdsDataType getByValue(int value) {
        for (AdsDataType dataType : AdsDataType.values()) {
            if (dataType.value == value) {
            	return dataType;
            }
        }
        
        return AdsDataType.UNKNOWN;
    } 
	
	public static final AdsDataType getByString(String value) {
		try {
			return AdsDataType.valueOf(value);
		} catch (IllegalArgumentException e) { 
			return AdsDataType.UNKNOWN;
		}
	}
}
