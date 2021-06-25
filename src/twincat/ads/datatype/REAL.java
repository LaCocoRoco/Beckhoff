package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;

public class REAL extends REAL32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public REAL(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public REAL(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public REAL(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.REAL;
	}	
}
