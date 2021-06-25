package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsError;
import twincat.ads.enums.DataType;
import twincat.ads.wrapper.Variable;

public class REAL32 extends Variable {
	/*************************/
	/****** constructor ******/
	/*************************/

	public REAL32(Ads ads, int symbolHandle) {
		super(ads, DataType.REAL32.size, symbolHandle);
	}

	public REAL32(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, DataType.REAL32.size, indexGroup, indexOffset);
	}
	
	public REAL32(Ads ads, String symbolName) throws AdsException {
		super(ads, DataType.REAL32.size, ads.readHandleOfSymbolName(symbolName));
	}
	
	/*************************/
	/******** override *******/
	/*************************/

	@Override
	public DataType getDataType() {
		return DataType.REAL32;
	}

	@Override
	public boolean toBoolean() {
		return arrayToValue(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) arrayToValue(data);
	}

	@Override
	public short toShort() {
		return (short) arrayToValue(data);
	}

	@Override
	public int toInteger() {
		return (int) arrayToValue(data);
	}

	@Override
	public long toLong() {
		return (long) arrayToValue(data);
	}

	@Override
	public float toFloat() {
		return (float) arrayToValue(data);
	}

	@Override
	public double toDouble() {
		return (double) arrayToValue(data);
	}

	@Override
	public String toString() {
		return Float.toString(REAL32.arrayToValue(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		super.write(REAL32.valueToArray(value ? (float) 1 : (float) 0));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(REAL32.valueToArray((float) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(REAL32.valueToArray((float) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(REAL32.valueToArray((float) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(REAL32.valueToArray((float) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(REAL32.valueToArray((float) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(REAL32.valueToArray((float) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			float data = Float.parseFloat(value);
			super.write(REAL32.valueToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.ADS_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/

	public static final float arrayToValue(byte[] data) {
		if (data.length != DataType.REAL32.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return byteBuffer.getFloat();
	}

	public static final byte[] valueToArray(float data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(DataType.REAL32.size);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(Float.floatToRawIntBits(data));
		return byteBuffer.array();
	}
}
