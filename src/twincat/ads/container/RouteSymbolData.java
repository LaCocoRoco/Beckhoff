package twincat.ads.container;

import twincat.ads.worker.SymbolLoader;

public class RouteSymbolData {
    
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private final Route route;
 
    private final SymbolLoader symbolLoader;
    
    /*************************/
    /****** constructor ******/
    /*************************/
        
    public RouteSymbolData(Route route, SymbolLoader symbolLoader) {
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
 
}
