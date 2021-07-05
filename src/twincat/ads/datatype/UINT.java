package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class UINT extends UINT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UINT(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public UINT(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public UINT(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
			
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.UINT;
	}
}
