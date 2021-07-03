package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class WORD extends UINT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public WORD(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public WORD(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public WORD(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.WORD;
	}
}
