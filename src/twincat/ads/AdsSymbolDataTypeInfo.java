package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.datatype.STRING;
import twincat.ads.enums.AdsDataType;
import twincat.ads.enums.AdsDataTypeFlag;

public class AdsSymbolDataTypeInfo {
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int INFO_DATA_LENGTH = 44;

    /*************************/
    /*** global attributes ***/
    /*************************/

    private int length = 0;

    private int version = 0;

    private int hashValue = 0;

    private int typeHashValue = 0;

    private int dataSize = 0;

    private int offset = 0;

    private String dataTypeName = new String();

    private String comment = new String();

    private String type = new String();

    private AdsTypeInfo typeInfo = new AdsTypeInfo();
 
    private AdsDataType dataType = AdsDataType.UNKNOWN;

    private AdsDataTypeFlag dataTypeFlag = AdsDataTypeFlag.UNKNOWN;

    private final List<AdsSymbolDataTypeInfo> subdividedSymbolDataTypeInfoList = new ArrayList<AdsSymbolDataTypeInfo>();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolDataTypeInfo() {
        /* empty */
    }
   
    public AdsSymbolDataTypeInfo(byte[] buffer) {
        parseSymbolDataTypeInfo(buffer);
    }

    public AdsSymbolDataTypeInfo(byte[] buffer, int index) {
        parseSymbolDataTypeInfo(buffer, index);
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getHashValue() {
        return hashValue;
    }

    public void setHashValue(int hashValue) {
        this.hashValue = hashValue;
    }

    public int getTypeHashValue() {
        return typeHashValue;
    }

    public void setTypeHashValue(int typeHashValue) {
        this.typeHashValue = typeHashValue;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public AdsTypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(AdsTypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public AdsDataType getDataType() {
        return dataType;
    }

    public void setDataType(AdsDataType dataType) {
        this.dataType = dataType;
    }

    public AdsDataTypeFlag getDataTypeFlag() {
        return dataTypeFlag;
    }

    public void setDataTypeFlag(AdsDataTypeFlag dataTypeFlag) {
        this.dataTypeFlag = dataTypeFlag;
    }

    public List<AdsSymbolDataTypeInfo> getSubSymbolDataTypeInfoList() {
        return subdividedSymbolDataTypeInfoList;
    }

    /*************************/
    /********* public ********/
    /*************************/
 
    public void parseSymbolDataTypeInfo(byte[] buffer) {
        parseSymbolDataTypeInfo(buffer, 0);
    }

    public void parseSymbolDataTypeInfo(byte[] buffer, int index) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        if (byteBuffer.remaining() >= index) {
            byteBuffer.position(index);

            if (byteBuffer.remaining() >= INFO_DATA_LENGTH) {
                length = byteBuffer.getInt();
                version = byteBuffer.getInt();
                hashValue = byteBuffer.getInt();
                typeHashValue = byteBuffer.getInt();
                dataSize = byteBuffer.getInt();
                offset = byteBuffer.getInt();
                int dataType = byteBuffer.getInt();
                int dataTypeFlag = byteBuffer.getInt();
                int nameLength = byteBuffer.getShort() + 1;
                int typeLength = byteBuffer.getShort() + 1;
                int commentLength = byteBuffer.getShort() + 1;
                @SuppressWarnings("unused")
                int arrayDimension = byteBuffer.getShort();
                int subItemCount = byteBuffer.getShort();
                this.dataType = AdsDataType.getByValue(dataType);
                this.dataTypeFlag = AdsDataTypeFlag.getByValue(dataTypeFlag);

                if (byteBuffer.remaining() >= nameLength) {
                    byte[] readBuffer = new byte[nameLength];
                    byteBuffer.get(readBuffer, 0, nameLength);
                    dataTypeName = STRING.arrayToValue(readBuffer);
                }

                if (byteBuffer.remaining() >= typeLength) {
                    byte[] readBuffer = new byte[typeLength];
                    byteBuffer.get(readBuffer, 0, typeLength);
                    type = STRING.arrayToValue(readBuffer);
                    typeInfo.parseType(type);
                }

                if (byteBuffer.remaining() >= commentLength) {
                    byte[] readBuffer = new byte[commentLength];
                    byteBuffer.get(readBuffer, 0, commentLength);
                    comment = STRING.arrayToValue(readBuffer);
                }

                int subItemIndex = byteBuffer.position();
                for (int i = 0; i < subItemCount; i++) {
                    AdsSymbolDataTypeInfo symbolDataTypeInfo = new AdsSymbolDataTypeInfo(buffer, subItemIndex);
                    subdividedSymbolDataTypeInfoList.add(symbolDataTypeInfo);
                    subItemIndex += symbolDataTypeInfo.getLength();
                }
            }
        }
    }
    

    
    public List<AdsSymbolInfo> getSymbolInfoList(List<AdsSymbolDataTypeInfo> symbolDataTypeInfoList) {
        List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();

        // dismiss pointer
        if (typeInfo.isPointer()) return symbolInfoList;
        
        // is complex data type
        if (dataType.equals(AdsDataType.BIGTYPE)) {

            // get symbol info list of data type info list from type info type
            List<AdsSymbolInfo> dataTypeSymbolInfoList = new ArrayList<AdsSymbolInfo>();
            for (AdsSymbolDataTypeInfo symbolDataTypeInfo : symbolDataTypeInfoList) {
                if (typeInfo.getType().equals(symbolDataTypeInfo.getDataTypeName())) {
                    dataTypeSymbolInfoList = symbolDataTypeInfo.getSymbolInfoList(symbolDataTypeInfoList);
                    break;
                }
            }

            // is data type
            if (dataTypeFlag.equals(AdsDataTypeFlag.DATA_TYPE)) {
                for (AdsSymbolInfo dataTypeSymbolInfo : dataTypeSymbolInfoList) {
                    if (!typeInfo.getArray().isEmpty()) {
                        // add symbol info and array type info
                        for (String typeInfoArray : typeInfo.getArray()) {
                            AdsSymbolInfo symbolInfo = new AdsSymbolInfo();
                            symbolInfo.setSymbolName(typeInfoArray + dataTypeSymbolInfo.getSymbolName());
                            symbolInfo.setDataType(dataTypeSymbolInfo.getDataType());
                            symbolInfoList.add(symbolInfo);
                        }
                    } else {
                        // add symbol info
                        AdsSymbolInfo symbolInfo = new AdsSymbolInfo();
                        symbolInfo.setSymbolName(dataTypeSymbolInfo.getSymbolName());
                        symbolInfo.setDataType(dataTypeSymbolInfo.getDataType());
                        symbolInfoList.add(symbolInfo);
                    }
                }
            }

            // is data item
            if (dataTypeFlag.equals(AdsDataTypeFlag.DATA_ITEM)) {
                for (AdsSymbolInfo dataTypeSymbolInfo : dataTypeSymbolInfoList) {
                    if (!typeInfo.getArray().isEmpty()) {
                     // add symbol info and array type info
                        for (String typeInfoArray : typeInfo.getArray()) {
                            AdsSymbolInfo symbolInfo = new AdsSymbolInfo();
                            symbolInfo.setSymbolName(dataTypeName + typeInfoArray + "." + dataTypeSymbolInfo.getSymbolName());
                            symbolInfo.setDataType(dataTypeSymbolInfo.getDataType());
                            symbolInfoList.add(symbolInfo);
                        }
                    } else {
                        // add symbol info
                        AdsSymbolInfo symbolInfo = new AdsSymbolInfo();
                        symbolInfo.setSymbolName(dataTypeName + "." + dataTypeSymbolInfo.getSymbolName());
                        symbolInfo.setDataType(dataTypeSymbolInfo.getDataType());
                        symbolInfoList.add(symbolInfo); 
                    }
                }
            }
        }

        // is simple data type
        if (!dataType.equals(AdsDataType.BIGTYPE)) {
            if (!typeInfo.getArray().isEmpty()) {
                // add symbol info and array type info
                for (String typeInfoArray : typeInfo.getArray()) {
                    AdsSymbolInfo symbolInfo = new AdsSymbolInfo();
                    symbolInfo.setSymbolName(dataTypeName + typeInfoArray);
                    symbolInfo.setDataType(dataType);
                    symbolInfoList.add(symbolInfo);
                }
            } else {
                // add symbol info
                AdsSymbolInfo symbolInfo = new AdsSymbolInfo();
                symbolInfo.setSymbolName(dataTypeName);
                symbolInfo.setDataType(dataType);
                symbolInfoList.add(symbolInfo);
            }
        }

        // collect subdivided symbol info
        for (AdsSymbolDataTypeInfo subdividedSymbolDataTypeInfo : subdividedSymbolDataTypeInfoList) {
            symbolInfoList.addAll(subdividedSymbolDataTypeInfo.getSymbolInfoList(symbolDataTypeInfoList));
        }

        return symbolInfoList;
    }
}
