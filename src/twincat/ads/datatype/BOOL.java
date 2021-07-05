package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class BOOL extends BIT {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BOOL(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public BOOL(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public BOOL(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}

	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.BOOL;
	}	
}
