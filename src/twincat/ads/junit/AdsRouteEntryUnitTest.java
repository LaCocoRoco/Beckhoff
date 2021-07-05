package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsRoute;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AmsPort;

public class AdsRouteEntryUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolInfoNodeUnitTest() {
        try {
            ads.setAmsNetId(AmsNetId.LOCAL);
            ads.setAmsPort(AmsPort.SYSTEMSERVICE);
            
            List<AdsRoute> routeList = ads.readRouteEntrys();
            
            for (AdsRoute route : routeList) {
                logger.info("HostName   : " + route.getHostName());
                logger.info("HostAddress: " + route.getHostAddress());
                logger.info("AmsNetId   : " + route.getAmsNetId());
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
