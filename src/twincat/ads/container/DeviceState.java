package twincat.ads.container;

import twincat.ads.constant.State;

public class DeviceState {
	/*************************/
	/*** global attributes ***/
	/*************************/
	
	private State adsState = State.UNKNOWN;
	
	private State devState = State.UNKNOWN;

	/*************************/
	/**** setter & getter ****/
	/*************************/

	public State getAdsState() {
		return adsState;
	}

	public void setAdsState(State adsState) {
		this.adsState = adsState;
	}

	public State getDevState() {
		return devState;
	}

	public void setDevState(State devState) {
		this.devState = devState;
	}	
}
