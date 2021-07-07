package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class LREAL extends REAL64 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public LREAL(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public LREAL(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public LREAL(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.LREAL;
	}	
}
