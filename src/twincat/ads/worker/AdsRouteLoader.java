package twincat.ads.worker;

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

public class AdsRouteLoader {
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
    
    public AdsRouteLoader(AdsClient adsClient) {
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
            logger.info("AdsRouteLoader: " + e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
        
        logger.info("AdsRouteLoader : Adding Route Symbol List");
        
        for (AdsRoute route : routeList) {
            String amsNetId = route.getAmsNetId();
            List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>(); 
            //for (AmsPort amsPort : AmsPort.values()) {
            for (int i = 0; i < 20; i++) {
                try {
                    // TODO : only necessary ports
                    AmsPort amsPort = AmsPort.TC2PLC1;
                    adsClient.open();
                    //adsClient.setTimeout(10);
                    adsClient.setAmsNetId(amsNetId);
                    adsClient.setAmsPort(amsPort);
                    //adsClient.readUploadInfo();

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
        
        logger.info("AdsRouteLoader : Route Symbol List Loaded");
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public List<AdsRouteHandler> getRouteSymbolHandlerList() {
        return routeHandlerList;
    }
   
}
