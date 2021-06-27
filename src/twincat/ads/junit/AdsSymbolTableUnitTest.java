package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsDataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.AdsSymbolInfo;

public class AdsSymbolTableUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @SuppressWarnings("unused")
    @Test
    public void adsDataTypeInfoTableUnitTest() {
        try {
            List<AdsSymbolInfo> symbolInfoList = ads.readSymbolInfoList();
            List<AdsDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
