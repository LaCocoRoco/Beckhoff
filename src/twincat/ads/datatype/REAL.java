package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class REAL extends REAL32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public REAL(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public REAL(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public REAL(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.REAL;
	}	
}
