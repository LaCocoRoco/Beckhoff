package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsDataType;

public class SINT extends INT8 {
	/*************************/
	/****** constructor ******/
	/*************************/

	public SINT(AdsClient ads, int symbolHandle) {
		super(ads, symbolHandle);
	}

	public SINT(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, indexGroup, indexOffset);
	}
	
	public SINT(AdsClient ads, String symbolName) throws AdsException {
		super(ads, symbolName);
	}
		
	/*************************/
	/******** override *******/
	/*************************/
		
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.SINT;
	}	
}
