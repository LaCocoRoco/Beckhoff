package twincat.ads;

import java.util.ArrayList;
import java.util.List;

public class AdsRouteHandler {
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private final AdsRoute route;
  
    private final List<AdsSymbolLoader> symbolLoader = new ArrayList<AdsSymbolLoader>();

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public AdsRouteHandler(AdsRoute route) {
        this.route = route;
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public AdsRoute getRoute() {
        return route;
    }

    public List<AdsSymbolLoader> getSymbolLoaderList() {
        return symbolLoader;
    }   
}
