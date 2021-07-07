package twincat.ads.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.AdsRoute;
import twincat.ads.container.AdsRouteSymbolData;

public class AdsRouteSymbolLoader {
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int READ_UPLOAD_INFO_TIMEOUT = 10;
    
    /*************************/
    /*** global attributes ***/
    /*************************/
  
    private final  List<AdsRouteSymbolData> routeSymbolsList = new ArrayList<AdsRouteSymbolData>();   

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final Logger logger = TwincatLogger.getLogger();

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public List<AdsRouteSymbolData> getRouteSymbolsList() {
        return routeSymbolsList;
    }
    
    /*************************/
    /********* public ********/
    /*************************/

    public void parseRouteHandlerList() {
        AdsClient adsClient = new AdsClient();
        List<AdsRoute> routeList = new ArrayList<AdsRoute>();

        logger.fine("Adding Routes");
        
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
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }

        logger.fine("Adding Route Symbol List");
        
        for (AdsRoute route : routeList) {
            String amsNetId = route.getAmsNetId();
            List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>();

            for (AmsPort amsPort : AmsPort.values()) {
                try {
                    adsClient.open();
                    adsClient.setTimeout(READ_UPLOAD_INFO_TIMEOUT);
                    adsClient.setAmsNetId(amsNetId);
                    adsClient.setAmsPort(amsPort);
                    adsClient.readUploadInfo();

                    //ams port holds symbol data
                    AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsClient);
                    symbolLoader.parseSymbolList();
                    
                    // TODO : combine route and loader here
                    // TODO : name complication
                    // TODO : next point net id and tree
                    symbolLoaderList.add(symbolLoader);
                } catch (AdsException e) {
                    // skip port exceptions
                } finally {
                    adsClient.close();     
                }
            }
            
            if (!symbolLoaderList.isEmpty()) {
                AdsRouteSymbolData routeSymbolData = new AdsRouteSymbolData(route); 
                routeSymbolData.getSymbolLoaderList().addAll(symbolLoaderList); 
                routeSymbolsList.add(routeSymbolData);
            }
        }
        
        logger.fine("Route Symbol List Loaded");
    }
}
