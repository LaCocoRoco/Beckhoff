package twincat.ads.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.DataType;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.DataTypeInfo;
import twincat.ads.container.Symbol;
import twincat.ads.container.SymbolInfo;
import twincat.ads.container.UploadInfo;

public class SymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final AdsClient adsClient = new AdsClient();
 
    private final List<Symbol> symbolList = new ArrayList<Symbol>();

    private final List<SymbolInfo> symbolInfoList = new ArrayList<SymbolInfo>();

    private final List<DataTypeInfo> dataTypeInfoList = new ArrayList<DataTypeInfo>();

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final Logger logger = TwincatLogger.getLogger();

    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public String getAmsNetId() {
        return adsClient.getAmsNetId();
    }

    public void setAmsNetId(String amsNetId) {
        adsClient.setAmsNetId(amsNetId);
    }

    public AmsPort getAmsPort() {
        return adsClient.getAmsPort();
    }

    public void setAmsPort(AmsPort amsPort) {
        adsClient.setAmsPort(amsPort);
    }
    
    public AdsClient getAdsClient() {
        return adsClient;
    }
   
    public List<Symbol> getSymbolList() {
        return symbolList;
    }

    public List<SymbolInfo> getSymbolInfoList() {
        return symbolInfoList;
    }

    public List<DataTypeInfo> getDataTypeInfoList() {
        return dataTypeInfoList;
    }

    /*************************/
    /********* public ********/
    /*************************/

    public void parseSymbolList() {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();

            UploadInfo uploadInfo = adsClient.readUploadInfo();
            long symbolInfoSize = uploadInfo.getSymbolLength() * uploadInfo.getSymbolCount();

            logger.fine("Parse Symbol Table | " + amsNetId + " | " + amsPort);

            symbolList.clear();
            symbolList.addAll(getSymbolList(adsClient.readSymbolInfoList()));

            logger.fine("Symbol Info Bytes  | " + symbolInfoSize);
            logger.fine("Symbol List Size   | " + symbolList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    public void parseDataTypeInfoList() {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            UploadInfo uploadInfo = adsClient.readUploadInfo();
            long dataTypeInfoSize = uploadInfo.getDataTypeLength() * uploadInfo.getDataTypeCount();

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();
            
            logger.fine("Parse Data Type Info     | " + amsNetId + " | " + amsPort);

            dataTypeInfoList.clear();
            dataTypeInfoList.addAll(adsClient.readDataTypeInfoList());

            logger.fine("Data Type Info Bytes     | " + dataTypeInfoSize);
            logger.fine("Data Type Info List Size | " + dataTypeInfoList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    public void parseSymbolInfoList() { 
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();
            
            UploadInfo uploadInfo = adsClient.readUploadInfo();
            long symbolInfoSize = uploadInfo.getSymbolLength() * uploadInfo.getSymbolCount();

            logger.fine("Parse Symbol Table    | " + amsNetId + " | " + amsPort);

            symbolInfoList.clear();
            symbolInfoList.addAll(adsClient.readSymbolInfoList());

            logger.fine("Symbol Info Byte Size | " + symbolInfoSize);
            logger.fine("Symbol Info List Size | " + symbolInfoList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    public void parseSymbolTable() {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();
            
            UploadInfo uploadInfo = adsClient.readUploadInfo();
            long symbolInfoSize = uploadInfo.getSymbolLength() * uploadInfo.getSymbolCount();
            long dataTypeInfoSize = uploadInfo.getDataTypeLength() * uploadInfo.getDataTypeCount();

            logger.fine("Parse Symbol Table       | " + amsNetId + " | " + amsPort);

            symbolInfoList.clear();
            symbolInfoList.addAll(adsClient.readSymbolInfoList());

            logger.fine("Symbol Info Byte Size    | " + symbolInfoSize);
            logger.fine("Symbol Info List Size    | " + symbolInfoList.size());

            dataTypeInfoList.clear();
            dataTypeInfoList.addAll(adsClient.readDataTypeInfoList());

            logger.fine("Data Type Info Bytes     | " + dataTypeInfoSize);
            logger.fine("Data Type Info List Size | " + dataTypeInfoList.size());

            symbolList.clear();
            symbolList.addAll(getSymbolList(adsClient.readSymbolInfoList()));

            logger.fine("Symbol List Size         | " + symbolList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    public Symbol getSymbol(SymbolInfo symbolInfo) {
        Symbol symbol = new Symbol();
        symbol.setSymbolName(symbolInfo.getSymbolName());

        // redeclare array to big type
        if (symbolInfo.getTypeInfo().getArray().isEmpty()) {
            symbol.setDataType(symbolInfo.getDataType());
        } else {
            symbol.setDataType(DataType.BIGTYPE);
        }

        return symbol;
    }

    public List<Symbol> getSymbolList(List<SymbolInfo> symbolInfoList) {
        List<Symbol> symbolList = new ArrayList<Symbol>();

        for (SymbolInfo symbolInfo : symbolInfoList) {
            Symbol symbol = getSymbol(symbolInfo);
            symbolList.add(symbol);
        }

        return symbolList;
    }

    public List<Symbol> getSymbolList(Symbol symbol) {
        return getSymbolList(symbol.getSymbolName());
    }

    public List<Symbol> getSymbolList(String symbolName) {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            if (dataTypeInfoList.isEmpty()) {
                dataTypeInfoList.addAll(adsClient.readDataTypeInfoList());
            }

            SymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolName);
            return symbolInfo.getSymbolList(dataTypeInfoList);
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }

        return new ArrayList<Symbol>();
    }
    
    public void clear() {
        symbolList.clear();
        symbolInfoList.clear(); 
        dataTypeInfoList.clear();
    }
}
