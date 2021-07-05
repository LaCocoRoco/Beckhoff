package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsDataType;
import twincat.ads.enums.AdsError;
import twincat.ads.wrapper.Variable;

public class UINT8 extends Variable {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UINT8(AdsClient ads, int symbolHandle) {
		super(ads, AdsDataType.UINT8.size, symbolHandle);
	}

	public UINT8(AdsClient ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, AdsDataType.UINT8.size, indexGroup, indexOffset);
	}
	
	public UINT8(AdsClient ads, String symbolName) throws AdsException {
		super(ads, AdsDataType.UINT8.size, ads.readHandleOfSymbolName(symbolName));
	}
	
	/*************************/
	/******** override *******/
	/*************************/
	
	@Override	
	public AdsDataType getDataType() {
		return AdsDataType.UINT8;
	}
	
	@Override
	public boolean toBoolean() {
		return UINT8.arrayToValue(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) UINT8.arrayToValue(data);
	}

	@Override
	public short toShort() {
		return (short) UINT8.arrayToValue(data);
	}

	@Override
	public int toInteger() {
		return (int) UINT8.arrayToValue(data);
	}

	@Override
	public long toLong() {
		return (long) UINT8.arrayToValue(data);
	}

	@Override
	public float toFloat() {
		return (float) UINT8.arrayToValue(data);
	}

	@Override
	public double toDouble() {
		return (double) UINT8.arrayToValue(data);
	}

	@Override
	public String toString() {
		return Short.toString(UINT8.arrayToValue(data));
	}
	
	@Override
	public Variable write(boolean value) throws AdsException {
		short data = value ? (short) 1 : (short) 0;
		super.write(UINT8.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(UINT8.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(UINT8.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(UINT8.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(UINT8.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(UINT8.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(UINT8.valueToArray((short) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			short data = Short.parseShort(value);
			super.write(UINT8.valueToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/
	
	public static final short arrayToValue (byte[] data) {
		if (data.length != AdsDataType.UINT8.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(data);
		byteBuffer.rewind();
		return byteBuffer.getShort();
	}
	
	public static final byte[] valueToArray(short data) {
		byte[] buffer = new byte[Short.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putShort(data);
		return Arrays.copyOfRange(buffer, 0, AdsDataType.UINT8.size);		
	}	
}
