package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AdsDataType;

public class INT extends INT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public INT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public INT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public INT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
				
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.INT;
	}
}
