package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;

public class USINT extends UINT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public USINT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public USINT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public USINT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.USINT;
	}	
}
