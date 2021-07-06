package twincat.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.enums.AmsPort;

public class AdsRouteSymbolLoader {
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private final  List<AdsRouteHandler> routeHandlerList = new ArrayList<AdsRouteHandler>();   

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final Logger logger = TwincatLogger.getSignedLogger();

    /*************************/
    /****** constructor ******/
    /*************************/
     
    public AdsRouteSymbolLoader(AdsClient adsClient) {
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
        
        logger.info("AdsRouteSymbolLoader : Adding Route Symbol List");
        
        for (AdsRoute route : routeList) {
            String amsNetId = route.getAmsNetId();
            List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>(); 
            for (AmsPort amsPort : AmsPort.values()) {
                
                try {
                    // TODO : nur benötigt ports loopen
                    adsClient.open();
                    adsClient.setTimeout(10);
                    adsClient.setAmsNetId(amsNetId);
                    adsClient.setAmsPort(amsPort);
                    adsClient.readUploadInfo();

                    //ams port holds symbol data
                    AdsSymbolLoader symbolLoader = adsClient.getSymbolLoader();
                    symbolLoaderList.add(symbolLoader);
                } catch (AdsException e) {
                    // skip port
                } finally {
                    adsClient.close();     
                }
            }
            
            if (!symbolLoaderList.isEmpty()) {
                AdsRouteHandler routeHandler = new AdsRouteHandler(route); 
                routeHandler.getSymbolLoaderList().addAll(symbolLoaderList); 
                routeHandlerList.add(routeHandler);
            }
        }
        
        logger.info("AdsRouteSymbolLoader : Route Symbol List Loaded");
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public List<AdsRouteHandler> getRouteSymbolHandlerList() {
        return routeHandlerList;
    }
   
}
