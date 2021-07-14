package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AdsError;
import twincat.ads.constant.DataType;
import twincat.ads.wrapper.Variable;

public class TIME extends UINT32 {
    /*********************************/
    /**** local constant variable ****/
    /*********************************/
   
    private static final String PATTERN_TIME = "t#";

    private static final String PATTERN_MINUTE = "m";

    private static final String PATTERN_SECOND = "s";

    private static final String PATTERN_MILLISECOND = "ms";

    /*********************************/
    /********** constructor **********/
    /*********************************/

	public TIME(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public TIME(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public TIME(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
			
    /*********************************/
    /******** override method ********/
    /*********************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.TIME;
	}	
	
    @Override
    public String toString() {
        long time = UINT32.arrayToLong(data);
        return TIME.longToString(time);
    }

    @Override
    public Variable write(String value) throws AdsException {
        try  {
            long data = Long.parseLong(value);
            super.write(UINT32.longToArray(data));
        } catch(NumberFormatException e) {
            throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
        }
        return this;
    }
    

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final String longToString(long data) {
        long milliseconds = data % 1000;
        long seconds = data / 1000 % 60;
        long minutes = data / 60000;

        StringBuilder stringBuilder = new StringBuilder();

        if (minutes != 0) {
            stringBuilder.append(PATTERN_TIME);
            stringBuilder.append(minutes);
            stringBuilder.append(PATTERN_MINUTE);
            stringBuilder.append(seconds);
            stringBuilder.append(PATTERN_SECOND);
            stringBuilder.append(milliseconds);
            stringBuilder.append(PATTERN_MILLISECOND);
        } else if (seconds != 0) {
            stringBuilder.append(PATTERN_TIME);
            stringBuilder.append(seconds);
            stringBuilder.append(PATTERN_SECOND);
            stringBuilder.append(milliseconds);
            stringBuilder.append(PATTERN_MILLISECOND);
        } else if (milliseconds != 0) {
            stringBuilder.append(PATTERN_TIME);
            stringBuilder.append(milliseconds);
            stringBuilder.append(PATTERN_MILLISECOND);
        } else {
            stringBuilder.append(PATTERN_TIME);
            stringBuilder.append(0);
            stringBuilder.append(PATTERN_MILLISECOND);
        }

        return stringBuilder.toString().toUpperCase();    
    }
}
