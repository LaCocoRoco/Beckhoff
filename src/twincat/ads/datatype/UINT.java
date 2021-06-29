package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class UINT extends UINT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UINT(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public UINT(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public UINT(Ads ads, String symbolName) throws AdsException {
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
