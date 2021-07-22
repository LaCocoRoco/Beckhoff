package twincat.ads.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Route;
import twincat.ads.common.RouteSymbolData;
import twincat.ads.constant.AdsData;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class RouteLoader extends Observable {
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

    public List<Route> loadRouteList() {
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

    public void loadRouteSymbolDataList(AmsPort amsPort) {
        List<Route> routeList = loadRouteList();

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
                // skip read exception
            } finally {
                adsClient.close();
            }
        }
    }

    public void loadRouteSymbolDataList() {
        List<Route> routeList = loadRouteList();

        for (Route route : routeList) {
            String amsNetId = route.getAmsNetId();

            for (AmsPort amsPort : AdsData.AMS_PORT_SYMBOL_LIST) {
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
                    // skip read exception
                } finally {
                    adsClient.close();
                }
            }
        }
    }

    public void clearRouteSymbolDataList() {
        routeSymbolDataList.clear();
    }   
}
