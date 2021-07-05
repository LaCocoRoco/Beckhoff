package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AdsDataType;
import twincat.ads.enums.AmsPort;

public class AdsSymbolLoaderFullUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    @Test
    public void adsSymbolLoaderFullUnitTest() {
        try {
            ads.setAmsNetId(AmsNetId.LOCAL);
            ads.setAmsPort(AmsPort.TC2PLC1);
            
            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

            // IMPORTEND: crash with big projects
            for (AdsSymbol symbol : symbolLoader.getSymbolTable()) {
                if (symbol.getType().equals(AdsDataType.BIGTYPE)) {
                    List<AdsSymbol> bigSymbolList = symbolLoader.getSubSymbolOfSymbol(symbol);
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
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }
}
