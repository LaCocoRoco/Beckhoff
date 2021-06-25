package twincat.ads;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import twincat.ads.constants.AdsData;
import twincat.ads.constants.AdsIdxGrp;
import twincat.ads.datatype.BIT;
import twincat.ads.datatype.BOOL;
import twincat.ads.datatype.BYTE;
import twincat.ads.datatype.DINT;
import twincat.ads.datatype.DWORD;
import twincat.ads.datatype.INT;
import twincat.ads.datatype.INT16;
import twincat.ads.datatype.INT32;
import twincat.ads.datatype.INT8;
import twincat.ads.datatype.LREAL;
import twincat.ads.datatype.REAL;
import twincat.ads.datatype.REAL32;
import twincat.ads.datatype.REAL64;
import twincat.ads.datatype.SINT;
import twincat.ads.datatype.STRING;
import twincat.ads.datatype.UDINT;
import twincat.ads.datatype.UINT;
import twincat.ads.datatype.UINT16;
import twincat.ads.datatype.UINT32;
import twincat.ads.datatype.UINT8;
import twincat.ads.datatype.USINT;
import twincat.ads.datatype.WORD;
import twincat.ads.enums.AdsError;
import twincat.ads.enums.DataType;
import twincat.ads.jni.AdsNative;
import twincat.ads.wrapper.Variable;

public class Ads extends AdsNative {
	/*************************/
	/*** ads implementation **/
	/*************************/

	public void open() {
		super.adsOpenPort();
	}
	
	public void close() throws AdsException {
		super.adsClosePort();
	}

	public void setTimeout(long timeout) throws AdsException {
		super.adsSetTimeout(timeout);
	}
	
	public String getVersion() {
		return super.adsReadVersion();
	}
	
	public String readAmsNetId() throws AdsException {
	    return super.adsGetLocalAddress();
	}
	
	public AdsDeviceInfo readDeviceInfo() throws AdsException {
		return super.adsReadDeviceInfo();
	}

	public AdsDeviceState readDeviceState() throws AdsException {
		return super.adsReadState();
	}

	public void writeDeviceState(AdsDeviceState adsDeviceState, byte[] writeBuffer) throws AdsException {
		super.adsWriteControl(adsDeviceState, writeBuffer);
	}

	public void read(long idxGrp, long idxOffs, byte[] readBuffer) throws AdsException {
		super.adsRead(idxGrp, idxOffs, readBuffer);
	}

	public void write(long idxGrp, long idxOffs, byte[] writeBuffer) throws AdsException {
		super.adsWrite(idxGrp, idxOffs, writeBuffer);
	}

	public void readWrite(long idxGrp, long idxOffs, byte[] readBuffer, byte[] writeBuffer) throws AdsException {
		super.adsReadWrite(idxGrp, idxOffs, readBuffer, writeBuffer);
	}

	public long addDeviceNotification(long idxGrp, long idxOffs, AdsNotification notification, AdsCallback callback) throws AdsException {
		return super.adsAddDeviceNotification(idxGrp, idxOffs, 0, notification, callback);
	}

	public void deleteDeviceNotification(long notification, AdsCallback callback) throws AdsException {
		super.adsDeleteDeviceNotification(notification, callback);
	}

	/*************************/
	/********* public ********/
	/*************************/

	public void readBySymbolHandle(int symbolHandle, byte[] readBuffer) throws AdsException {
		if (symbolHandle != 0) {
			read(AdsIdxGrp.SYM_VALBYHND, symbolHandle, readBuffer);
		} else throw new AdsException(AdsError.ADS_INV_SYMB);
	}

	public void writeBySymbolHandle(int symbolHandle, byte[] writeBuffer) throws AdsException {
		if (symbolHandle != 0) {
			write(AdsIdxGrp.SYM_VALBYHND, symbolHandle, writeBuffer);
		} else throw new AdsException(AdsError.ADS_INV_SYMB);
	}
	
	public void writeReleaseSymbolHandle(int symbolHandle) throws AdsException {
		byte[] writeBuffer = INT32.valueToArray(symbolHandle);
		write(AdsIdxGrp.SYM_RELEASEHND, 0, writeBuffer);
	}

