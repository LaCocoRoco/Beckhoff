package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class DWORD extends UINT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public DWORD(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public DWORD(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public DWORD(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
			
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.DWORD;
	}		
}
