package twincat.ads;

import twincat.ads.constant.AdsError;

public class AdsException extends Exception {
    /*********************************/
    /**** local constant variable ****/
    /*********************************/

	private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

	private AdsError adsError;

    /*********************************/
    /********** constructor **********/
    /*********************************/

	public AdsException(AdsError adsError) {
		this.adsError = adsError;
	}

	public AdsException(long adsError) {
		this.adsError = AdsError.getByValue((int) adsError);
	}

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

	public AdsError getAdsError() {
		return adsError;
	}

	public void setAdsError(AdsError adsError) {
		this.adsError = adsError;
	}

    /*********************************/
    /********* public method *********/
    /*********************************/

	public String getAdsErrorMessage() {
		return adsError.toString();
	}
}
