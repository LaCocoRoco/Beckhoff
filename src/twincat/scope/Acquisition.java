package twincat.scope;

import twincat.ads.constant.DataType;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class Acquisition {
    /*********************************/
    /******** global variable ********/
    /*********************************/
	
	private int sampleTime = 2;

	private boolean symbolBased = true;

    private String symbolName = "Channel";
	
	private int indexGroup = 0;

	private int indexOffset = 0;

	private String amsNetId = AmsNetId.LOCAL;
	
	private AmsPort amsPort = AmsPort.TC2PLC1;

	private DataType dataType = DataType.UNKNOWN;

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

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

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public int getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(int sampleTime) {
		this.sampleTime = sampleTime;
	}

    /*********************************/
    /********* public method *********/
    /*********************************/

	public Acquisition clone() {
	    Acquisition acquisition = new Acquisition();
        acquisition.setAmsNetId(amsNetId);
        acquisition.setAmsPort(amsPort);
        acquisition.setDataType(dataType);
        acquisition.setIndexGroup(indexGroup);
        acquisition.setIndexOffset(indexOffset);
        acquisition.setSymbolBased(symbolBased);
        acquisition.setSymbolName(symbolName);
        acquisition.setSampleTime(sampleTime);
        
        return acquisition;
	}
	
	public String getChannelName() {
	    String[] symbolNameArray = symbolName.split("\\.");
	    
	    if (symbolNameArray.length != 0) {
	        return symbolNameArray[symbolNameArray.length - 1];
	    } else {
	        return symbolName;
	    }
	}	
}
