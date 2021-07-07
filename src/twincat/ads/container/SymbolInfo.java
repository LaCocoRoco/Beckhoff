package twincat.ads.container;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.constant.DataType;
import twincat.ads.constant.SymbolFlag;
import twincat.ads.datatype.STRING;

public class SymbolInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int INFO_DATA_LENGTH = 30;

    /*************************/
    /*** global attributes ***/
    /*************************/

    private int length = 0;

    private int indexGroup = 0;

    private int indexOffset = 0;

    private int dataSize = 0;

    private String symbolName = new String();

    private String comment = new String();

    private String type = new String();

    private TypeInfo typeInfo = new TypeInfo();

    private DataType dataType = DataType.UNKNOWN;

    private SymbolFlag symbolFlag = SymbolFlag.UNKNOWN;

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getIndexGroup() {
        return indexGroup;
    }

    public void setIndexGroup(int indexGroup) {
        this.indexGroup = indexGroup;
    }

    public int getIndexOffset() {
        return indexOffset;
    }

    public void setIndexOffset(int indexOffset) {
        this.indexOffset = indexOffset;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public SymbolFlag getSymbolFlag() {
        return symbolFlag;
    }

    public void setSymbolFlag(SymbolFlag symbolFlag) {
        this.symbolFlag = symbolFlag;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*************************/
    /********* public ********/
    /*************************/

    public void parseSymbolInfo(byte[] buffer) {
        parseSymbolInfo(buffer, 0);
    }

    public void parseSymbolInfo(byte[] buffer, int index) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        if (byteBuffer.remaining() >= index) {
            byteBuffer.position(index);

            if (byteBuffer.remaining() >= INFO_DATA_LENGTH) {
                length = byteBuffer.getInt();
                indexGroup = byteBuffer.getInt();
                indexOffset = byteBuffer.getInt();
                dataSize = byteBuffer.getInt();
                int dataType = byteBuffer.getInt();
                int symbolFlag = byteBuffer.getShort();
                @SuppressWarnings("unused")
                int arrayDimension = byteBuffer.getShort();
                int nameLength = byteBuffer.getShort() + 1;
                int typeLength = byteBuffer.getShort() + 1;
                int commentLength = byteBuffer.getShort() + 1;
                this.dataType = DataType.getByValue(dataType);
                this.symbolFlag = SymbolFlag.getByValue(symbolFlag);

                if (byteBuffer.remaining() >= nameLength) {
                    byte[] readBuffer = new byte[nameLength];
                    byteBuffer.get(readBuffer, 0, nameLength);
                    symbolName = STRING.arrayToValue(readBuffer);
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

            for (Symbol dataTypeSymbol : dataTypeSymbolList) {
                if (!typeInfo.getArray().isEmpty()) {
                    // concatenate symbol and array type info
                    for (String typeInfoArray : typeInfo.getArray()) {
                        Symbol symbol = new Symbol();
                        symbol.setSymbolName(symbolName + typeInfoArray + "." + dataTypeSymbol.getSymbolName());
                        symbol.setDataType(dataTypeSymbol.getDataType());
                        symbolList.add(symbol);
                    }
                } else {
                    Symbol symbol = new Symbol();
                    symbol.setSymbolName(symbolName + "." +dataTypeSymbol.getSymbolName());
                    symbol.setDataType(dataTypeSymbol.getDataType());
                    symbolList.add(symbol);
                }
            }
        }

        // is simple data type
        if (!dataType.equals(DataType.BIGTYPE)) {
            if (!typeInfo.getArray().isEmpty()) {       
                // concatenate symbol and array type info
                for (String typeInfoArray : typeInfo.getArray()) {
                    Symbol symbol = new Symbol();
                    symbol.setSymbolName(symbolName + typeInfoArray);
                    symbol.setDataType(dataType);
                    symbolList.add(symbol);
                }
            } else {
                Symbol symbol = new Symbol();
                symbol.setSymbolName(symbolName);
                symbol.setDataType(dataType);
                symbolList.add(symbol);
            }
        }

        return symbolList;
    }
}
