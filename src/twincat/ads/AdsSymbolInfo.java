package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

	private AdsDataType dataType = AdsDataType.UNKNOWN;

    private AdsSymbolFlag symbolFlag = AdsSymbolFlag.UNKNOWN;
    
	private String symbolName = new String();

	private String type = new String();

	private String comment = new String();
 
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

	/*************************/
	/******** private ********/
	/*************************/

    private void getAdsSymbolInfo(byte[] buffer, int index) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

		if (byteBuffer.remaining() >= index) {
			byteBuffer.position(index);

			if (byteBuffer.remaining() >= INFO_BUFFER_LENGTH) {
				length             = byteBuffer.getInt();
				indexGroup         = byteBuffer.getInt();
				indexOffset        = byteBuffer.getInt();
				dataSize           = byteBuffer.getInt();
				int dataType       = byteBuffer.getInt();
				int symbolFlag     = byteBuffer.getShort();
				@SuppressWarnings("unused")
                int arrayDimension = byteBuffer.getShort();
				int nameLength     = byteBuffer.getShort() + 1;
				int typeLength     = byteBuffer.getShort() + 1;
				int commentLength  = byteBuffer.getShort() + 1;
                this.dataType      = AdsDataType.getByValue(dataType);
                this.symbolFlag    = AdsSymbolFlag.getByValue(symbolFlag);
				
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
}
