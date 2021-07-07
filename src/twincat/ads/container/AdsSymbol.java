package twincat.ads.container;

import twincat.ads.constant.AdsDataType;

public class AdsSymbol {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private String symbolName = new String();

    private AdsDataType dataType = AdsDataType.UNKNOWN;

    /*************************/
    /**** setter & getter ****/
    /*************************/
   
    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public AdsDataType getDataType() {
        return dataType;
    }

    public void setDataType(AdsDataType dataType) {
        this.dataType = dataType;
    } 
}
