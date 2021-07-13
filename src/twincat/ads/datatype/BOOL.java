package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;

public class BOOL extends BIT {
    /*********************************/
    /********** constructor **********/
    /*********************************/

	public BOOL(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public BOOL(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public BOOL(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}

    /*********************************/
    /******** override method ********/
    /*********************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.BOOL;
	}	
}
