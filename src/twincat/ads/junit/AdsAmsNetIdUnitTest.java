package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;

public class AdsAmsNetIdUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();
    
    @Before
    public void startAds() {
        ads.open();
    }
    
    @Test
    public void adsAmsNetIdUnitTest() {
        try {
            logger.info(ads.readAmsNetId());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
