package twincat.ads;

import twincat.ads.enums.AdsTransMode;

public class AdsNotification {
	/*************************/
	/** constant attributes **/
	/*************************/
	
	public static final int TIME_RATIO_NS_TO_MS = 10000;
	
	/*************************/
	/*** global attributes ***/
	/*************************/	
	
	private long dataLength = 0;

	private long maxDelay = 0;
	
	private long cycleTime = 0;
	
	private AdsTransMode transmissionMode = AdsTransMode.UNKNOWN;
	
	/*************************/
	/**** setter & getter ****/
	/*************************/

	public long getDataLength() {
		return dataLength;
	}

	public void setDataLength(long dataLength) {
		this.dataLength = dataLength;
	}
	
	public long getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(long maxDelay) {
		this.maxDelay = maxDelay;
	}

	public long getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(long cycleTime) {
		this.cycleTime = cycleTime;
	}

	public AdsTransMode getTransmissionMode() {
		return transmissionMode;
	}

	public void setTransmissionMode(AdsTransMode transmissionMode) {
		this.transmissionMode = transmissionMode;
	}
}
