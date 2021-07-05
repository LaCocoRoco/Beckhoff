package twincat.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsRoute;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AmsPort;

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
        List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>();
        
        try {
            adsClient.open();
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.SYSTEMSERVICE);
            
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

        logger.info("Generate Symbol Loader");
        
        for (AdsRoute route : routeList) {
            for (AmsPort amsPort : AmsPort.values()) {
                
                try {
                    // is symbol data available
                    adsClient.open();
                    adsClient.setTimeout(10);
                    adsClient.setAmsNetId(route.getAmsNetId());
                    adsClient.setAmsPort(amsPort);
                    adsClient.readUploadInfo();

                    symbolLoaderList.add(adsClient.getSymbolLoader());
                    logger.info("Stop : " + adsClient.getAmsPort());
                } catch (AdsException e) {
                    /* ignore ads error */
                } finally {
                    adsClient.close();     
                }
            }
        } 
        
        
 
        logger.info("######");
        
        for (AdsSymbolLoader symbolLoader : symbolLoaderList) {
            logger.info("AmsNetId: " + symbolLoader.getAds().getAmsNetId());
            logger.info("AmsPort : " + symbolLoader.getAds().getAmsPort());
            
            List<AdsSymbol> symbolList = symbolLoader.getSymbols();
            logger.info("SymbolListSize: " + symbolList.size());
        }
        
    }
}
