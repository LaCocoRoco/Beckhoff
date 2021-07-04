package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsAmsNetIdUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
     
    @Before
    public void startAds() {
        ads.open();
    }
    
    @Test
    public void adsAmsNetIdUnitTest() {  
        try {
            ads.setAmsNetId(AmsNetId.LOCAL);
            ads.setAmsPort(AmsPort.TC2PLC1);
            
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
