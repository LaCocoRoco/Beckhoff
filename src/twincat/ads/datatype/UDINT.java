package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class UDINT extends UINT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UDINT(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public UDINT(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public UDINT(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.UDINT;
	}		
}
