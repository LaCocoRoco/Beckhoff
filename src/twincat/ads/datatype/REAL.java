package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class REAL extends REAL32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public REAL(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public REAL(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public REAL(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.REAL;
	}	
}
