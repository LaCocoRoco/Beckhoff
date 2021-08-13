package twincat.ads.datatype;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.DataType;

public class BIT extends Variable {
    /*********************************/
    /********** constructor **********/
    /*********************************/

	public BIT(AdsClient adsClient, int symbolHandle) {
		super(adsClient, DataType.BIT.size, symbolHandle);
	}

	public BIT(AdsClient adsClient, int indexGroup, int indexOffset) throws AdsException {
		super(adsClient, DataType.BIT.size, indexGroup, indexOffset);
	}
	
	public BIT(AdsClient adsClient, String symbolName) throws AdsException {
		super(adsClient, DataType.BIT.size, adsClient.readHandleOfSymbolName(symbolName));
	}

    /*********************************/
    /******** override method ********/
    /*********************************/
	
	@Override	
	public DataType getDataType() {
		return DataType.BIT;
	}
	
	@Override
	public boolean toBoolean() {
		return BIT.arrayToBoolean(data);
	}

	@Override
	public byte toByte() {
		return BIT.arrayToBoolean(data) ? (byte) 1 : (byte) 0;
	}

	@Override
	public short toShort() {
		return BIT.arrayToBoolean(data) ? (short) 1 : (short) 0;
	}

	@Override
	public int toInteger() {
		return BIT.arrayToBoolean(data) ? (int) 1 : (int) 0;
	}

	@Override
	public long toLong() {
		return BIT.arrayToBoolean(data) ? (long) 1 : (long) 0;
	}

	@Override
	public float toFloat() {
		return BIT.arrayToBoolean(data) ? (float) 1 : (float) 0;
	}

	@Override
	public double toDouble() {
		return BIT.arrayToBoolean(data) ? (double) 1 : (double) 0;
	}

	@Override
	public String toString() {
		return Boolean.toString(BIT.arrayToBoolean(data));
	}

	@Override
	public Variable write(boolean value) throws AdsException {
		super.write(BIT.booleanToArray(value));
		return this;
	}

	@Override
	public Variable write(byte value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.booleanToArray(data));
		return this;
	}

	@Override
	public Variable write(short value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.booleanToArray(data));
		return this;
	}

	@Override
	public Variable write(int value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.booleanToArray(data));
		return this;
	}

	@Override
	public Variable write(long value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.booleanToArray(data));
		return this;
	}

	@Override
	public Variable write(float value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.booleanToArray(data));
		return this;
	}

	@Override
	public Variable write(double value) throws AdsException {
		boolean data = value > 0 ? true : false;
		super.write(BIT.booleanToArray(data));
		return this;
	}

	@Override
	public Variable write(String value) throws AdsException {
		boolean data = Boolean.parseBoolean(value);
		super.write(BIT.booleanToArray(data));
		return this;
	}

    /*********************************/
    /** public static final method ***/
    /*********************************/
	
	public static final boolean arrayToBoolean(byte[] data) {
		if (data.length != DataType.BIT.size) return false;
		return data[0] == 1 ? true : false;
	}

	public static final byte[] booleanToArray(boolean data) {
		return data ? new byte[] {1} :  new byte[] {0};
	}
}
