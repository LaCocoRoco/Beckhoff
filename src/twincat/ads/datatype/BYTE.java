package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;

public class BYTE extends UINT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BYTE(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public BYTE(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public BYTE(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
	
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.BYTE;
	}	
}
