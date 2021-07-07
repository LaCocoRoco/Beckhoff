package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.AdsSymbol;
import twincat.ads.worker.AdsSymbolLoader;

public class AdsSymbolLoaderUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void startAds() {
        adsClient.open();
    }
    
    @Test
    public void adsSymbolLoaderUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);

            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsClient);
            symbolLoader.parseSymbolList();
   
            for (AdsSymbol symbol : symbolLoader.getSymbolList()) {
                String name = symbol.getSymbolName();
                String type = String.format("%-8s", symbol.getDataType().toString());
                logger.info("Type: " + type + "| Name: " + name);             
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
