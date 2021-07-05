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

    // TODO : 
    
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private void doSomething() {
        AdsClient ads = new AdsClient();
        AdsRoute localRoute = new AdsRoute();
        List<AdsRoute> routeList = new ArrayList<AdsRoute>();
        List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>();
        
        try {
            ads.open();
            ads.setAmsNetId(AmsNetId.LOCAL);
            ads.setAmsPort(AmsPort.SYSTEMSERVICE);
            
            localRoute.setAmsNetId(ads.readLocalAmsNetId());
            localRoute.setHostName(ads.readLocalHostName());

            routeList.add(localRoute);
            routeList.addAll(ads.readRouteEntrys());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            ads.close();
        }

        logger.info("Generate Symbol Loader");
        
        
        
        for (AdsRoute route : routeList) {
            for (AmsPort amsPort : AmsPort.values()) {
                
                try {
                    // is symbol data available
                    ads.open();
                    ads.setTimeout(10);
                    ads.setAmsNetId(route.getAmsNetId());
                    ads.setAmsPort(amsPort);
                    ads.readUploadInfo();
                    
                    // initialize symbol loader
                    AdsClient adsRouterClient = new AdsClient();
                    adsRouterClient.setAmsNetId(ads.getAmsNetId());
                    adsRouterClient.setAmsPort(ads.getAmsPort());
                    
                    AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsRouterClient);
                    symbolLoaderList.add(symbolLoader);
                    
                    logger.info("Stop : " + ads.getAmsPort());

                } catch (AdsException e) {
                    /* ignore ads error */
                } finally {
                    ads.close();     
                }
            }
        } 
        
        
 
        logger.info("######");
        
        for (AdsSymbolLoader symbolLoader : symbolLoaderList) {
            logger.info("AmsNetId: " + symbolLoader.getAds().getAmsNetId());
            logger.info("AmsPort : " + symbolLoader.getAds().getAmsPort());
            
            List<AdsSymbol> symbolList = symbolLoader.getSymbolTable();
            logger.info("SymbolListSize: " + symbolList.size());
            
            /*
            for (AdsSymbol symbol : symbolList) {
              
            }
            */
        }
        
    }
}
