package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import twincat.ads.datatype.STRING;
import twincat.ads.enums.DataType;

public class AdsSymbolInfo {
	/*************************/
	/** constant attributes **/
	/*************************/

	private static final int INFO_BUFFER_LENGTH = 30;

	/*************************/
	/*** global attributes ***/
	/*************************/

	private int infoLength = 0;

	private int indexGroup = 0;

	private int indexOffset = 0;

	private int dataSize = 0;

	private int flags = 0;
	
	private int dataType = 0;
	
	//private DataType dataType = DataType.UNKNOWN;
	
	private String name = new String();

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

	public int getInfoLength() {
		return infoLength;
	}

	public void setInfoLength(int infoLength) {
		this.infoLength = infoLength;
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

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
				infoLength  = byteBuffer.getInt();
				indexGroup  = byteBuffer.getInt();
				indexOffset = byteBuffer.getInt();
				dataSize    = byteBuffer.getInt();
				dataType 	= byteBuffer.getInt();
				flags       = byteBuffer.getInt();

				int nameLength    = byteBuffer.getShort() + 1;
				int typeLength    = byteBuffer.getShort() + 1;
				int commentLength = byteBuffer.getShort() + 1;

				if (byteBuffer.remaining() >= nameLength) {
					byte[] nameBuffer = new byte[nameLength];
					byteBuffer.get(nameBuffer, 0, nameLength);
					name = STRING.arrayToValue(nameBuffer);
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
