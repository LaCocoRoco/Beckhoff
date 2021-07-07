package twincat.ads.container;

import twincat.ads.worker.AdsSymbolLoader;

public class AdsRouteSymbolData {
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private final AdsRoute route;
  
    private final AdsSymbolLoader symbolLoader;

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public AdsRouteSymbolData(AdsRoute route, AdsSymbolLoader symbolLoader) {
        this.route = route;
        this.symbolLoader = symbolLoader;
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public AdsRoute getRoute() {
        return route;
    }

    public AdsSymbolLoader getSymbolLoader() {
        return symbolLoader;
    }
}
