package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class LREAL extends REAL64 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public LREAL(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public LREAL(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public LREAL(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.LREAL;
	}	
}
