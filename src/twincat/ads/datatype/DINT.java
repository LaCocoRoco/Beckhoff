package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class DINT extends INT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public DINT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public DINT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public DINT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.DINT;
	}	
}
