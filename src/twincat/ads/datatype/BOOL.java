package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class BOOL extends BIT {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BOOL(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public BOOL(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public BOOL(Ads ads, String symbolName) throws AdsException {
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
