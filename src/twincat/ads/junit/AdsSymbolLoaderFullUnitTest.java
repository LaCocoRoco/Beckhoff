package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AdsDataType;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.AdsSymbol;
import twincat.ads.worker.AdsSymbolLoader;

public class AdsSymbolLoaderFullUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsSymbolLoaderFullUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);

            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsClient);
            symbolLoader.parseSymbolList();

            // load symbols from symbol info
            for (AdsSymbol symbol : symbolLoader.getSymbolList()) {
                if (symbol.getDataType().equals(AdsDataType.BIGTYPE)) {
                    
                    // load symbols from big type symbol
                    for (AdsSymbol bigTypeSymbol : symbolLoader.getSymbolList(symbol)) {
                        
                        // only print none big type symbols
                        if (!bigTypeSymbol.getDataType().equals(AdsDataType.BIGTYPE)) {
                            String name = bigTypeSymbol.getSymbolName();
                            String type = String.format("%-8s", bigTypeSymbol.getDataType().toString());
                            logger.info("Type: " + type + "| Name: " + name);   
                        }
                    }
                } else {
                    String name = symbol.getSymbolName();
                    String type = String.format("%-8s", symbol.getDataType().toString());
                    logger.info("Type: " + type + "| Name: " + name);
                }
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
