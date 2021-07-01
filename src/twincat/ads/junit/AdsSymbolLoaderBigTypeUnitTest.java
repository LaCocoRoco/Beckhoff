package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;

public class AdsSymbolLoaderBigTypeUnitTest {
    Ads ads = new Ads();
    Logger logger = TwincatLogger.getSignedLogger();

    @Test
    public void adsSymbolLoaderUnitTest() {
        AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

        List<AdsSymbol> symbolList = symbolLoader.getSymbolBySymbolName(".juinit_p_simple");
        
        for (AdsSymbol symbol : symbolList) {
            String type = String.format("%-8s", symbol.getType().toString());
            logger.info("Type: " + type + "\t| Name: " + symbol.getName());
        }
    }
}
