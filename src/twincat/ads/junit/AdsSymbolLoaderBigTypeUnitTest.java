package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;
import twincat.ads.container.AdsSymbol;
import twincat.ads.worker.AdsSymbolLoader;

public class AdsSymbolLoaderBigTypeUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    private final String symbolName = ".junit_array_complex";

    @Before
    public void startAds() {
        adsClient.open();
    }
    
    @Test
    public void adsSymbolLoaderUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            AdsSymbolLoader symbolLoader = adsClient.getSymbolLoader();
            for (AdsSymbol symbol : symbolLoader.getSymbolList(symbolName)) {
                String type = String.format("%-8s", symbol.getDataType().toString());
                logger.info("Type: " + type + "\t| Name: " + symbol.getName());
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }
    
    @After
    public void stopAds() throws AdsException {
        adsClient.close();
    }    
}
