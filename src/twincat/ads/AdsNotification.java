package twincat.ads;

import twincat.ads.constant.AdsTransmitMode;

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
	
	private AdsTransmitMode transmissionMode = AdsTransmitMode.UNKNOWN;
	
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

	public AdsTransmitMode getTransmissionMode() {
		return transmissionMode;
	}

	public void setTransmissionMode(AdsTransmitMode transmissionMode) {
		this.transmissionMode = transmissionMode;
	}
}
