package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;

public class INT32 extends Variable {
    /*********************************/
    /********** constructor **********/
    /*********************************/

	public INT32(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.INT32.size, symbolHandle);
	}

	public INT32(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.INT32.size, indexGroup, indexOffset);
	}
	
	public INT32(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.INT32.size, adsClient.readHandleOfSymbolName(symbolName));
	}
	
    /*********************************/
    /******** override method ********/
    /*********************************/

	@Override
	public DataType getDataType() {
		return DataType.INT32;
	}

	@Override
	public boolean toBoolean() {
		return INT32.arrayToInteger(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) INT32.arrayToInteger(data);
	}

	@Override
	public short toShort() {
		return (short) INT32.arrayToInteger(data);
	}

	@Override
	public int toInteger() {
		return (int) INT32.arrayToInteger(data);
	}

	@Override
	public long toLong() {
		return (long) INT32.arrayToInteger(data);
	}

	@Override
	public float toFloat() {
		return (float) INT32.arrayToInteger(data);
	}

	@Override
	public double toDouble() {
		return (double) INT32.arrayToInteger(data);
	}

	@Override
	public String toString() {
		return Integer.toString(INT32.arrayToInteger(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		int data = value ? (int) 1 : (int) 0;
		super.write(INT32.integerToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(INT32.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(INT32.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(INT32.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(INT32.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(INT32.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(INT32.integerToArray((int) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			int data = Integer.parseInt(value);
			super.write(INT32.integerToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

    /*********************************/
    /** public static final method ***/
    /*********************************/

	public static final int arrayToInteger(byte[] data) {
		if (data.length != DataType.INT32.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return byteBuffer.getInt();
	}

	public static final byte[] integerToArray(int data) {
		byte[] buffer = new byte[Integer.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(data);
		return buffer;
	}
}
