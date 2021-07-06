package twincat.ads;

import java.util.List;

import twincat.ads.enums.AdsDataType;
import twincat.ads.enums.AmsPort;

public class AdsSymbol {
    /*************************/
    /*** local attributes ****/
    /*************************/

    private final AdsSymbolInfo symbolInfo;

    private final AdsSymbolLoader symbolLoader;

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbol(AdsSymbolInfo symbolInfo, AdsSymbolLoader symbolLoader) {
        this.symbolInfo = symbolInfo;
        this.symbolLoader = symbolLoader;
    }

    /*************************/
    /********* public ********/
    /*************************/
    
    @Override
    public String toString() {
        String[] nameList = symbolInfo.getSymbolName().split(".");
        return nameList.length != 0 ? nameList[nameList.length - 1] : "";
    }
    
    public String getName() {
        return symbolInfo.getSymbolName();
    }

    public AdsDataType getDataType() {
        boolean isArray = !symbolInfo.getTypeInfo().getArray().isEmpty();
        return isArray ? AdsDataType.BIGTYPE : symbolInfo.getDataType();
    }

    public String getAmsNetId() {
        return symbolLoader.getAds().getAmsNetId();
    }
    
    public AmsPort getAmsPort() {
        return symbolLoader.getAds().getAmsPort();
    }
    
    public boolean isBigType() {
        return getDataType() != AdsDataType.BIGTYPE ? false : true;
    }
       
    public List<AdsSymbol> getSymbolList() {
        return symbolLoader.getSymbolList(symbolInfo);
    }   
}
