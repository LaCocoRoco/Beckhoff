package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.constant.DataType;
import twincat.ads.common.DataTypeInfo;
import twincat.ads.common.DeviceInfo;
import twincat.ads.common.DeviceState;
import twincat.ads.common.Route;
import twincat.ads.common.SymbolInfo;
import twincat.ads.common.UploadInfo;
import twincat.ads.constant.AdsError;
import twincat.ads.constant.IndexGroup;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
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
import twincat.ads.datatype.TIME;
import twincat.ads.datatype.UDINT;
import twincat.ads.datatype.UINT;
import twincat.ads.datatype.UINT16;
import twincat.ads.datatype.UINT32;
import twincat.ads.datatype.UINT8;
import twincat.ads.datatype.USINT;
import twincat.ads.datatype.WORD;
import twincat.ads.wrapper.Variable;

public class AdsClient extends AdsNative {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final int DEFAULT_TIMEOUT = 1000;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AdsClient() {
        super.amsAddress.setNetIdStringEx(AmsNetId.LOCAL);
        super.amsAddress.setPort(AmsPort.SYSTEMSERVICE.value);
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public void setAmsNetId(String amsNetId) {
        super.amsAddress.setNetIdStringEx(amsNetId);
    }
    
    public String getAmsNetId() {
        return super.amsAddress.getNetIdString();
    }

    public void setAmsPort(AmsPort amsPort) {
        super.amsAddress.setPort(amsPort.value);
    }
    
    public AmsPort getAmsPort() {
        return AmsPort.getByValue(super.amsAddress.getPort());
    }

    /*********************************/
    /****** ads implementation *******/
    /*********************************/

    public void open() {
        super.adsOpenPort();
    }

    public void close() {
        super.adsClosePort();
    }

    public void setTimeout(long timeout) throws AdsException {
        super.adsSetTimeout(timeout);
    }

    public String getVersion() {
        return super.adsReadVersion();
    }

    public String readLocalAmsNetId() throws AdsException {
        return super.adsGetLocalAddress();
    }

    public DeviceInfo readDeviceInfo() throws AdsException {
        return super.adsReadDeviceInfo();
    }

    public DeviceState readDeviceState() throws AdsException {
        return super.adsReadState();
    }

