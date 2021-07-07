package twincat.ads.container;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.constant.DataType;
import twincat.ads.constant.DataTypeFlag;
import twincat.ads.datatype.STRING;

public class DataTypeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private TypeInfo typeInfo = new TypeInfo();
 
    private DataType dataType = DataType.UNKNOWN;

    private DataTypeFlag dataTypeFlag = DataTypeFlag.UNKNOWN;

    private final List<DataTypeInfo> internalDataTypeInfoList = new ArrayList<DataTypeInfo>();

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
    
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataTypeFlag getDataTypeFlag() {
        return dataTypeFlag;
    }

    public void setDataTypeFlag(DataTypeFlag dataTypeFlag) {
        this.dataTypeFlag = dataTypeFlag;
    }

    public List<DataTypeInfo> getSubSymbolDataTypeInfoList() {
        return internalDataTypeInfoList;
    }

    /*************************/
    /********* public ********/
    /*************************/
 
    public void parseDataTypeInfo(byte[] buffer) {
        parseDataTypeInfo(buffer, 0);
    }

    public void parseDataTypeInfo(byte[] buffer, int index) {
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
                this.dataType = DataType.getByValue(dataType);
                this.dataTypeFlag = DataTypeFlag.getByValue(dataTypeFlag);

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

                int internalIndex = byteBuffer.position();
                for (int i = 0; i < subItemCount; i++) {
                    DataTypeInfo dataTypeInfo = new DataTypeInfo();
                    dataTypeInfo.parseDataTypeInfo(buffer, internalIndex);
                    internalDataTypeInfoList.add(dataTypeInfo);
                    internalIndex += dataTypeInfo.getLength();
                }
            }
        }
    }
    
    public List<Symbol> getSymbolList(List<DataTypeInfo> dataTypeInfoList) {
        List<Symbol> symbolList = new ArrayList<Symbol>();

        // dismiss pointer
        if (typeInfo.isPointer()) return symbolList;
        
        // is complex data type
        if (dataType.equals(DataType.BIGTYPE)) {

            // get symbol list of data type info list byte type info
            List<Symbol> dataTypeSymbolList = new ArrayList<Symbol>();
            for (DataTypeInfo dataTypeInfo : dataTypeInfoList) {
                if (typeInfo.getType().equals(dataTypeInfo.getDataTypeName())) {
                    dataTypeSymbolList = dataTypeInfo.getSymbolList(dataTypeInfoList);
                    break;
                }
            }

            // is data type
            if (dataTypeFlag.equals(DataTypeFlag.DATA_TYPE)) {
                for (Symbol dataTypeSymbol : dataTypeSymbolList) {
                    if (!typeInfo.getArray().isEmpty()) {
                        // concatenate symbol and array type info
                        for (String typeInfoArray : typeInfo.getArray()) {
                            Symbol symbol = new Symbol();
                            symbol.setSymbolName(typeInfoArray + dataTypeSymbol.getSymbolName());
                            symbol.setDataType(dataTypeSymbol.getDataType());
                            symbolList.add(symbol);
                        }
                    } else {
                        Symbol symbol = new Symbol();
                        symbol.setSymbolName(dataTypeSymbol.getSymbolName());
                        symbol.setDataType(dataTypeSymbol.getDataType());
                        symbolList.add(symbol);
                    }
                }
            }

            // is data item
            if (dataTypeFlag.equals(DataTypeFlag.DATA_ITEM)) {
                for (Symbol dataTypeSymbol : dataTypeSymbolList) {
                    if (!typeInfo.getArray().isEmpty()) {
                        // concatenate symbol and array type info
                        for (String typeInfoArray : typeInfo.getArray()) {
                            Symbol symbol = new Symbol();
                            symbol.setSymbolName(dataTypeName + typeInfoArray + "." + dataTypeSymbol.getSymbolName());
                            symbol.setDataType(dataTypeSymbol.getDataType());
                            symbolList.add(symbol);
                        }
                    } else {
                        Symbol symbol = new Symbol();
                        symbol.setSymbolName(dataTypeName + "." + dataTypeSymbol.getSymbolName());
                        symbol.setDataType(dataTypeSymbol.getDataType());
                        symbolList.add(symbol); 
                    }
                }
            }
        }

        // is simple data type
        if (!dataType.equals(DataType.BIGTYPE)) {
            if (!typeInfo.getArray().isEmpty()) {
                // concatenate symbol and array type info
                for (String typeInfoArray : typeInfo.getArray()) {
                    Symbol symbol = new Symbol();
                    symbol.setSymbolName(dataTypeName + typeInfoArray);
                    symbol.setDataType(dataType);
                    symbolList.add(symbol);
                }
            } else {
                Symbol symbol = new Symbol();
                symbol.setSymbolName(dataTypeName);
                symbol.setDataType(dataType);
                symbolList.add(symbol);
            }
        }

        // collect internal symbol list
        for (DataTypeInfo internalSymbolDataTypeInfo : internalDataTypeInfoList) {
            symbolList.addAll(internalSymbolDataTypeInfo.getSymbolList(dataTypeInfoList));
        }

        return symbolList;
    }
}
