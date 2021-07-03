package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsError;
import twincat.ads.constants.AdsDataType;
import twincat.ads.wrapper.Variable;

public class INT8 extends Variable {
	/*************************/
	/****** constructor ******/
	/*************************/

	public INT8(AdsClient ads, int symbolHandle) {
		super(ads, AdsDataType.INT8.size, symbolHandle);
	}

	public INT8(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, AdsDataType.INT8.size, indexGroup, indexOffset);
	}
	
	public INT8(AdsClient ads, String symbolName) throws AdsException {
		super(ads, AdsDataType.INT8.size, ads.readHandleOfSymbolName(symbolName));
	}
	
	/*************************/
	/******** override *******/
	/*************************/

	@Override
	public AdsDataType getDataType() {
		return AdsDataType.INT8;
	}

	@Override
	public boolean toBoolean() {
		return INT8.arrayToValue(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) INT8.arrayToValue(data);
	}

	@Override
	public short toShort() {
		return (short) INT8.arrayToValue(data);
	}

	@Override
	public int toInteger() {
		return (int) INT8.arrayToValue(data);
	}

	@Override
	public long toLong() {
		return (long) INT8.arrayToValue(data);
	}

	@Override
	public float toFloat() {
		return (float) INT8.arrayToValue(data);
	}

	@Override
	public double toDouble() {
		return (double) INT8.arrayToValue(data);
	}

	@Override
	public String toString() {
		return Byte.toString(INT8.arrayToValue(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		byte data = value ? (byte) 1 : (byte) 0;
		super.write(INT8.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(INT8.valueToArray((byte) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(INT8.valueToArray((byte) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(INT8.valueToArray((byte) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(INT8.valueToArray((byte) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(INT8.valueToArray((byte) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(INT8.valueToArray((byte) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			byte data = Byte.parseByte(value);
			super.write(INT8.valueToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/

	public static final byte arrayToValue(byte[] data) {
		if (data.length != AdsDataType.INT8.size) return 0;
		return data[0];
	}

	public static final byte[] valueToArray(byte data) {
		return new byte[] { data };
	}
}
