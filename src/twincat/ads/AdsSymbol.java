package twincat.ads;

import twincat.ads.enums.AdsDataType;

public class AdsSymbol {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private String name = new String();
 
    private AdsDataType dataType = AdsDataType.UNKNOWN;

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public AdsDataType getDataType() {
        return dataType;
    }

    public void setDataType(AdsDataType dataType) {
        this.dataType = dataType;
    }
}
