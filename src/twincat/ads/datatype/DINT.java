package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;

public class DINT extends INT32 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public DINT(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public DINT(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public DINT(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.DINT;
	}	
}
