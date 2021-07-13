package twincat.ads.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.Route;
import twincat.ads.container.RouteSymbolData;

public class RouteSymbolLoader extends Observable {
    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int READ_UPLOAD_INFO_TIMEOUT = 10;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private String loadingState = new String();

    /*********************************/
    /***** global final variable *****/
    /*********************************/

    private final AdsClient adsClient = new AdsClient();

    private final List<RouteSymbolData> routeSymbolDataList = new ArrayList<RouteSymbolData>();

    private final Logger logger = TwincatLogger.getLogger();

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public String getLoadingState() {
        return loadingState;
    }

    public void setLoadingState(String loadingState) {
        this.loadingState = loadingState;
    }

    public List<RouteSymbolData> getRouteSymbolDataList() {
        return routeSymbolDataList;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void clearRouteSymbolData() {
        routeSymbolDataList.clear();
    }
    
    public void loadRouteSymbolDataList(AmsPort amsPort) {
        List<Route> routeList = loadRouteList();

        logger.fine("Load Route Symbol List");
        for (Route route : routeList) {
            String amsNetId = route.getAmsNetId();

            try {
                loadingState = amsNetId + " | " + amsPort;

                setChanged();
                notifyObservers();

                adsClient.open();
                adsClient.setAmsNetId(amsNetId);
                adsClient.setAmsPort(amsPort);
                adsClient.setTimeout(READ_UPLOAD_INFO_TIMEOUT);
                adsClient.readUploadInfo();

                logger.fine(loadingState);

                // ams port holds symbol data
                SymbolLoader symbolLoader = new SymbolLoader();
                symbolLoader.setAmsPort(amsPort);
                symbolLoader.setAmsNetId(amsNetId);
                symbolLoader.parseSymbolList();

                RouteSymbolData routeSymbolHandler = new RouteSymbolData(route, symbolLoader);
                routeSymbolDataList.add(routeSymbolHandler);
            } catch (AdsException e) {
                // skip port exceptions
            } finally {
                adsClient.close();
            }
        }

        logger.fine("Load Route Symbol Data Done");
    }

    public void loadRouteSymbolDataList() {
        List<Route> routeList = loadRouteList();

        logger.fine("Load Route Symbol List");

        for (Route route : routeList) {
            String amsNetId = route.getAmsNetId();

            for (AmsPort amsPort : AmsPort.values()) {
                try {
                    loadingState = amsNetId + " | " + amsPort;

                    setChanged();
                    notifyObservers();

                    adsClient.open();
                    adsClient.setAmsNetId(amsNetId);
                    adsClient.setAmsPort(amsPort);
                    adsClient.setTimeout(READ_UPLOAD_INFO_TIMEOUT);
                    adsClient.readUploadInfo();

                    logger.fine(loadingState);

                    // ams port holds symbol data
                    SymbolLoader symbolLoader = new SymbolLoader();
                    symbolLoader.setAmsPort(amsPort);
                    symbolLoader.setAmsNetId(amsNetId);
                    symbolLoader.parseSymbolList();

                    RouteSymbolData routeSymbolHandler = new RouteSymbolData(route, symbolLoader);
                    routeSymbolDataList.add(routeSymbolHandler);
                } catch (AdsException e) {
                    // skip port exceptions
                } finally {
                    adsClient.close();
                }
            }
        }

        logger.fine("Load Route Symbol Data Done");
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private List<Route> loadRouteList() {
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

        return routeList;
    }
}
