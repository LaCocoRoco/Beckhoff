package twincat.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.AdsSymbolLoader;

public class GeneralFunctionTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    public static void main(String[] args) {
        new GeneralFunctionTest();
    }
    
    public GeneralFunctionTest() {
        doSomething();
    }

    private void doSomething() {
        try {
            logger.setLevel(Level.FINE);
            
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);

            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsClient);
            symbolLoader.parseSymbolTable();
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } 
    }
}
