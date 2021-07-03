package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class BYTE extends UINT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BYTE(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public BYTE(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public BYTE(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
	
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.BYTE;
	}	
}
