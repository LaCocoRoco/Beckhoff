package twincat.ads;

import twincat.ads.enums.AdsStatus;

public class AdsDeviceState {
	/*************************/
	/*** global attributes ***/
	/*************************/
	
	private AdsStatus adsState = AdsStatus.UNKNOWN;
	
	private AdsStatus devState = AdsStatus.UNKNOWN;

	/*************************/
	/**** setter & getter ****/
	/*************************/

	public AdsStatus getAdsState() {
		return adsState;
	}

	public void setAdsState(AdsStatus adsState) {
		this.adsState = adsState;
	}

	public AdsStatus getDevState() {
		return devState;
	}

	public void setDevState(AdsStatus devState) {
		this.devState = devState;
	}	
}
