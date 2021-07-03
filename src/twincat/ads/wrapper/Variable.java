package twincat.ads.wrapper;

import java.util.Observable;

import twincat.ads.AdsClient;
import twincat.ads.AdsCallback;
import twincat.ads.AdsException;
import twincat.ads.AdsNotification;
import twincat.ads.constants.AdsIndexGroup;
import twincat.ads.constants.AdsTransmitMode;
import twincat.ads.constants.AdsDataType;

public abstract class Variable extends Observable implements AdsCallback {
	/*************************/
	/*** global attributes ***/
	/*************************/

	private long timeStamp = 0;

	/*************************/
	/*** local attributes ***/
	/*************************/

	private long notification = 0;

	private int symbolHandle = 0;

	private int indexGroup = 0;
	
	private int indexOffset = 0;

	private final AdsClient ads;

	protected final byte[] data;
	
	/*************************/
	/****** constructor ******/
	/*************************/

	public Variable(AdsClient ads, int dataSize, int symbolHandle) {
		this.ads = ads;
		this.data = new byte[dataSize];
		this.symbolHandle = symbolHandle;
	}

	public Variable(AdsClient ads, int dataSize, int indexGroup, int indexOffset) {
		this.ads = ads;
		this.data = new byte[dataSize];
		this.indexGroup = indexGroup;
		this.indexOffset = indexOffset;
	}	
	
	/*************************/
	/**** setter & getter ****/
	/*************************/

	public long getTimeStamp() {
		return timeStamp;
	}
	
	/*************************/
	/********* public ********/
	/*************************/

	@Override
	public void update(long notification, long timeStampe, byte[] data) {
		if (notification == this.notification) {
			System.arraycopy(data, 0, this.data, 0, this.data.length);
			timeStamp = timeStampe / AdsNotification.TIME_RATIO_NS_TO_MS;

			setChanged();
			notifyObservers();
		}
	}

	public Variable read() throws AdsException {
		if (symbolHandle != 0) readBySymbol();
		else readByAddress();
		return this;
	}

	public void addNotification(int intervall) throws AdsException {
		if (notification != 0) return;
		if (symbolHandle != 0) addNotificationBySymbol(intervall);
		else addNotificationByAddress(intervall);
	}

	public void close() throws AdsException {
		removeHandle();
		removeNotification();
	}
	
	/*************************/
	/******* protected *******/
	/*************************/	

	protected void write(byte[] data) throws AdsException {
		if (symbolHandle != 0) writeBySymbole(data);
		else writeByAddress(data);
	}

	/*************************/
	/******** private ********/
	/*************************/

	private void readByAddress() throws AdsException {
		ads.read(indexGroup, indexOffset, data);		
	}
	
	private void readBySymbol() throws AdsException {
		ads.readBySymbolHandle(symbolHandle, data);
	}
	
	private void writeByAddress(byte[] data) throws AdsException {
		ads.write(indexGroup, indexOffset, data);			
	}
	
	private void writeBySymbole(byte[] data) throws AdsException  {
		ads.writeBySymbolHandle(symbolHandle, data);	
	}

	private void addNotificationByAddress(int intervall) throws AdsException {
		AdsNotification adsNotification = new AdsNotification();
		adsNotification.setDataLength(data.length);
		adsNotification.setTransmissionMode(AdsTransmitMode.SERVER_CYCLE);
		adsNotification.setCycleTime(intervall * AdsNotification.TIME_RATIO_NS_TO_MS);
		
		notification = ads.addDeviceNotification(indexGroup, indexOffset, adsNotification, this);	
	}

	private void addNotificationBySymbol(int intervall) throws AdsException {
		AdsNotification adsNotification = new AdsNotification();
		adsNotification.setDataLength(data.length);
		adsNotification.setTransmissionMode(AdsTransmitMode.SERVER_CYCLE);
		adsNotification.setCycleTime(intervall * AdsNotification.TIME_RATIO_NS_TO_MS);
		
		long indexGroup = AdsIndexGroup.SYMBOL_VALUE_BY_HANDLE.value;
		notification = ads.addDeviceNotification(indexGroup, symbolHandle, adsNotification, this);
	}

	private void removeNotification() throws AdsException {
		if (notification != 0) {
			ads.deleteDeviceNotification(notification, this);
			notification = 0;
		}
	}

	private void removeHandle() throws AdsException {
		if (symbolHandle != 0) {
			ads.writeReleaseSymbolHandle(symbolHandle);
			symbolHandle = 0;
		}
	}

	/*************************/
	/****** abstraction ******/
	/*************************/

	public abstract Variable write(boolean value) throws AdsException;

	public abstract Variable write(byte value) throws AdsException;

	public abstract Variable write(short value) throws AdsException;

	public abstract Variable write(int value) throws AdsException;

	public abstract Variable write(long value) throws AdsException;

	public abstract Variable write(float value) throws AdsException;

	public abstract Variable write(double value) throws AdsException;

	public abstract Variable write(String value) throws AdsException;

	public abstract AdsDataType getDataType();

	public abstract boolean toBoolean();

	public abstract byte toByte();

	public abstract short toShort();

	public abstract int toInteger();

	public abstract long toLong();

	public abstract float toFloat();

	public abstract double toDouble();

	public abstract String toString();
}
