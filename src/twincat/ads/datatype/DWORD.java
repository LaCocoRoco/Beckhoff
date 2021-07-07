package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class DWORD extends UINT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public DWORD(AdsClient adsClient, int symbolHandle) {
		super(adsClient, symbolHandle);
	}

	public DWORD(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, indexGroup, indexOffset);
	}
	
	public DWORD(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, symbolName);
	}
			
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.DWORD;
	}		
}