    public void writeDeviceState(DeviceState adsDeviceState, byte[] writeBuffer) throws AdsException {
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

    /*********************************/
    /********* public method *********/
    /*********************************/

    public String readLocalHostName() throws AdsException {
        byte[] readBuffer = new byte[IndexGroup.SYSTEM_IP_HOST_NAME.size];
        read(IndexGroup.SYSTEM_IP_HOST_NAME.value, 0, readBuffer);
        return STRING.arrayToString(readBuffer);
    }
    
    public void readBySymbolHandle(int symbolHandle, byte[] readBuffer) throws AdsException {
        if (symbolHandle != 0) {
            read(IndexGroup.SYMBOL_VALUE_BY_HANDLE.value, symbolHandle, readBuffer);
        } else throw new AdsException(AdsError.ADS_INV_SYMB);
    }

    public void writeBySymbolHandle(int symbolHandle, byte[] writeBuffer) throws AdsException {
        if (symbolHandle != 0) {
            write(IndexGroup.SYMBOL_VALUE_BY_HANDLE.value, symbolHandle, writeBuffer);
        } else throw new AdsException(AdsError.ADS_INV_SYMB);
    }

    public void writeReleaseSymbolHandle(int symbolHandle) throws AdsException {
        byte[] writeBuffer = INT32.integerToArray(symbolHandle);
        write(IndexGroup.SYMBOL_RELEASE_HANDLE.value, 0, writeBuffer);
    }

    public int readHandleOfSymbolName(String symbolName) throws AdsException {
        byte[] writeBuffer = STRING.stringToArray(symbolName);
        byte[] readBuffer = new byte[Integer.SIZE / Byte.SIZE];
        readWrite(IndexGroup.SYMBOL_HANDLE_BY_NAME.value, 0, readBuffer, writeBuffer);
        return INT32.arrayToInteger(readBuffer);
    }

    public void readBySymbolName(byte[] readBuffer, String symbolName) throws AdsException {
        byte[] writeBuffer = STRING.stringToArray(symbolName);
        readWrite(IndexGroup.SYMBOL_VALUE_BY_NAME.value, 0, readBuffer, writeBuffer);
    }
 
    public SymbolInfo readSymbolInfoBySymbolName(String symbolName) throws AdsException {
        byte[] writeBuffer = symbolName.getBytes();
        byte[] readBuffer = new byte[IndexGroup.SYMBOL_INFO_BYNAME_EX.size];
        readWrite(IndexGroup.SYMBOL_INFO_BYNAME_EX.value, 0, readBuffer, writeBuffer);
        SymbolInfo symbolInfo = new SymbolInfo();
        symbolInfo.parseSymbolInfo(readBuffer);
        return symbolInfo;
    }

    public DataTypeInfo readDataTypeInfoByDataTypeName(String dataTypeName) throws AdsException {
        byte[] writeBuffer = STRING.stringToArray(dataTypeName);
        byte[] readBuffer = new byte[IndexGroup.DATA_TYPE_INFO_BY_NAME_EX.size];
        readWrite(IndexGroup.DATA_TYPE_INFO_BY_NAME_EX.value, 0, readBuffer, writeBuffer);
        DataTypeInfo dataTypeInfo = new DataTypeInfo();
        dataTypeInfo.parseDataTypeInfo(readBuffer);
        return dataTypeInfo;
    }

    public List<DataTypeInfo> readDataTypeInfoList() throws AdsException {
        UploadInfo uploadInfo = readUploadInfo();

        byte[] readBuffer = new byte[uploadInfo.getDataTypeLength()];
        read(IndexGroup.SYMBOL_DATA_TYPE_UPLOAD.value, 0, readBuffer);
        ByteBuffer readByteBuffer = ByteBuffer.wrap(readBuffer);
        readByteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        List<DataTypeInfo> dataTypeInfoList = new ArrayList<DataTypeInfo>();

        int index = 0;
        for (int i = 0; i < uploadInfo.getDataTypeCount(); i++) {
            DataTypeInfo dataTypeInfo = new DataTypeInfo();
            dataTypeInfo.parseDataTypeInfo(readBuffer, index);
            dataTypeInfoList.add(dataTypeInfo);
            index += dataTypeInfo.getLength();
        }
  
        return dataTypeInfoList;
    }

    public List<SymbolInfo> readSymbolInfoList() throws AdsException {
        UploadInfo uploadInfo = readUploadInfo();

        byte[] readBuffer = new byte[uploadInfo.getSymbolLength()];
        read(IndexGroup.SYMBOL_UPLOAD.value, 0, readBuffer);
        ByteBuffer readByteBuffer = ByteBuffer.wrap(readBuffer);
        readByteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        List<SymbolInfo> symbolInfoList = new ArrayList<SymbolInfo>();

        int index = 0;
        for (int i = 0; i < uploadInfo.getSymbolCount(); i++) {
            SymbolInfo symbolInfo = new SymbolInfo();
            symbolInfo.parseSymbolInfo(readBuffer, index);
            symbolInfoList.add(symbolInfo);
            index += symbolInfo.getLength();
        }

        return symbolInfoList;
    }

    public UploadInfo readUploadInfo() throws AdsException {
        byte[] readBuffer = new byte[IndexGroup.SYMBOL_UPLOAD_INFO_2.size];
        read(IndexGroup.SYMBOL_UPLOAD_INFO_2.value, 0, readBuffer);
        return new UploadInfo(readBuffer);
    }

    public List<Route> readRouteEntrys() throws AdsException {     
        List<Route> routeList = new ArrayList<Route>();

        for (int i = 0; i < IndexGroup.SYSTEM_ENUM_REMOTE.size; i++) {
            try {
                byte[] readBuffer = new byte[IndexGroup.SYSTEM_ENUM_REMOTE.size];
                read(IndexGroup.SYSTEM_ENUM_REMOTE.value, i, readBuffer);
                Route route = new Route();
                route.parseRoute(readBuffer);
                routeList.add(route);
            } catch (AdsException e) {
                if (e.getAdsError().equals(AdsError.ADS_NOMORE_NOT_HDL)) {
                    break;
                } else {
                    throw new AdsException(e.getAdsError());
                }   
            }  
        }

        return routeList;
    }

    /*********************************/
    /************ mapping ************/
    /*********************************/

    public Variable getVariableByAddress(DataType dataType, int idxGrp, int idxOffs) throws AdsException {
        switch (dataType) {
            case BIT:       return new BIT(this, idxGrp, idxOffs);
            case BOOL:      return new BOOL(this, idxGrp, idxOffs);
            case INT8:      return new INT8(this, idxGrp, idxOffs);
            case SINT:      return new SINT(this, idxGrp, idxOffs);
            case INT16:     return new INT16(this, idxGrp, idxOffs);
            case INT:       return new INT(this, idxGrp, idxOffs);
            case UINT8:     return new UINT8(this, idxGrp, idxOffs);
            case USINT:     return new USINT(this, idxGrp, idxOffs);
            case BYTE:      return new BYTE(this, idxGrp, idxOffs);
            case UINT16:    return new UINT16(this, idxGrp, idxOffs);
            case UINT:      return new UINT(this, idxGrp, idxOffs);
            case WORD:      return new WORD(this, idxGrp, idxOffs);
            case INT32:     return new INT32(this, idxGrp, idxOffs);
            case DINT:      return new DINT(this, idxGrp, idxOffs);
            case UINT32:    return new UINT32(this, idxGrp, idxOffs);
            case UDINT:     return new UDINT(this, idxGrp, idxOffs);
            case DWORD:     return new DWORD(this, idxGrp, idxOffs);
            case TIME:      return new TIME(this, idxGrp, idxOffs);
            case REAL32:    return new REAL32(this, idxGrp, idxOffs);
            case REAL:      return new REAL(this, idxGrp, idxOffs);
            case REAL64:    return new REAL64(this, idxGrp, idxOffs);
            case LREAL:     return new LREAL(this, idxGrp, idxOffs);
            case STRING:    return new STRING(this, idxGrp, idxOffs);
            default:        return null;
        }
    }

    public Variable getVariableBySymbolName(String symbolName) throws AdsException {
        SymbolInfo symbolInfo = readSymbolInfoBySymbolName(symbolName);
        DataType dataType = symbolInfo.getDataType();
        short dataSize = (short) symbolInfo.getDataSize();

        switch (dataType) {
            case BIT:       return new BIT(this, symbolName);
            case BOOL:      return new BOOL(this, symbolName);
            case INT8:      return new INT8(this, symbolName);
            case SINT:      return new SINT(this, symbolName);
            case INT16:     return new INT16(this, symbolName);
            case INT:       return new INT(this, symbolName);
            case UINT8:     return new UINT8(this, symbolName);
            case USINT:     return new USINT(this, symbolName);
            case BYTE:      return new BYTE(this, symbolName);
            case UINT16:    return new UINT16(this, symbolName);
            case UINT:      return new UINT(this, symbolName);
            case WORD:      return new WORD(this, symbolName);
            case INT32:     return new INT32(this, symbolName);
            case DINT:      return new DINT(this, symbolName);
            case UINT32:    return new UINT32(this, symbolName);
            case UDINT:     return new UDINT(this, symbolName);
            case DWORD:     return new DWORD(this, symbolName);
            case TIME:      return new TIME(this, symbolName);
            case REAL32:    return new REAL32(this, symbolName);
            case REAL:      return new REAL(this, symbolName);
            case REAL64:    return new REAL64(this, symbolName);
            case LREAL:     return new LREAL(this, symbolName);
            case STRING:    return new STRING(this, symbolName, dataSize);
            default:        return null;
        }
    }
}
