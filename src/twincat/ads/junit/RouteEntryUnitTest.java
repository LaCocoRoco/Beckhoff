package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Route;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class RouteEntryUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void start() {
        adsClient.open();
    }

    @Test
    public void test() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.SYSTEMSERVICE);
            
            List<Route> routeList = adsClient.readRouteEntrys();
            
            for (Route route : routeList) {
                logger.info("HostName   : " + route.getHostName());
                logger.info("HostAddress: " + route.getHostAddress());
                logger.info("AmsNetId   : " + route.getAmsNetId());
            }     
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stop() throws AdsException {
        adsClient.close();
    }
}
