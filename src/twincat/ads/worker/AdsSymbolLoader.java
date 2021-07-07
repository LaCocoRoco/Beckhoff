package twincat.ads.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AdsDataType;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.container.AdsSymbol;
import twincat.ads.container.AdsSymbolInfo;
import twincat.ads.container.AdsUploadInfo;

public class AdsSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private final AdsClient adsClient = new AdsClient();

    private final List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

    private final List<AdsSymbolInfo> symbolInfoList = new ArrayList<AdsSymbolInfo>();

    private final List<AdsDataTypeInfo> dataTypeInfoList = new ArrayList<AdsDataTypeInfo>();

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final Logger logger = TwincatLogger.getLogger();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolLoader(AdsClient adsClient) {
        try {
            this.adsClient.setAmsNetId(adsClient.getAmsNetId());
            this.adsClient.setAmsPort(adsClient.getAmsPort());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        }
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public AdsClient getAdsClient() {
        return adsClient;
    }

    public List<AdsSymbol> getSymbolList() {
        return symbolList;
    }

    public List<AdsSymbolInfo> getSymbolInfoList() {
        return symbolInfoList;
    }

    public List<AdsDataTypeInfo> getDataTypeInfoList() {
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

            AdsUploadInfo uploadInfo = adsClient.readUploadInfo();
            long symbolInfoSize = uploadInfo.getSymbolLength() * uploadInfo.getSymbolCount();

            logger.fine("Parse Symbol Table | " + amsNetId + " | " + amsPort);

            symbolList.clear();
            symbolList.addAll(getSymbolList(adsClient.readSymbolInfoList()));

            logger.fine("Symbol Info Bytes  | " + symbolInfoSize);
            logger.fine("Symbol List Size   | " + symbolList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            this.adsClient.close();
        }
    }

    public void parseDataTypeInfoList() {
        try {

            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();

            AdsUploadInfo uploadInfo = adsClient.readUploadInfo();
            long dataTypeInfoSize = uploadInfo.getDataTypeLength() * uploadInfo.getDataTypeCount();

            logger.fine("Parse Data Type Info     | " + amsNetId + " | " + amsPort);

            dataTypeInfoList.clear();
            dataTypeInfoList.addAll(adsClient.readDataTypeInfoList());

            logger.fine("Data Type Info Bytes     | " + dataTypeInfoSize);
            logger.fine("Data Type Info List Size | " + dataTypeInfoList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            this.adsClient.close();
        }
    }

    public void parseSymbolInfoList() {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();

            AdsUploadInfo uploadInfo = adsClient.readUploadInfo();
            long symbolInfoSize = uploadInfo.getSymbolLength() * uploadInfo.getSymbolCount();

            logger.fine("Parse Symbol Table    | " + amsNetId + " | " + amsPort);

            symbolInfoList.clear();
            symbolInfoList.addAll(adsClient.readSymbolInfoList());

            logger.fine("Symbol Info Byte Size | " + symbolInfoSize);
            logger.fine("Symbol Info List Size | " + symbolInfoList.size());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            this.adsClient.close();
        }
    }

    public void parseSymbolTable() {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            String amsNetId = adsClient.getAmsNetId();
            AmsPort amsPort = adsClient.getAmsPort();

            AdsUploadInfo uploadInfo = adsClient.readUploadInfo();
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
            this.adsClient.close();
        }
    }

    public AdsSymbol getSymbol(AdsSymbolInfo symbolInfo) {
        AdsSymbol symbol = new AdsSymbol();
        symbol.setSymbolName(symbolInfo.getSymbolName());

        // redeclare array to big type
        if (symbolInfo.getTypeInfo().getArray().isEmpty()) {
            symbol.setDataType(symbolInfo.getDataType());
        } else {
            symbol.setDataType(AdsDataType.BIGTYPE);
        }

        return symbol;
    }

    public List<AdsSymbol> getSymbolList(List<AdsSymbolInfo> symbolInfoList) {
        List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            AdsSymbol symbol = getSymbol(symbolInfo);
            symbolList.add(symbol);
        }

        return symbolList;
    }

    public List<AdsSymbol> getSymbolList(AdsSymbol symbol) {
        return getSymbolList(symbol.getSymbolName());
    }

    public List<AdsSymbol> getSymbolList(String symbolName) {
        try {
            adsClient.open();
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            if (dataTypeInfoList.isEmpty()) {
                dataTypeInfoList.addAll(adsClient.readDataTypeInfoList());
            }

            AdsSymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolName);
            return symbolInfo.getSymbolList(dataTypeInfoList);
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            this.adsClient.close();
        }

        return new ArrayList<AdsSymbol>();
    }
}
