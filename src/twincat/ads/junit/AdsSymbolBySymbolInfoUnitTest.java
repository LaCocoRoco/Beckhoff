package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsSymbolDataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolInfo;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsSymbolBySymbolInfoUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    private final String symbolName = ".junit_array_complex";
    
    @Before
    public void startAds() {
        ads.open();
        ads.setAmsNetId(AmsNetId.LOCAL);
        ads.setAmsPort(AmsPort.TC2PLC1);
    }

    @Test
    public void adsSymbolInfoNodeUnitTest() {
        try {
            AdsSymbolInfo symbolInfo = ads.readSymbolInfoBySymbolName(symbolName);

            List<AdsSymbolDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();
            List<AdsSymbol> symbolList = symbolInfo.getSymbolList(dataTypeInfoList);

            for (AdsSymbol symbol : symbolList) {
                logger.info("Type: " + symbol.getType() + "\tName: " + symbol.getName());
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
