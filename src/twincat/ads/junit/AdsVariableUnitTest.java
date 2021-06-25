package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.DataType;
import twincat.ads.wrapper.Variable;

public class AdsVariableUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void bit() {
		try {
			String name = ".bit";
			
			boolean valueMin = false;
			Variable variableMin;
			variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.BIT == variableMin.getDataType();
			assert variableMin.toBoolean() == valueMin;
			assert variableMin.toByte()    == (byte) (valueMin ? 1 : 0);
			assert variableMin.toShort()   == (short) (valueMin ? 1 : 0);
			assert variableMin.toInteger() == (int) (valueMin ? 1 : 0);
			assert variableMin.toLong()    == (long) (valueMin ? 1 : 0);
			assert variableMin.toFloat()   == (float) (valueMin ? 1 : 0);
			assert variableMin.toDouble()  == (double) (valueMin ? 1 : 0);
			assert variableMin.toString().equals(Boolean.toString(valueMin));

			boolean valueMax = true;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(true);
			variableMax.read();
			variableMax.close();
			
			assert DataType.BIT == variableMax.getDataType();
			assert variableMax.toBoolean() == valueMax;
			assert variableMax.toByte()    == (byte) (valueMax ? 1 : 0);
			assert variableMax.toShort()   == (short) (valueMax ? 1 : 0);
			assert variableMax.toInteger() == (int) (valueMax ? 1 : 0);
			assert variableMax.toLong()    == (long) (valueMax ? 1 : 0);
			assert variableMax.toFloat()   == (float) (valueMax ? 1 : 0);
			assert variableMax.toDouble()  == (double) (valueMax ? 1 : 0);
			assert variableMax.toString().equals(Boolean.toString(valueMax));	
		} catch (AdsException e) {
			System.out.println("BIT: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void int8() {
		try {
			String name = ".int8";
			
			byte valueMin = -128;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.INT8 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Byte.toString(valueMin));

			byte valueMax = 127;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.INT8 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Byte.toString(valueMax));			
		} catch (AdsException e) {
			System.out.println("INT8: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void int16() {
		try {
			String name = ".int16";
			
			int valueMin = -32768;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.INT16 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Integer.toString(valueMin));

			int valueMax = 32767;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.INT16 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Integer.toString(valueMax));			
		} catch (AdsException e) {
			System.out.println("INT16: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void int32() {
		try {
			String name = ".int32";
			
			int valueMin = -2147483647;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.INT32 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Integer.toString(valueMin));

			int valueMax = 2147483647;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.INT32 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Integer.toString(valueMax));			
		} catch (AdsException e) {
			System.out.println("INT32: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void uint8() {
		try {
			String name = ".uint8";
			
			short valueMin = 0;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.UINT8 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Short.toString(valueMin));

			short valueMax = 255;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.UINT8 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Short.toString(valueMax));	
		} catch (AdsException e) {
			System.out.println("INT8: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void uint16() {
		try {
			String name = ".uint16";
			
			int valueMin = 0;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.UINT16 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Integer.toString(valueMin));

			int valueMax = 65535;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.UINT16 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Integer.toString(valueMax));		
		} catch (AdsException e) {
			System.out.println("UINT16: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void uint32() {
		try {
			String name = ".uint32";
			
			long valueMin = 0;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.UINT32 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort(  ) == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Long.toString(valueMin));
		
			long valueMax = 4294967295L;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.UINT32 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Long.toString(valueMax));
		} catch (AdsException e) {
			System.out.println("UINT32: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void real32() {
		try {
			String name = ".real32";
			
			float valueMin = (float) -3.402823E38;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.REAL32 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			
			float valueMax = (float) 3.402823E38;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.REAL32 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Float.toString(valueMax));	
		} catch (AdsException e) {
			System.out.println("REAL32: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void real64() {
		try {
			String name = ".real64";
			
			double valueMin = -1.79769313486231E307;
			Variable variableMin = ads.getVariableBySymbolName(name);
			variableMin.write(valueMin);
			variableMin.read();
			variableMin.close();
			
			assert DataType.REAL64 == variableMin.getDataType();
			assert variableMin.toBoolean() == false;
			assert variableMin.toByte()    == (byte) valueMin;
			assert variableMin.toShort()   == (short) valueMin;
			assert variableMin.toInteger() == (int) valueMin;
			assert variableMin.toLong()    == (long) valueMin;
			assert variableMin.toFloat()   == (float) valueMin;
			assert variableMin.toDouble()  == (double) valueMin;
			assert variableMin.toString().equals(Double.toString(valueMin));

			double valueMax = 1.79769313486231E307;
			Variable variableMax = ads.getVariableBySymbolName(name);
			variableMax.write(valueMax);
			variableMax.read();
			variableMax.close();
			
			assert DataType.REAL64 == variableMin.getDataType();
			assert variableMax.toBoolean() == true;
			assert variableMax.toByte()    == (byte) valueMax;
			assert variableMax.toShort()   == (short) valueMax;
			assert variableMax.toInteger() == (int) valueMax;
			assert variableMax.toLong()    == (long) valueMax;
			assert variableMax.toFloat()   == (float) valueMax;
			assert variableMax.toDouble()  == (double) valueMax;
			assert variableMax.toString().equals(Double.toString(valueMax));
		} catch (AdsException e) {
			System.out.println("REAL64: " + e.getAdsErrorMessage());
		}
	}

	@Test
	public void string() {
		try {
			String name = ".strng";
			
			String value = "Hello Wold";
			Variable variable = ads.getVariableBySymbolName(name);
			variable.write(value);
			variable.read();
			variable.close();
			
			assert DataType.STRING == variable.getDataType();
			assert variable.toBoolean() == true;
			assert variable.toByte()    == 1;
			assert variable.toShort()   == 1;
			assert variable.toInteger() == 1;
			assert variable.toLong()    == 1;
			assert variable.toFloat()   == 1;
			assert variable.toDouble()  == 1;
			assert variable.toString().equals(value);
		} catch (AdsException e) {
			System.out.println("STRING: " + e.getAdsErrorMessage());
		}
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}