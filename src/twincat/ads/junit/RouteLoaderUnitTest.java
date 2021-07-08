package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.RouteSymbolHandler;
import twincat.ads.worker.SymbolLoader;

public class RouteLoaderUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void startAdsClient() {
        adsClient.open();
    }
    
    @Test
    public void routeLoaderUnitTest() {
        List<RouteSymbolHandler> routeSymbolLoaderList = RouteSymbolHandler.getRouteHandlerList();
        
        for (RouteSymbolHandler routeSymbolLoader : routeSymbolLoaderList) {
            String hostName = routeSymbolLoader.getRoute().getHostName();

            SymbolLoader symbolLoader = routeSymbolLoader.getSymbolLoader();
            String amsNetId = symbolLoader.getAmsNetId();
            AmsPort amsPort = symbolLoader.getAmsPort();
            int symbolListSize = symbolLoader.getSymbolList().size();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HostName: " + String.format("%-8s", hostName) + " | ");
            stringBuilder.append("AmsNetId: " + String.format("%-8s", amsNetId) + " | ");
            stringBuilder.append("AmsPort: " + String.format("%-8s", amsPort) + " | ");
            stringBuilder.append("SymbolSize: " + String.format("%-8s", symbolListSize));

            logger.info(stringBuilder.toString());
        }
    }
    
    @After
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }  
}
