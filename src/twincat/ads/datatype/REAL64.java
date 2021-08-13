package twincat.ads.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AdsError;

public class REAL64 extends Variable {
    /*********************************/
    /********** constructor **********/
    /*********************************/

	public REAL64(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.REAL64.size, symbolHandle);
	}

	public REAL64(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.REAL64.size, indexGroup, indexOffset);
	}

	public REAL64(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.REAL64.size, adsClient.readHandleOfSymbolName(symbolName));
	}

    /*********************************/
    /******** override method ********/
    /*********************************/

	@Override
	public DataType getDataType() {
		return DataType.REAL64;
	}

	@Override
	public boolean toBoolean() {
		double value = REAL64.arrayToDouble(data);
		return value > 0 ? true : false;
	}

	@Override
	public byte toByte() {
		return (byte) REAL64.arrayToDouble(data);
	}

	@Override
	public short toShort() {
		return (short) REAL64.arrayToDouble(data);
	}

	@Override
	public int toInteger() {
		return (int) REAL64.arrayToDouble(data);
	}

	@Override
	public long toLong() {
		return (long) REAL64.arrayToDouble(data);
	}

	@Override
	public float toFloat() {
		return (float) REAL64.arrayToDouble(data);
	}

	@Override
	public double toDouble() {
		return (double) REAL64.arrayToDouble(data);
	}

	@Override
	public String toString() {
		double value = REAL64.arrayToDouble(data);
		return Double.toString(value);
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		double data = value ? (double) 1 : (double) 0;
		super.write(REAL64.doubleToArray(data));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		super.write(REAL64.doubleToArray((double) value));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		super.write(REAL64.doubleToArray((double) value));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		super.write(REAL64.doubleToArray((double) value));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		super.write(REAL64.doubleToArray((double) value));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		super.write(REAL64.doubleToArray((double) value));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		super.write(REAL64.doubleToArray((double) value));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {	
		try  {
			double data = Double.parseDouble(value);
			super.write(REAL64.doubleToArray(data));
		} catch(NumberFormatException e) {
			throw new AdsException(AdsError.VARIABLE_WRITE_PARSE_ERROR);
		}
		return this;
	}

    /*********************************/
    /** public static final method ***/
    /*********************************/

	public static final double arrayToDouble(byte[] data) {
		if (data.length != DataType.REAL64.size) return 0;
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return byteBuffer.getDouble();
	}

	public static final byte[] doubleToArray(double data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(DataType.REAL64.size);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putLong(Double.doubleToLongBits(data));
		return byteBuffer.array();
	}
}