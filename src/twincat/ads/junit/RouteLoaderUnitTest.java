package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.RouteSymbolData;
import twincat.ads.worker.RouteSymbolLoader;
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
        RouteSymbolLoader routeSymbolLoader = new RouteSymbolLoader();       
        routeSymbolLoader.loadRouteSymbolDataList();
        
        for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
            String hostName = routeSymbolData.getRoute().getHostName();

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();
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
