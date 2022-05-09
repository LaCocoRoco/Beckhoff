package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;

public class REAL32 extends Variable {
	/*********************************/
	/********** constructor **********/
	/*********************************/

	public REAL32(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.REAL32.size, symbolHandle);
	}

	public REAL32(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.REAL32.size, indexGroup, indexOffset);
	}
	
	public REAL32(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.REAL32.size, adsClient.readHandleOfSymbolName(symbolName));
	}
	
	/*********************************/
	/******** override method ********/
	/*********************************/

	@Override
	public DataType getDataType() {
		return DataType.REAL32;
	}

	@Override
	public boolean toBoolean() {
		return arrayToFloat(data) > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) arrayToFloat(data);
	}

	@Override
	public short toShort() {
		return (short) arrayToFloat(data);
	}

	@Override
	public int toInteger() {
		return (int) arrayToFloat(data);
	}

	@Override
	public long toLong() {
		return (long) arrayToFloat(data);
	}

	@Override
	public float toFloat() {
		return (float) arrayToFloat(data);
	}

	@Override
	public double toDouble() {
		return (double) arrayToFloat(data);
	}

	@Override
	public String toString() {
		return Float.toString(REAL32.arrayToFloat(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		super.write(REAL32.floatToArray(value ? (float) 1 : (float) 0));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(REAL32.floatToArray((float) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(REAL32.floatToArray((float) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(REAL32.floatToArray((float) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(REAL32.floatToArray((float) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(REAL32.floatToArray((float) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(REAL32.floatToArray((float) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		try  {
			float data = Float.parseFloat(value);
			super.write(REAL32.floatToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

	/*********************************/
	/** public static final method ***/
	/*********************************/

	public static final float arrayToFloat(byte[] data) {
		if (data.length != DataType.REAL32.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return byteBuffer.getFloat();
	}

	public static final byte[] floatToArray(float data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(DataType.REAL32.size);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(Float.floatToRawIntBits(data));
		return byteBuffer.array();
	}
}
