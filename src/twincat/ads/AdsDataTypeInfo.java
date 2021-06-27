package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.constants.AdsDataType;
import twincat.ads.constants.AdsDataTypeFlag;
import twincat.ads.datatype.STRING;

public class AdsDataTypeInfo {
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int INFO_BUFFER_LENGTH = 38;

    /*************************/
    /*** global attributes ***/
    /*************************/

    private int length = 0;

    private int version = 0;

    private int hashValue = 0;

    private int typeHashValue = 0;

    private int size = 0;

    private int offset = 0;

    private AdsDataType dataType = AdsDataType.UNKNOWN;

    private AdsDataTypeFlag dataTypeFlag = AdsDataTypeFlag.UNKNOWN;

    private String dataTypeName = new String();

    private String type = new String();

    private String comment = new String();

    private final List<AdsDataTypeInfo> dataTypeInfoList = new ArrayList<AdsDataTypeInfo>();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsDataTypeInfo(byte[] buffer) {
        getDataTypeInfo(buffer, 0);
    }

    public AdsDataTypeInfo(byte[] buffer, int index) {
        getDataTypeInfo(buffer, index);
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<AdsDataTypeInfo> getDataTypeInfoList() {
        return dataTypeInfoList;
    }

    /*************************/
    /******** private ********/
    /*************************/

    private void getDataTypeInfo(byte[] buffer, int index) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        if (byteBuffer.remaining() >= index) {
            byteBuffer.position(index);

            if (byteBuffer.remaining() >= INFO_BUFFER_LENGTH) {
                length             = byteBuffer.getInt();
                version            = byteBuffer.getInt();
                hashValue          = byteBuffer.getInt();
                typeHashValue      = byteBuffer.getInt();
                size               = byteBuffer.getInt();
                offset             = byteBuffer.getInt();
                int dataType       = byteBuffer.getInt();
                int dataTypeFlag   = byteBuffer.getInt();
                int nameLength     = byteBuffer.getShort() + 1;
                int typeLength     = byteBuffer.getShort() + 1;
                int commentLength  = byteBuffer.getShort() + 1;  
                @SuppressWarnings("unused")
                int arrayDimension = byteBuffer.getShort();
                int subItemCount   = byteBuffer.getShort();
                this.dataType      = AdsDataType.getByValue(dataType);
                this.dataTypeFlag  = AdsDataTypeFlag.getByValue(dataTypeFlag);
    
                if (byteBuffer.remaining() >= nameLength) {
                    byte[] symbolNameBuffer = new byte[nameLength];
                    byteBuffer.get(symbolNameBuffer, 0, nameLength);
                    dataTypeName = STRING.arrayToValue(symbolNameBuffer);
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

                int subItemIndex = byteBuffer.position();
                for (int i = 0; i < subItemCount; i++) {
                    AdsDataTypeInfo dataTypeInfo = new AdsDataTypeInfo(buffer, subItemIndex);
                    dataTypeInfoList.add(dataTypeInfo);
                    subItemIndex += dataTypeInfo.getLength();     
                }
            }
        }
    }
}
