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
import twincat.ads.container.AdsSymbol;

public class AdsSymbolLoaderBigTypeUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolLoaderUnitTest() {
        AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

        List<AdsSymbol> symbolList = symbolLoader.getSymbolBySymbolName(".JUINIT_P_BIG");
        
        for (AdsSymbol symbol : symbolList) {
            String type = String.format("%-8s", symbol.getType().toString());
            logger.info("Type: " + type + "\t| Name: " + symbol.getName());
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
