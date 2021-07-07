package twincat.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;
import twincat.ads.container.AdsRoute;
import twincat.ads.container.AdsRouteHandler;
import twincat.ads.worker.AdsSymbolLoader;

public class GeneralFunctionTest {

    public static void main(String[] args) {
        new GeneralFunctionTest();
    }
    
    public GeneralFunctionTest() {
        doSomething();
    }

    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private void doSomething() {
        AdsClient adsClient = new AdsClient();
        
        List<AdsRoute> routeList = new ArrayList<AdsRoute>();
        
        try {
            adsClient.open();
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.SYSTEMSERVICE);
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);
            
            AdsRoute localRoute = new AdsRoute();
            localRoute.setAmsNetId(adsClient.readLocalAmsNetId());
            localRoute.setHostName(adsClient.readLocalHostName());

            routeList.add(localRoute);
            routeList.addAll(adsClient.readRouteEntrys());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }


        List<AdsRouteHandler> routeSymbolHandlerList = new ArrayList<AdsRouteHandler>(); 
        
        for (AdsRoute route : routeList) {
            List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>(); 
            for (AmsPort amsPort : AmsPort.values()) {
                
                try {
                    adsClient.open();
                    adsClient.setTimeout(10);
                    adsClient.setAmsNetId(route.getAmsNetId());
                    adsClient.setAmsPort(amsPort);
                    adsClient.readUploadInfo();

                    // ams port holds symbol data
                    AdsSymbolLoader symbolLoader = adsClient.getSymbolLoader();
                    symbolLoaderList.add(symbolLoader);
                } catch (AdsException e) {
                    // skip port
                } finally {
                    adsClient.close();     
                }
            }
            
            if (!symbolLoaderList.isEmpty()) {
                AdsRouteHandler routeSymbolHandler = new AdsRouteHandler(route); 
                routeSymbolHandler.getSymbolLoaderList().addAll(symbolLoaderList); 
                routeSymbolHandlerList.add(routeSymbolHandler);
            }
        } 
        
        for (AdsRouteHandler routeSymbolHandler : routeSymbolHandlerList) {
            String hostName = routeSymbolHandler.getRoute().getHostName();

            for (AdsSymbolLoader symbolLoader : routeSymbolHandler.getSymbolLoaderList()) {
                String amsNetId = symbolLoader.getAds().getAmsNetId();
                AmsPort amsPort = symbolLoader.getAds().getAmsPort();
                int symbolListSize = symbolLoader.getSymbolList().size();
                
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("HostName: " + String.format("%-10s", hostName) + " | ");
                stringBuilder.append("AmsNetId: " + String.format("%-10s", amsNetId) + " | ");
                stringBuilder.append("AmsPort: " + String.format("%-10s", amsPort) + " | ");
                stringBuilder.append("SymbolListSize: " + String.format("%-10s", symbolListSize));
                
                logger.info(stringBuilder.toString());
            }
        }
        
    }
}
