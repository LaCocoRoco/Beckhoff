package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class LREAL extends REAL64 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public LREAL(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public LREAL(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public LREAL(AdsClient ads, String symbolName) throws AdsException {
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
