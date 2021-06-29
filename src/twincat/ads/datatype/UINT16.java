package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsError;
import twincat.ads.constants.AdsDataType;
import twincat.ads.wrapper.Variable;

public class UINT16 extends Variable {
	/*************************/
	/****** constructor ******/
	/*************************/

	public UINT16(Ads ads, int symbolHandle) {
		super(ads, AdsDataType.UINT16.size, symbolHandle);
	}

	public UINT16(Ads ads, int indexGroup, int indexOffset) throws AdsException {
		super(ads, AdsDataType.UINT16.size, indexGroup, indexOffset);
	}
	
	public UINT16(Ads ads, String symbolName) throws AdsException {
		super(ads, AdsDataType.UINT16.size, ads.readHandleOfSymbolName(symbolName));
	}
	
	/*************************/
	/******** override *******/
	/*************************/

	@Override
	public AdsDataType getDataType() {
		return AdsDataType.UINT16;
	}

	@Override
	public boolean toBoolean() {
		return UINT16.arrayToValue(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) UINT16.arrayToValue(data);
	}

	@Override
	public short toShort() {
		return (short) UINT16.arrayToValue(data);
	}

	@Override
	public int toInteger() {
		return (int) UINT16.arrayToValue(data);
	}

	@Override
	public long toLong() {
		return (long) UINT16.arrayToValue(data);
	}

	@Override
	public float toFloat() {
		return (float) UINT16.arrayToValue(data);
	}

	@Override
	public double toDouble() {
		return (double) UINT16.arrayToValue(data);
	}

	@Override
	public String toString() {
		return Integer.toString(UINT16.arrayToValue(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		int data = value ? (int) 1 : (int) 0;
		super.write(UINT16.valueToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(UINT16.valueToArray((int) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(UINT16.valueToArray((int) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(UINT16.valueToArray((int) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(UINT16.valueToArray((int) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(UINT16.valueToArray((int) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(UINT16.valueToArray((int) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			int data = Integer.parseInt(value);
			super.write(UINT16.valueToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.ADS_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*************************/
	/** public static final **/
	/*************************/

	public static final int arrayToValue(byte[] data) {
		if (data.length != AdsDataType.UINT16.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(data);
		byteBuffer.rewind();
		return byteBuffer.getInt();
	}

	public static final byte[] valueToArray(int data) {
		byte[] buffer = new byte[Integer.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(data);
		return Arrays.copyOfRange(buffer, 0, AdsDataType.UINT16.size);
	}
}
