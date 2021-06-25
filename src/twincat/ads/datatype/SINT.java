package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;

public class SINT extends INT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public SINT(Ads ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public SINT(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public SINT(Ads ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public DataType getDataType() {
		return DataType.SINT;
	}	
}
