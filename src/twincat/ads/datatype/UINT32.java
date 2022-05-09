package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;

public class UINT32 extends Variable {
	/*********************************/
	/********** constructor **********/
	/*********************************/

	public UINT32(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.UINT32.size, symbolHandle);
	}

	public UINT32(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.UINT32.size, indexGroup, indexOffset);
	}
	
	public UINT32(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.UINT32.size, adsClient.readHandleOfSymbolName(symbolName));
	}
	
	/*********************************/
	/******** override method ********/
	/*********************************/

	@Override
	public DataType getDataType() {
		return DataType.UINT32;
	}

	@Override
	public boolean toBoolean() {
		return UINT32.arrayToLong(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) UINT32.arrayToLong(data);
	}

	@Override
	public short toShort() {
		return (short) UINT32.arrayToLong(data);
	}

	@Override
	public int toInteger() {
		return (int) UINT32.arrayToLong(data);
	}

	@Override
	public long toLong() {
		return (long) UINT32.arrayToLong(data);
	}

	@Override
	public float toFloat() {
		return (float) UINT32.arrayToLong(data);
	}

	@Override
	public double toDouble() {
		return (double) UINT32.arrayToLong(data);
	}

	@Override
	public String toString() {
		return Long.toString(UINT32.arrayToLong(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		long data = value ? (long) 1 : (long) 0;
		super.write(UINT32.longToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(UINT32.longToArray((long) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(UINT32.longToArray((long) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(UINT32.longToArray((long) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(UINT32.longToArray((long) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(UINT32.longToArray((long) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(UINT32.longToArray((long) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			long data = Long.parseLong(value);
			super.write(UINT32.longToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*********************************/
	/** public static final method ***/
	/*********************************/

	public static final long arrayToLong(byte[] data) {
		if (data.length != DataType.UINT32.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(data);
		byteBuffer.rewind();
		return byteBuffer.getLong();
	}

	public static final byte[] longToArray(long data) {
		byte[] buffer = new byte[Long.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putLong(data);
		return Arrays.copyOfRange(buffer, 0, DataType.UINT32.size);
	}
}
