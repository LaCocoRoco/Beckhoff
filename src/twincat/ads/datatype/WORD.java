package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;

public class WORD extends UINT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public WORD(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public WORD(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public WORD(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public DataType getDataType() {
		return DataType.WORD;
	}
}
