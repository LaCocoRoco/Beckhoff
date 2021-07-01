package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsException;

public class AdsAmsNetIdUnitTest {
    Ads ads = new Ads();
     
    @Before
    public void startAds() {
        ads.open();
    }
    
    @Test
    public void adsAmsNetIdUnitTest() {
        Logger logger = TwincatLogger.getSignedLogger();
        
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
