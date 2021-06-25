package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsError;
import twincat.ads.enums.DataType;
import twincat.ads.wrapper.Variable;

public class INT16 extends Variable {
	/*************************/
	/****** constructor ******/
	/*************************/

	public INT16(Ads ads, int symbolHandle) {
		super(ads, DataType.INT16.size, symbolHandle);
	}

	public INT16(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, DataType.INT16.size, indexGroup, indexOffset);
	}
	
	public INT16(Ads ads, String symbolName) throws AdsException {
		super(ads, DataType.INT16.size, ads.readHandleOfSymbolName(symbolName));
	}
	
	/*************************/
	/******** override *******/
	/*************************/

	@Override
	public DataType getDataType() {
		return DataType.INT16;
	}

	@Override
	public boolean toBoolean() {
		return INT16.arrayToValue(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) INT16.arrayToValue(data);
	}

	@Override
	public short toShort() {
		return (short) INT16.arrayToValue(data);
	}

	@Override
	public int toInteger() {
		return (int) INT16.arrayToValue(data);
	}

	@Override
	public long toLong() {
		return (long) INT16.arrayToValue(data);
	}

	@Override
	public float toFloat() {
		return (float) INT16.arrayToValue(data);
	}

	@Override
	public double toDouble() {
		return (double) INT16.arrayToValue(data);
	}

	@Override
	public String toString() {
		return Short.toString(INT16.arrayToValue(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		short data = value ? (short) 1 : (short) 0;
		super.write(INT16.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(INT16.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(INT16.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(INT16.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(INT16.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(INT16.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(INT16.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			short data = Short.parseShort(value);
			super.write(INT16.valueToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.ADS_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/

	public static final short arrayToValue(byte[] data) {
		if (data.length != DataType.INT16.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return byteBuffer.getShort();
	}

	public static final byte[] valueToArray(short data) {
		byte[] buffer = new byte[Short.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putShort(data);
		return buffer;
	}
}
