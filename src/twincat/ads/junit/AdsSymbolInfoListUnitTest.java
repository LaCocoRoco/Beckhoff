package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbolInfo;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AmsPort;

public class AdsSymbolInfoListUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolInfoListUnitTest() {
        try {
            ads.setAmsNetId(AmsNetId.LOCAL);
            ads.setAmsPort(AmsPort.TC2PLC1);
            
            List<AdsSymbolInfo> symbolInfoList = ads.readSymbolInfoList();

            for (AdsSymbolInfo symbolInfo : symbolInfoList) {
                logger.info("SymbolName    : " + symbolInfo.getSymbolName());
                logger.info("SymbolDataType: " + symbolInfo.getDataType());
                logger.info("SymbolType    : " + symbolInfo.getType());
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
