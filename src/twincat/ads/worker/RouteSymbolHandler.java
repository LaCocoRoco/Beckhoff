package twincat.ads.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.Route;

public class RouteSymbolHandler {
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int READ_UPLOAD_INFO_TIMEOUT = 10;
    
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private final Route route;
  
    private final SymbolLoader symbolLoader;
   
    /*************************/
    /****** constructor ******/
    /*************************/
    
    public RouteSymbolHandler(Route route, SymbolLoader symbolLoader) {
        this.route = route;
        this.symbolLoader = symbolLoader;
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public Route getRoute() {
        return route;
    }

    public SymbolLoader getSymbolLoader() {
        return symbolLoader;
    } 

    /*************************/
    /** public static final **/
    /*************************/   
    
    public static final List<RouteSymbolHandler> getSymbolLoaderList(AmsNetId amsNetId) {
        return null;
    }
       
    public static final List<RouteSymbolHandler> getSymbolLoaderList(AmsPort amsPort) {
        return null;
    }
    
    public static final List<RouteSymbolHandler> getRouteHandlerList() {
        Logger logger = TwincatLogger.getLogger();
        AdsClient adsClient = new AdsClient();

        logger.fine("Loading Route List");
        
        List<Route> routeList = new ArrayList<Route>();
        
        try {
            adsClient.open();
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.SYSTEMSERVICE);
            adsClient.setTimeout(AdsClient.DEFAULT_TIMEOUT);

            Route localRoute = new Route();
            String localAmsNetId = adsClient.readLocalAmsNetId();
            String localHostName = adsClient.readLocalHostName();
            String localHostAddress = AmsNetId.netIdSTringToAddress(localAmsNetId);
            
            localRoute.setAmsNetId(localAmsNetId);
            localRoute.setHostName(localHostName);
            localRoute.setHostAddress(localHostAddress);
            
            routeList.add(localRoute);
            routeList.addAll(adsClient.readRouteEntrys());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }

        logger.fine("Loading Route Symbol List");
        
        List<RouteSymbolHandler> routeSymbolHandlerList = new ArrayList<RouteSymbolHandler>();
        
        for (Route route : routeList) {
            String amsNetId = route.getAmsNetId();

            for (AmsPort amsPort : AmsPort.values()) {
                try {
                    adsClient.open();
                    adsClient.setAmsNetId(amsNetId);
                    adsClient.setAmsPort(amsPort);
                    adsClient.setTimeout(READ_UPLOAD_INFO_TIMEOUT);
                    adsClient.readUploadInfo();

                    //ams port holds symbol data
                    SymbolLoader symbolLoader = new SymbolLoader();
                    symbolLoader.setAmsPort(amsPort);
                    symbolLoader.setAmsNetId(amsNetId);
                    symbolLoader.parseSymbolList();
                    
                    RouteSymbolHandler routeSymbolHandler = new RouteSymbolHandler(route, symbolLoader);         
                    routeSymbolHandlerList.add(routeSymbolHandler);
                } catch (AdsException e) {
                    // skip port exceptions
                } finally {
                    adsClient.close();     
                }
            }
        }
        
        logger.fine("Route Symbol Loader List Done"); 
        
        return routeSymbolHandlerList;
    }
}
