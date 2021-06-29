package twincat.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.container.AdsSymbol;
import twincat.ads.container.AdsSymbolInfo;

public class AdsSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final List<AdsSymbol> symbolTable;
 
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final List<AdsSymbolInfo> symbolInfoList;

    private final List<AdsDataTypeInfo> dataTypeInfoList;
 
    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolLoader(Ads ads) {
        Logger logger = AdsLogger.getLogger();

        List<AdsSymbolInfo> symbolInfoList = null;
        List<AdsDataTypeInfo> dataTypeInfoList = null;
  
        try {
            symbolInfoList = ads.readSymbolInfoList();
            dataTypeInfoList = ads.readDataTypeInfoList();
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
        
        this.dataTypeInfoList = dataTypeInfoList;
        this.symbolInfoList = symbolInfoList;
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

    public List<AdsSymbol> getSubNodesOfNode(AdsSymbol symbol) {
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
