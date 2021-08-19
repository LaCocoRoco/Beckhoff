package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.RouteSymbolData;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.RouteLoader;
import twincat.ads.worker.SymbolLoader;

public class RouteLoaderUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void start() {
        adsClient.open();
    }
    
    @Test
    public void test() {
        RouteLoader routeLoader = new RouteLoader();       
        routeLoader.loadRouteSymbolDataList();
        
        for (RouteSymbolData routeSymbolData : routeLoader.getRouteSymbolDataList()) {
            String hostName = routeSymbolData.getRoute().getHostName();

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();
            String amsNetId = symbolLoader.getAmsNetId();
            AmsPort amsPort = symbolLoader.getAmsPort();
            int symbolListSize = symbolLoader.getSymbolList().size();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HostName: "   + String.format("%-8s", hostName)   + " | ");
            stringBuilder.append("AmsNetId: "   + String.format("%-8s", amsNetId)   + " | ");
            stringBuilder.append("AmsPort: "    + String.format("%-8s", amsPort)    + " | ");
            stringBuilder.append("SymbolSize: " + String.format("%-8s", symbolListSize));

            logger.info(stringBuilder.toString());
        }
    }
    
    @After
    public void stop() throws AdsException {
        adsClient.close();
    }  
}
