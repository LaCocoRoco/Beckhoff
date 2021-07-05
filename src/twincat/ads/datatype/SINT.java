package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;

public class SINT extends INT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public SINT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public SINT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public SINT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.SINT;
	}	
}
