package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbolDataTypeInfo;
import twincat.ads.AdsSymbolInfo;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AmsPort;

public class AdsSymbolBySymbolInfoUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

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
            List<AdsSymbolDataTypeInfo> symbolDataTypeInfoList = adsClient.readDataTypeInfoList();
            List<AdsSymbolInfo> symbolInfoList = symbolInfo.getSymbolInfoList(symbolDataTypeInfoList);

            for (AdsSymbolInfo symbolInfoIterator : symbolInfoList) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Type: " + symbolInfoIterator.getDataType());
                stringBuilder.append("\t| ");
                stringBuilder.append("Name: " + symbolInfoIterator.getSymbolName());
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
