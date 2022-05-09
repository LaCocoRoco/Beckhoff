package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;

public class UINT extends UINT16 {
	/*********************************/
	/********** constructor **********/
	/*********************************/

	public UINT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public UINT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public UINT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
			
	/*********************************/
	/******** override method ********/
	/*********************************/
	
	@Override	
	public DataType getDataType() {
		return DataType.UINT;
	}
}
