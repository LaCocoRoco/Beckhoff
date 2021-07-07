package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AdsDataType;
import twincat.ads.wrapper.Variable;

public class BIT extends Variable {
	/*************************/
	/****** constructor ******/
	/*************************/

	public BIT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, AdsDataType.BIT.size, symbolHandle);
	}

	public BIT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, AdsDataType.BIT.size, indexGroup, indexOffset);
	}
	
	public BIT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, AdsDataType.BIT.size, adsClient.readHandleOfSymbolName(symbolName));
	}

	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.BIT;
	}
	
	@Override
	public boolean toBoolean() {
		return BIT.arrayToValue(data);
	}

	@Override
	public byte toByte() {
		return BIT.arrayToValue(data) ? (byte) 1 : (byte) 0;
	}

	@Override
	public short toShort() {
		return BIT.arrayToValue(data) ? (short) 1 : (short) 0;
	}

	@Override
	public int toInteger() {
		return BIT.arrayToValue(data) ? (int) 1 : (int) 0;
	}

	@Override
	public long toLong() {
		return BIT.arrayToValue(data) ? (long) 1 : (long) 0;
	}

	@Override
	public float toFloat() {
		return BIT.arrayToValue(data) ? (float) 1 : (float) 0;
	}

	@Override
	public double toDouble() {
		return BIT.arrayToValue(data) ? (double) 1 : (double) 0;
	}

	@Override
	public String toString() {
		return Boolean.toString(BIT.arrayToValue(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		super.write(BIT.valueToArray(value));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		boolean data = Boolean.parseBoolean(value);
		super.write(BIT.valueToArray(data));
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/
	
	public static final boolean arrayToValue(byte[] data) {
		if (data.length != AdsDataType.BIT.size) return false;
		return data[0] == 1 ? true : false;
	}

	public static final byte[] valueToArray(boolean data) {
		return data ? new byte[] {1} :  new byte[] {0};
	}
}
