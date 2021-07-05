package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class UDINT extends UINT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UDINT(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public UDINT(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public UDINT(AdsClient ads, String symbolName) throws AdsException {
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
