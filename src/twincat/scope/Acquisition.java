package twincat.scope;

import twincat.ads.constants.AdsDataType;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class Acquisition {
	/*************************/
	/*** global attributes ***/
	/*************************/
	
	private int taskTime = 2;

	private boolean symbolBased = false;

	private String symbolName = new String();

	private int indexGroup = 0;

	private int indexOffset = 0;

	private String amsNetId = AmsNetId.LOCAL;
	
	private AmsPort amsPort = AmsPort.TC2PLC1;

	private AdsDataType dataType = AdsDataType.UNKNOWN;

	/*************************/
	/**** setter & getter ****/
	/*************************/

	public boolean isSymbolBased() {
		return symbolBased;
	}

	public void setSymbolBased(boolean symbolBased) {
		this.symbolBased = symbolBased;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
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

	public String getAmsNetId() {
		return amsNetId;
	}

	public void setAmsNetId(String amsNetId) {
		this.amsNetId = amsNetId;
	}

	public AmsPort getAmsPort() {
		return amsPort;
	}

	public void setAmsPort(AmsPort amsPort) {
		this.amsPort = amsPort;
	}

	public AdsDataType getDataType() {
		return dataType;
	}

	public void setDataType(AdsDataType dataType) {
		this.dataType = dataType;
	}

	public int getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(int taskTime) {
		this.taskTime = taskTime;
	}
}
