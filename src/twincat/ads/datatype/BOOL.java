package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class BOOL extends BIT {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BOOL(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public BOOL(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public BOOL(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}

	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.BOOL;
	}	
}
