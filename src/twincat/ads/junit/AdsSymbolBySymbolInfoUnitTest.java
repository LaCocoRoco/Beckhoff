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
import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.container.AdsSymbol;
import twincat.ads.container.AdsSymbolInfo;

public class AdsSymbolBySymbolInfoUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    private final String symbolName = ".junit_array_complex";
    
    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsSymbolInfoNodeUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            AdsSymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolName);
            List<AdsDataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            List<AdsSymbol> symbolList = symbolInfo.getSymbolList(dataTypeInfoList);

            for (AdsSymbol symbol : symbolList) {
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
    public void stopAds() throws AdsException {
        adsClient.close();
    }
}
