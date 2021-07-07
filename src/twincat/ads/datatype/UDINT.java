package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;

public class UDINT extends UINT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UDINT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public UDINT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public UDINT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.UDINT;
	}		
}
