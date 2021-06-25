package twincat.ads.datatype;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;
import twincat.ads.wrapper.Variable;

public class STRING extends Variable {
	/*************************/
	/** constant attributes **/
	/*************************/

	private static final String NULL_TERMINATION = "\u0000";

	/*************************/
	/****** constructor ******/
	/*************************/

	public STRING(Ads ads, int symbolHandle) {
		super(ads, DataType.STRING.size, symbolHandle);
	}

	public STRING(Ads ads, int symbolHandle,short dataSize) {
		super(ads, dataSize, symbolHandle);
	}

	public STRING(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, DataType.STRING.size, indexGroup, indexOffset);
	}

	public STRING(Ads ads, int indexGroup, int indexOffset, short dataSize) throws AdsException {
		super(ads, dataSize, indexGroup, indexOffset);
	}
	
	public STRING(Ads ads, String symbolName) throws AdsException {
		super(ads, DataType.STRING.size, ads.readHandleOfSymbolName(symbolName));
	}
	
	public STRING(Ads ads, String symbolName, short dataSize) throws AdsException {
		super(ads, dataSize, ads.readHandleOfSymbolName(symbolName));
	}
		
	/*************************/
	/******** override *******/
	/*************************/

	@Override
	public DataType getDataType() {
		return DataType.STRING;
	}

	@Override
	public boolean toBoolean() {
		return !STRING.arrayToValue(data).isEmpty() ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) (!STRING.arrayToValue(data).isEmpty() ? 1 : 0);
	}

	@Override
	public short toShort() {
		return (short) (!STRING.arrayToValue(data).isEmpty() ? 1 : 0);
	}

	@Override
	public int toInteger() {
		return (int) (!STRING.arrayToValue(data).isEmpty() ? 1 : 0);
	}

	@Override
	public long toLong() {
		return (long) (!STRING.arrayToValue(data).isEmpty() ? 1 : 0);
	}

	@Override
	public float toFloat() {
		return (float) (!STRING.arrayToValue(data).isEmpty() ? 1 : 0);
	}

	@Override
	public double toDouble() {
		return (double) (!STRING.arrayToValue(data).isEmpty() ? 1 : 0);
	}

	@Override
	public String toString() {
		return STRING.arrayToValue(data);
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		super.write(STRING.valueToArray(Boolean.toString(value), true));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(STRING.valueToArray(Byte.toString(value), true));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(STRING.valueToArray(Short.toString(value), true));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(STRING.valueToArray(Integer.toString(value), true));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(STRING.valueToArray(Long.toString(value), true));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(STRING.valueToArray(Float.toString(value), true));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(STRING.valueToArray(Double.toString(value), true));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		String data = value.concat(NULL_TERMINATION);
		super.write(STRING.valueToArray(data, true));
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/

	public static final String arrayToValue(byte[] data) {
		String value = new String();
		for (int i = 0; i < data.length && data[i] != 0; ++i)
			value += (char) data[i];
		return value;
	}

	public static final byte[] valueToArray(String data) {
		return STRING.valueToArray(data, false);
	}

	public static final byte[] valueToArray(String data, boolean termination) {
		if (termination) data += NULL_TERMINATION;
		return data.getBytes();
	}
}
