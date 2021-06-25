package twincat.ads;

import twincat.ads.enums.AdsStatus;

public class AdsDeviceState {
	/*************************/
	/*** global attributes ***/
	/*************************/
	
	private AdsStatus adsState = AdsStatus.UNKNOWN;
	
	private AdsStatus deviceState = AdsStatus.UNKNOWN;

	/*************************/
	/**** setter & getter ****/
	/*************************/

	public AdsStatus getAdsState() {
		return adsState;
	}

	public void setAdsState(AdsStatus adsState) {
		this.adsState = adsState;
	}

	public AdsStatus getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(AdsStatus deviceState) {
		this.deviceState = deviceState;
	}	
}
