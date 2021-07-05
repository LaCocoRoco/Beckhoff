package twincat.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;

public class AdsSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final AdsClient ads;

    private final List<AdsSymbol> symbolTable;
 
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final Logger logger = TwincatLogger.getSignedLogger();

    private final List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();

    private final List<AdsSymbolDataTypeInfo> dataTypeInfoList = new ArrayList<AdsSymbolDataTypeInfo>();
 
    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolLoader(AdsClient ads) { 
        this.ads = ads;
        
        try {
            ads.open();
            ads.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            symbolInfoList.addAll(ads.readSymbolInfoList());
            dataTypeInfoList.addAll(ads.readDataTypeInfoList());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            ads.close();
        }   
        
        this.symbolTable = setSymbolTable();
    }  

    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public AdsClient getAds() {
        return ads;
    }

    public List<AdsSymbol> getSymbolTable() {
        return symbolTable;
    }
  
    /*************************/
    /********* public ********/
    /*************************/

    public List<AdsSymbol> getSubSymbolOfSymbol(AdsSymbol symbol) {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            if (symbolInfo.getSymbolName().equalsIgnoreCase(symbol.getName())) {
                symbolList.addAll(symbolInfo.getSymbolList(dataTypeInfoList));
            }
        }

        return symbolList;
    }
    
    public List<AdsSymbol> getSymbolBySymbolName(String symbolName) {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            if (symbolInfo.getSymbolName().equalsIgnoreCase(symbolName)) {   
                symbolList.addAll(symbolInfo.getSymbolList(dataTypeInfoList));
            }
        }
        
        return symbolList;
    }

    /*************************/
    /******** private ********/
    /*************************/

    private List<AdsSymbol> setSymbolTable()  {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            symbolList.addAll(symbolInfo.toSymbol());
        }
        
        return symbolList;   
    }
}
