package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;

public class BYTE extends UINT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BYTE(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public BYTE(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public BYTE(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
	
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.BYTE;
	}	
}
