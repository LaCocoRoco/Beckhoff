package twincat.ads;

import twincat.ads.enums.AdsDataType;

public class AdsSymbol {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private String name = new String();
 
    private AdsDataType type = AdsDataType.UNKNOWN;

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public AdsDataType getType() {
        return type;
    }

    public void setType(AdsDataType type) {
        this.type = type;
    }
}