	public int readHandleOfSymbolName(String symbolName) throws AdsException {
		byte[] writeBuffer = STRING.valueToArray(symbolName);
		byte[] readBuffer = new byte[Integer.SIZE / Byte.SIZE];
		readWrite(AdsIdxGrp.SYM_HNDBYNAME, 0, readBuffer, writeBuffer);
		return INT32.arrayToValue(readBuffer);
	}
	
	public void readBySymbolName(byte[] readBuffer, String symbolName) throws AdsException {
		byte [] writeBuffer = STRING.valueToArray(symbolName);
		readWrite(AdsIdxGrp.SYM_VALBYNAME, 0, readBuffer, writeBuffer);
	}

	public AdsSymbolInfo readSymbolInfoBySymbolName(String symbolName) throws AdsException {
		byte[] writeBuffer = symbolName.getBytes();	
		byte[] readBuffer = new byte[AdsData.SYM_INFO_BY_NAME_SIZE];
		readWrite(AdsIdxGrp.SYM_INFOBYNAMEEX, 0, readBuffer, writeBuffer);
		return new AdsSymbolInfo(readBuffer);		
	}

	public List<AdsSymbolInfo> readSymbolInfoList() throws AdsException {
		byte[] uploadInfoBuffer = new byte[AdsData.SYM_UPLOADINFO_SIZE];
		read(AdsIdxGrp.SYM_UPLOADINFO, 0, uploadInfoBuffer);
		ByteBuffer uploadInfoByteBuffer = ByteBuffer.wrap(uploadInfoBuffer);
		uploadInfoByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		int symbolCount = uploadInfoByteBuffer.getInt();
		int uploadLength = uploadInfoByteBuffer.getInt();

		byte[] uploadBuffer = new byte[uploadLength];
		read(AdsIdxGrp.SYM_UPLOAD, 0, uploadBuffer);
		ByteBuffer uploadByteBuffer = ByteBuffer.wrap(uploadBuffer);
		uploadByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();
		
		int index = 0;
		for (int i = 0; i < symbolCount; i++) {
			AdsSymbolInfo symbolInfo = new AdsSymbolInfo(uploadBuffer, index);
			symbolInfoList.add(symbolInfo);

			if (symbolInfo.getType().contains(AdsData.SYM_INFO_TYP_ARRAY)) {
				List<String> symbolNameList = getTypeArrayToSymbolName(symbolInfo.getName(), symbolInfo.getType());
				
				for (String symbolName : symbolNameList) {
					AdsSymbolInfo symbolArrayInfo = readSymbolInfoBySymbolName(symbolName);
					symbolInfoList.add(symbolArrayInfo);
				}		
			}

			index += symbolInfo.getInfoLength();
		}
		
		return symbolInfoList;
	}
	
	public List<String> readVariableSymbolNameList() throws AdsException {
		List<AdsSymbolInfo> symbolInfoList = readSymbolInfoList();
		List<String> symbolNameList = new ArrayList<String>();
		
		for (AdsSymbolInfo symbolInfo : symbolInfoList) {
			DataType dataType = DataType.getByValue(symbolInfo.getDataType());
			if (dataType.size != DataType.UNKNOWN.size) {
				symbolNameList.add(symbolInfo.getName());		
			}
		}
		
		return symbolNameList;
	}
	
	/*************************/
	/******** private ********/
	/*************************/	

	private List<String> dimensionToRange(List<Point> dimension, int index) {
		List<String> data = new ArrayList<>();
		Point range = dimension.get(index);
		String end = index == 0 ? "[" : "";
		for (int i = range.x; i <= range.y; i++) {
			if (dimension.size() > index + 1) {
				for (String r : dimensionToRange(dimension, index + 1))
					data.add(end + Integer.toString(i) + "," + r);
			} else {
				data.add(end + Integer.toString(i) + "]");
			}
		}
		
		return data;
	}

