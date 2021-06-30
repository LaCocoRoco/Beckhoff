package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.constants.AdsDataType;
import twincat.ads.constants.AdsSymbolFlag;
import twincat.ads.datatype.STRING;

public class AdsSymbolInfo {
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int INFO_BUFFER_LENGTH = 30;

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

    private AdsDataType dataType = AdsDataType.UNKNOWN;

    private AdsSymbolFlag symbolFlag = AdsSymbolFlag.UNKNOWN;

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolInfo(byte[] buffer) {
        getAdsSymbolInfo(buffer, 0);
    }

    public AdsSymbolInfo(byte[] buffer, int index) {
        getAdsSymbolInfo(buffer, index);
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

    public AdsDataType getDataType() {
        return dataType;
    }

    public void setDataType(AdsDataType dataType) {
        this.dataType = dataType;
    }

    public AdsSymbolFlag getSymbolFlag() {
        return symbolFlag;
    }

    public void setSymbolFlag(AdsSymbolFlag symbolFlag) {
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
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*************************/
    /******** private ********/
    /*************************/

    @SuppressWarnings("unused")
    private void getAdsSymbolInfo(byte[] buffer, int index) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        if (byteBuffer.remaining() >= index) {
            byteBuffer.position(index);

            if (byteBuffer.remaining() >= INFO_BUFFER_LENGTH) {
                length = byteBuffer.getInt();
                indexGroup = byteBuffer.getInt();
                indexOffset = byteBuffer.getInt();
                dataSize = byteBuffer.getInt();
                int dataType = byteBuffer.getInt();
                int symbolFlag = byteBuffer.getShort();
                int arrayDimension = byteBuffer.getShort();
                int nameLength = byteBuffer.getShort() + 1;
                int typeLength = byteBuffer.getShort() + 1;
                int commentLength = byteBuffer.getShort() + 1;
                this.dataType = AdsDataType.getByValue(dataType);
                this.symbolFlag = AdsSymbolFlag.getByValue(symbolFlag);

                if (byteBuffer.remaining() >= nameLength) {
                    byte[] symbolNameBuffer = new byte[nameLength];
                    byteBuffer.get(symbolNameBuffer, 0, nameLength);
                    symbolName = STRING.arrayToValue(symbolNameBuffer);
                }

                if (byteBuffer.remaining() >= typeLength) {
                    byte[] typeBuffer = new byte[typeLength];
                    byteBuffer.get(typeBuffer, 0, typeLength);
                    type = STRING.arrayToValue(typeBuffer);                     
                }

                if (byteBuffer.remaining() >= commentLength) {
                    byte[] commentBuffer = new byte[commentLength];
                    byteBuffer.get(commentBuffer, 0, commentLength);
                    comment = STRING.arrayToValue(commentBuffer);
                }
            }
        }
    }

    /*************************/
    /********* public ********/
    /*************************/

    public List<AdsSymbol> toSymbol() {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

        // parse type info
        AdsTypeInfo typeInfo = new AdsTypeInfo(type);
        AdsDataType dataType = typeInfo.isArray() ? AdsDataType.BIGTYPE :this.dataType;
        
        // set symbol 
        AdsSymbol symbol = new AdsSymbol();
        symbol.setName(symbolName);
        symbol.setType(dataType);
        symbolList.add(symbol);

        return symbolList;
    }
    
    // time consuming
    public List<AdsSymbol> getSymbolList(List<AdsSymbolDataTypeInfo> symbolDataTypeInfoList) {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

        // parse type info
        AdsTypeInfo typeInfo = new AdsTypeInfo(type);
        
        // skip pointer
        //if (typeInfo.isPointer()) return symbolList;
        
        // get named array list
        List<String> namedArrayList = new ArrayList<String>();
        if (!typeInfo.getTypeArray().isEmpty()) {
            namedArrayList.addAll(typeInfo.getNamedTypeArray());
        }

        // is complex data type
        if (dataType.equals(AdsDataType.BIGTYPE)) {

            // get type symbol list from type info
            List<AdsSymbol> typeNodeList = new ArrayList<AdsSymbol>();
            for (AdsSymbolDataTypeInfo symbolDataTypeInfo : symbolDataTypeInfoList) {
                if (typeInfo.getTypeName().equals(symbolDataTypeInfo.getDataTypeName())) {
                    typeNodeList = symbolDataTypeInfo.getDataTypeSymbolList(symbolDataTypeInfoList);
                    break;
                }
            }

            for (AdsSymbol typeNode : typeNodeList) {
                if (!namedArrayList.isEmpty()) {
                    // add array
                    for (String namedArray : namedArrayList) {
                        // add symbol
                        AdsSymbol symbol = new AdsSymbol();
                        symbol.setName(symbolName + namedArray + "." + typeNode.getName());
                        symbol.setType(typeNode.getType());
                        symbolList.add(symbol);
                    }
                } else {
                    // add symbol
                    AdsSymbol symbol = new AdsSymbol();
                    symbol.setName(symbolName + "." + typeNode.getName());
                    symbol.setType(typeNode.getType());
                    symbolList.add(symbol);
                }
            }
        }

        // is primitive data type
        if (!dataType.equals(AdsDataType.BIGTYPE)) {
            if (!namedArrayList.isEmpty()) {       
                // add array
                for (String namedArray : namedArrayList) {
                    // add symbol
                    AdsSymbol symbol = new AdsSymbol();
                    symbol.setName(symbolName + namedArray);
                    symbol.setType(dataType);
                    symbolList.add(symbol);
                }
            } else {
                // add symbol
                AdsSymbol symbol = new AdsSymbol();
                symbol.setName(symbolName);
                symbol.setType(dataType);
                symbolList.add(symbol);
            }
        }

        return symbolList;
    }
}
