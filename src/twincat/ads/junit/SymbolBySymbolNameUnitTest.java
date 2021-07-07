package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.DataTypeInfo;
import twincat.ads.container.Symbol;
import twincat.ads.container.SymbolInfo;

public class SymbolBySymbolNameUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    private final String symbolName = ".junit_array_complex";
    
    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    @Test
    public void symbolBySymbolInfoUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            SymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolName);
            List<DataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            List<Symbol> symbolList = symbolInfo.getSymbolList(dataTypeInfoList);

            for (Symbol symbol : symbolList) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Type: " + symbol.getDataType());
                stringBuilder.append("\t| ");
                stringBuilder.append("Name: " + symbol.getSymbolName());
                logger.info(stringBuilder.toString());
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }
}
