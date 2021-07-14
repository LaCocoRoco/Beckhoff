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
import twincat.ads.container.TypeInfo;
import twincat.ads.container.UploadInfo;

public class SymbolLoader {
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private final AdsClient adsClient = new AdsClient();
 
    /*********************************/
    /***** global final variable *****/
    /*********************************/
    
    private final List<Symbol> symbolList = new ArrayList<Symbol>();

    private final List<SymbolInfo> symbolInfoList = new ArrayList<SymbolInfo>();

    private final List<DataTypeInfo> dataTypeInfoList = new ArrayList<DataTypeInfo>();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final Logger logger = TwincatLogger.getLogger();

    /*********************************/
    /******** setter & getter ********/
    /*********************************/
    
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

    /*********************************/
    /********* public method *********/
    /*********************************/

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
            symbolList.addAll(getSymbolList(symbolInfoList));

            logger.fine("Symbol List Size         | " + symbolList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    public List<Symbol> getSymbolList(Symbol symbol) {
        return getSymbolList(symbol.getSymbolName());
    }

    public List<Symbol> getSymbolList(String symbolName) {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            // read data type info
            if (dataTypeInfoList.isEmpty()) {
                dataTypeInfoList.addAll(adsClient.readDataTypeInfoList());
            }

            // read symbol info
            SymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolName); 
            
            return getSymbolList(symbolInfo, dataTypeInfoList);
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
    
    /*********************************/
    /******** private method *********/
    /*********************************/

    private List<Symbol> getSymbolList(List<SymbolInfo> symbolInfoList) {
        List<Symbol> symbolList = new ArrayList<Symbol>();

        for (SymbolInfo symbolInfo : symbolInfoList) {
            Symbol symbol = new Symbol();
            symbol.setSymbolName(symbolInfo.getSymbolName());
  
            // read symbol info type info
            TypeInfo symbolInfoTypeInfo = new TypeInfo();
            symbolInfoTypeInfo.parseTypeInfo(symbolInfo.getType());
            
            // redeclare array to big type
            if (symbolInfoTypeInfo.getArray().isEmpty()) {
                symbol.setDataType(symbolInfo.getDataType());
            } else {
                symbol.setDataType(DataType.BIGTYPE);
            }

            symbolList.add(symbol);
        }

        return symbolList;
    }

    private List<Symbol> getSymbolList(SymbolInfo symbolInfo, List<DataTypeInfo> dataTypeInfoList) {
        List<Symbol> symbolList = new ArrayList<Symbol>();

        // read symbol info type info
        TypeInfo symbolInfoTypeInfo = new TypeInfo();
        symbolInfoTypeInfo.parseTypeInfo(symbolInfo.getType());
        
        // dismiss pointer
        if (symbolInfoTypeInfo.isPointer()) return symbolList;

        // is complex data type
        if (symbolInfo.getDataType().equals(DataType.BIGTYPE)) {
            // search data type info
            for (DataTypeInfo dataTypeInfo : dataTypeInfoList) {
                if (symbolInfoTypeInfo.getType().equals(dataTypeInfo.getDataTypeName())) {
                    // build every internal data type
                    for (DataTypeInfo internalDataTypeInfo : dataTypeInfo.getInternalSymbolDataTypeInfoList())  {      
                        
                        // read data type info type info
                        TypeInfo internalDataTypeInfoTypeInfo = new TypeInfo();
                        internalDataTypeInfoTypeInfo.parseTypeInfo(internalDataTypeInfo.getType());
                        
                        if (!symbolInfoTypeInfo.getArray().isEmpty()) {       
                            // concatenate symbol and array type info
                            for (String typeInfoArray : symbolInfoTypeInfo.getArray()) {
                                Symbol symbol = new Symbol();
                                String internalDataTypeName = "." + internalDataTypeInfo.getDataTypeName();
                                symbol.setSymbolName(symbolInfo.getSymbolName() + typeInfoArray + internalDataTypeName);

                                // redeclare array to big type
                                if (internalDataTypeInfoTypeInfo.getArray().isEmpty()) {
                                    symbol.setDataType(internalDataTypeInfo.getDataType());
                                } else {
                                    symbol.setDataType(DataType.BIGTYPE);
                                }
                                
                                symbolList.add(symbol);
                            }
                        } else {
                            Symbol symbol = new Symbol();
                            symbol.setSymbolName(symbolInfo.getSymbolName() + "." + internalDataTypeInfo.getDataTypeName());
                            
                            // redeclare array to big type
                            if (internalDataTypeInfoTypeInfo.getArray().isEmpty()) {
                                symbol.setDataType(internalDataTypeInfo.getDataType());
                            } else {
                                symbol.setDataType(DataType.BIGTYPE);
                            }

                            symbolList.add(symbol);
                        }  
                    }
                    break;
                }
            }
        }

        // is simple data type
        if (!symbolInfo.getDataType().equals(DataType.BIGTYPE)) {
            if (!symbolInfoTypeInfo.getArray().isEmpty()) {       
                // concatenate symbol and array type info
                for (String typeInfoArray : symbolInfoTypeInfo.getArray()) {
                    Symbol symbol = new Symbol();
                    symbol.setSymbolName(symbolInfo.getSymbolName() + typeInfoArray);
                    symbol.setDataType(symbolInfo.getDataType());
                    symbolList.add(symbol);
                }
            } else {
                Symbol symbol = new Symbol();
                symbol.setSymbolName(symbolInfo.getSymbolName());
                symbol.setDataType(symbolInfo.getDataType());
                symbolList.add(symbol);
            }
        }

        return symbolList;
    }   
    
}
