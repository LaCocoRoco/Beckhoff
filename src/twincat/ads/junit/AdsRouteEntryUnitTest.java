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
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsSymbolInfoNodeUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.SYSTEMSERVICE);
            
            List<AdsRoute> routeList = adsClient.readRouteEntrys();
            
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
        adsClient.close();
    }
}
