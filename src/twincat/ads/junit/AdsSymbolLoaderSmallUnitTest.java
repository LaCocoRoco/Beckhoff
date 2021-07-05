package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AmsPort;

public class AdsSymbolLoaderSmallUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    @Test
    public void adsSymbolLoaderFullUnitTest() {
        try {
            ads.setAmsNetId(AmsNetId.LOCAL);
            ads.setAmsPort(AmsPort.TC2PLC1);
            
            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

            for (AdsSymbol symbol : symbolLoader.getSymbolTable()) {
                String name = symbol.getName();
                String type = String.format("%-8s", symbol.getType().toString());
                logger.info("Type: " + type + "| Name: " + name);             
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }
}
