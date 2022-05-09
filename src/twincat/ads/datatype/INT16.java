package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;

public class INT16 extends Variable {
	/*********************************/
	/********** constructor **********/
	/*********************************/

	public INT16(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.INT16.size, symbolHandle);
	}

	public INT16(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.INT16.size, indexGroup, indexOffset);
	}
	
	public INT16(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.INT16.size, adsClient.readHandleOfSymbolName(symbolName));
	}
	
	/*********************************/
	/******** override method ********/
	/*********************************/

	@Override
	public DataType getDataType() {
		return DataType.INT16;
	}

	@Override
	public boolean toBoolean() {
		return INT16.arrayToShort(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) INT16.arrayToShort(data);
	}

	@Override
	public short toShort() {
		return (short) INT16.arrayToShort(data);
	}

	@Override
	public int toInteger() {
		return (int) INT16.arrayToShort(data);
	}

	@Override
	public long toLong() {
		return (long) INT16.arrayToShort(data);
	}

	@Override
	public float toFloat() {
		return (float) INT16.arrayToShort(data);
	}

	@Override
	public double toDouble() {
		return (double) INT16.arrayToShort(data);
	}

	@Override
	public String toString() {
		return Short.toString(INT16.arrayToShort(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		short data = value ? (short) 1 : (short) 0;
		super.write(INT16.shortToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(INT16.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(INT16.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(INT16.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(INT16.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(INT16.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(INT16.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			short data = Short.parseShort(value);
			super.write(INT16.shortToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*********************************/
	/** public static final method ***/
	/*********************************/

	public static final short arrayToShort(byte[] data) {
		if (data.length != DataType.INT16.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return byteBuffer.getShort();
	}

	public static final byte[] shortToArray(short data) {
		byte[] buffer = new byte[Short.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putShort(data);
		return buffer;
	}
}
