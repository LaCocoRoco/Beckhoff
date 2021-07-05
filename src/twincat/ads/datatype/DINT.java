package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class DINT extends INT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public DINT(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public DINT(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public DINT(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.DINT;
	}	
}
