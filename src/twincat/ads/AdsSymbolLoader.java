package twincat.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.constants.AmsPort;

public class AdsSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final List<AdsSymbol> symbolTable;
 
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final AdsClient ads = new AdsClient();
    
    private final Logger logger = TwincatLogger.getSignedLogger();

    private final List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();

    private final List<AdsSymbolDataTypeInfo> dataTypeInfoList = new ArrayList<AdsSymbolDataTypeInfo>();
 
    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolLoader(String amsNetId, AmsPort amsPort) { 
        try {
            ads.open();
            ads.setAmsNetId(amsNetId);
            ads.setAmsPort(amsPort);
            
            symbolInfoList.addAll(ads.readSymbolInfoList());
            dataTypeInfoList.addAll(ads.readDataTypeInfoList());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }

        try {
            ads.close();
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }      
        
        this.symbolTable = setSymbolTable();
    }  

    /*************************/
    /**** setter & getter ****/
    /*************************/

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
