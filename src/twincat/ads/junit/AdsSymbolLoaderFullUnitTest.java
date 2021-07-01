package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.constants.AdsDataType;

public class AdsSymbolLoaderFullUnitTest {
    Ads ads = new Ads();
    Logger logger = TwincatLogger.getSignedLogger();

    @Test
    public void adsSymbolLoaderFullUnitTest() {
        AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

        // WARNING: crash with big projects
        for (AdsSymbol symbol : symbolLoader.getSymbolTable()) {
            if (symbol.getType().equals(AdsDataType.BIGTYPE)) {
                List<AdsSymbol> bigSymbolList = symbolLoader.getSubNodesOfNode(symbol);
                for(AdsSymbol bigSymbol : bigSymbolList) {
                    String name = bigSymbol.getName();
                    String type = String.format("%-8s", bigSymbol.getType().toString());
                    logger.info("Type: " + type + "| Name: " + name); 
                }  
            } else {
                String name = symbol.getName();
                String type = String.format("%-8s", symbol.getType().toString());
                logger.info("Type: " + type + "| Name: " + name);             
            }
        }
    }
}
