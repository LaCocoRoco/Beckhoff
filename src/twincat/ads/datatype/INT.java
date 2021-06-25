package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;

public class INT extends INT16 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public INT(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public INT(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public INT(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
				
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public DataType getDataType() {
		return DataType.INT;
	}
}
