package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class UINT extends UINT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UINT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public UINT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public UINT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
			
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.UINT;
	}
}
