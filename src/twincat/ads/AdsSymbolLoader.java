package twincat.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.enums.AmsPort;

public class AdsSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final AdsClient adsClient = new AdsClient();

    private final List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

    private final List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();

    private final List<AdsSymbolDataTypeInfo> symbolDataTypeInfoList = new ArrayList<AdsSymbolDataTypeInfo>();

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final Logger logger = TwincatLogger.getSignedLogger();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolLoader() {
        /* empty */
    }
    
    // TODO : to parse method
    public AdsSymbolLoader(AdsClient adsClient) { 
        try {
            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();
            
            this.adsClient.setAmsNetId(amsNetId);
            this.adsClient.setAmsPort(amsPort);
            this.adsClient.open();
            this.adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);
            
            logger.info("AdsSymbolLoader : Load Symbol List | " + amsNetId + " | " + amsPort);
            
            symbolInfoList.addAll(this.adsClient.readSymbolInfoList());

            for (AdsSymbolInfo symbolInfo : symbolInfoList) {
                AdsSymbol symbol = new AdsSymbol(symbolInfo, this);
                symbolList.add(symbol); 
            }

        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            this.adsClient.close();
        } 
    }  

    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public AdsClient getAds() {
        return adsClient;
    }

    public List<AdsSymbol> getSymbolList() {
        return symbolList;
    }

    public List<AdsSymbolInfo> getSymbolInfoList() {
        return symbolInfoList;
    }
    
    public List<AdsSymbolDataTypeInfo> getDataTypeInfoList() {
        return symbolDataTypeInfoList;
    }

    /*************************/
    /********* public ********/
    /*************************/

    // TODO : symbol info without symbol list
    public List<AdsSymbol> getSymbolList(String symbolName) { 
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            if (symbolName.equalsIgnoreCase(symbolInfo.getSymbolName())) { 
                symbolList.addAll(symbolInfoListToSymbolList(symbolInfo));
            }
        }
   
        return symbolList;
    }

    public List<AdsSymbol> getSymbolList(AdsSymbolInfo symbolInfo) {
        return symbolInfoListToSymbolList(symbolInfo);
    }
    
    /*************************/
    /******** private ********/
    /*************************/
  
    // TODO : only load here symbol data type info
    private List<AdsSymbol> symbolInfoListToSymbolList(AdsSymbolInfo symbolInfo) {

        if (symbolDataTypeInfoList.isEmpty()) {
            try {
                adsClient.open();
                symbolDataTypeInfoList.addAll(adsClient.readDataTypeInfoList());
            } catch (AdsException e) {
                e.printStackTrace();
            } finally {
                adsClient.close();
            }
        }

        List<AdsSymbolInfo> symbolInfoList = symbolInfo.getSymbolInfoList(symbolDataTypeInfoList);
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
        
        for (AdsSymbolInfo symbolInfoIterator : symbolInfoList) {
            AdsSymbol symbol = new AdsSymbol(symbolInfoIterator, this);
            symbolList.add(symbol); 
        }
        
        return symbolList;
    } 
}