	private List<String> getTypeArrayToSymbolName(String name, String type) {
		String[] list = type.substring(type.indexOf("[") + 1, type.indexOf("]")).split(",");
		List<Point> dimension = new ArrayList<Point>();
		for (String index : list) {
			String[] size = index.replace(" ", "").split("\\..");
			int x = Integer.valueOf(size[0]);
			int y = Integer.valueOf(size[1]);
			dimension.add(new Point(x, y));
		}

		List<String> range = dimensionToRange(dimension, 0);
		for (int i = 0; i < range.size(); i++) {
			String value = range.get(i);
			range.set(i, name + value);
		}

		return range;	
	}	
	
	/*************************/
	/******** mapping ********/
	/*************************/

	public Variable getVariableByAddress(DataType dataType, int idxGrp, int idxOffs) throws AdsException {
		switch (dataType) {
			case BIT:		return new BIT(this, idxGrp, idxOffs);
			case BOOL:		return new BOOL(this, idxGrp, idxOffs);
			case INT8:		return new INT8(this, idxGrp, idxOffs);
			case SINT:		return new SINT(this, idxGrp, idxOffs);
			case INT16:		return new INT16(this, idxGrp, idxOffs);
			case INT:		return new INT(this, idxGrp, idxOffs);
			case UINT8:		return new UINT8(this, idxGrp, idxOffs);
			case USINT:		return new USINT(this, idxGrp, idxOffs);
			case BYTE:		return new BYTE(this, idxGrp, idxOffs);
			case UINT16:	return new UINT16(this, idxGrp, idxOffs);
			case UINT:		return new UINT(this, idxGrp, idxOffs);
			case WORD:		return new WORD(this, idxGrp, idxOffs);
			case INT32:		return new INT32(this, idxGrp, idxOffs);
			case DINT:		return new DINT(this, idxGrp, idxOffs);
			case UINT32:	return new UINT32(this, idxGrp, idxOffs);
			case UDINT:		return new UDINT(this, idxGrp, idxOffs);
			case DWORD:		return new DWORD(this, idxGrp, idxOffs);
			case REAL32:	return new REAL32(this, idxGrp, idxOffs);
			case REAL:		return new REAL(this, idxGrp, idxOffs);
			case REAL64:	return new REAL64(this, idxGrp, idxOffs);
			case LREAL:		return new LREAL(this, idxGrp, idxOffs);
			case STRING:	return new STRING(this, idxGrp, idxOffs);
			default: 		return null;
		}
	}	
	
	public Variable getVariableBySymbolName(String symbolName) throws AdsException {
		AdsSymbolInfo symbolInfo = readSymbolInfoBySymbolName(symbolName);
		DataType dataType = DataType.getByValue(symbolInfo.getDataType());
		short dataSize = (short) symbolInfo.getDataSize();

		switch (dataType) {
			case BIT:		return new BIT(this, symbolName);
			case BOOL:		return new BOOL(this, symbolName);
			case INT8:		return new INT8(this, symbolName);
			case SINT:		return new SINT(this, symbolName);
			case INT16:		return new INT16(this, symbolName);
			case INT:		return new INT(this, symbolName);
			case UINT8:		return new UINT8(this, symbolName);
			case USINT:		return new USINT(this, symbolName);
			case BYTE:		return new BYTE(this, symbolName);
			case UINT16:	return new UINT16(this, symbolName);
			case UINT:		return new UINT(this, symbolName);
			case WORD:		return new WORD(this, symbolName);
			case INT32:		return new INT32(this, symbolName);
			case DINT:		return new DINT(this, symbolName);
			case UINT32:	return new UINT32(this, symbolName);
			case UDINT:		return new UDINT(this, symbolName);
			case DWORD:		return new DWORD(this, symbolName);
			case REAL32:	return new REAL32(this, symbolName);
			case REAL:		return new REAL(this, symbolName);
			case REAL64:	return new REAL64(this, symbolName);
			case LREAL:		return new LREAL(this, symbolName);
			case STRING:	return new STRING(this, symbolName, dataSize);
			default: 		return null;
		}
	}	
}
