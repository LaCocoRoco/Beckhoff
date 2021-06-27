package twincat.ads;

import twincat.ads.constants.AdsError;

public class AdsException extends Exception {
	/*************************/
	/** constant attributes **/
	/*************************/	
	
	private static final long serialVersionUID = 1L;

	/*************************/
	/*** global attributes ***/
	/*************************/	

	private AdsError adsError;

	/*************************/
	/****** constructor ******/
	/*************************/
	
	public AdsException(AdsError adsError) {
		this.adsError = adsError;
	}

	public AdsException(long adsError) {
		this.adsError = AdsError.getByValue((int) adsError);
	}

	/*************************/
	/**** setter & getter ****/
	/*************************/
	
	public AdsError getAdsError() {
		return adsError;
	}

	public void setAdsError(AdsError adsError) {
		this.adsError = adsError;
	}

	/*************************/
	/********* public ********/
	/*************************/	
		
	public String getAdsErrorMessage() {
		return adsError.toString();
	}
}
