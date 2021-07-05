package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class USINT extends UINT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public USINT(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public USINT(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public USINT(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.USINT;
	}	
}
