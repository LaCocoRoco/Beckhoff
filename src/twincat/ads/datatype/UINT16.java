package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;
import twincat.ads.wrapper.Variable;

public class UINT16 extends Variable {
    /*********************************/
    /********** constructor **********/
    /*********************************/

	public UINT16(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.UINT16.size, symbolHandle);
	}

	public UINT16(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.UINT16.size, indexGroup, indexOffset);
	}
	
	public UINT16(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.UINT16.size, adsClient.readHandleOfSymbolName(symbolName));
	}
	
    /*********************************/
    /******** override method ********/
    /*********************************/

	@Override
	public DataType getDataType() {
		return DataType.UINT16;
	}

	@Override
	public boolean toBoolean() {
		return UINT16.arrayToInteger(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) UINT16.arrayToInteger(data);
	}

	@Override
	public short toShort() {
		return (short) UINT16.arrayToInteger(data);
	}

	@Override
	public int toInteger() {
		return (int) UINT16.arrayToInteger(data);
	}

	@Override
	public long toLong() {
		return (long) UINT16.arrayToInteger(data);
	}

	@Override
	public float toFloat() {
		return (float) UINT16.arrayToInteger(data);
	}

	@Override
	public double toDouble() {
		return (double) UINT16.arrayToInteger(data);
	}

	@Override
	public String toString() {
		return Integer.toString(UINT16.arrayToInteger(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		int data = value ? (int) 1 : (int) 0;
		super.write(UINT16.integerToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(UINT16.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(UINT16.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(UINT16.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(UINT16.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(UINT16.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(UINT16.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			int data = Integer.parseInt(value);
			super.write(UINT16.integerToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

    /*********************************/
    /** public static final method ***/
    /*********************************/

	public static final int arrayToInteger(byte[] data) {
		if (data.length != DataType.UINT16.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(data);
		byteBuffer.rewind();
		return byteBuffer.getInt();
	}

	public static final byte[] integerToArray(int data) {
		byte[] buffer = new byte[Integer.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(data);
		return Arrays.copyOfRange(buffer, 0, DataType.UINT16.size);
	}
}
