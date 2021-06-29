package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.constants.AdsDataType;
import twincat.ads.container.AdsSymbol;

public class AdsSymbolLoaderUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolLoaderUnitTest() {
        AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

        // load full symbol table
        // WARNING: will crash with big project
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

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
