package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;

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
	public DataType getDataType() {
		return DataType.UDINT;
	}		
}
