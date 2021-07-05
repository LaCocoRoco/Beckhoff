package twincat.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;

public class AdsSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final AdsClient adsClient = new AdsClient();

    private final List<AdsSymbol> symbols = new ArrayList<AdsSymbol>();
 
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final Logger logger = TwincatLogger.getSignedLogger();

    private final List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();

    private final List<AdsSymbolDataTypeInfo> dataTypeInfoList = new ArrayList<AdsSymbolDataTypeInfo>();
 
    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolLoader(AdsClient adsClient) { 
        try {
            this.adsClient.open();
            this.adsClient.setAmsNetId(adsClient.getAmsNetId());
            this.adsClient.setAmsPort(adsClient.getAmsPort());
            this.adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            symbolInfoList.addAll(this.adsClient.readSymbolInfoList());
            dataTypeInfoList.addAll(this.adsClient.readDataTypeInfoList());
             
            for (AdsSymbolInfo symbolInfo : symbolInfoList) {
                symbols.add(symbolInfo.toSymbol());
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

    public List<AdsSymbol> getSymbols() {
        return symbols;
    }
  
    /*************************/
    /********* public ********/
    /*************************/

    public List<AdsSymbol> getSymbolsOfBigType(AdsSymbol symbol) {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            if (symbolInfo.getSymbolName().equalsIgnoreCase(symbol.getName())) {
                symbolList.addAll(symbolInfo.getSymbolList(dataTypeInfoList));
            }
        }

        return symbolList;
    }
    
    public List<AdsSymbol> getSymbolsBySymbolName(String symbolName) {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            if (symbolInfo.getSymbolName().equalsIgnoreCase(symbolName)) {   
                symbolList.addAll(symbolInfo.getSymbolList(dataTypeInfoList));
            }
        }
        
        return symbolList;
    }
}
