package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;

public class UINT8 extends Variable {
    /*********************************/
    /********** constructor **********/
    /*********************************/

	public UINT8(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.UINT8.size, symbolHandle);
	}

	public UINT8(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.UINT8.size, indexGroup, indexOffset);
	}
	
	public UINT8(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.UINT8.size, adsClient.readHandleOfSymbolName(symbolName));
	}
	
    /*********************************/
    /******** override method ********/
    /*********************************/
	
	@Override	
	public DataType getDataType() {
		return DataType.UINT8;
	}
	
	@Override
	public boolean toBoolean() {
		return UINT8.arrayToShort(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) UINT8.arrayToShort(data);
	}

	@Override
	public short toShort() {
		return (short) UINT8.arrayToShort(data);
	}

	@Override
	public int toInteger() {
		return (int) UINT8.arrayToShort(data);
	}

	@Override
	public long toLong() {
		return (long) UINT8.arrayToShort(data);
	}

	@Override
	public float toFloat() {
		return (float) UINT8.arrayToShort(data);
	}

	@Override
	public double toDouble() {
		return (double) UINT8.arrayToShort(data);
	}

	@Override
	public String toString() {
		return Short.toString(UINT8.arrayToShort(data));
	}
	
	@Override
	public Variable write(boolean value) throws AdsException {
		short data = value ? (short) 1 : (short) 0;
		super.write(UINT8.shortToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(UINT8.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(UINT8.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(UINT8.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(UINT8.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(UINT8.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(UINT8.shortToArray((short) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			short data = Short.parseShort(value);
			super.write(UINT8.shortToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

    /*********************************/
    /** public static final method ***/
    /*********************************/
	
	public static final short arrayToShort(byte[] data) {
		if (data.length != DataType.UINT8.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(data);
		byteBuffer.rewind();
		return byteBuffer.getShort();
	}
	
	public static final byte[] shortToArray(short data) {
		byte[] buffer = new byte[Short.BYTES];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putShort(data);
		return Arrays.copyOfRange(buffer, 0, DataType.UINT8.size);		
	}
}
