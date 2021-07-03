package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.constants.AdsDataType;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsSymbolLoaderFullUnitTest {
    AdsClient ads = new AdsClient();
    Logger logger = TwincatLogger.getSignedLogger();

    @Test
    public void adsSymbolLoaderFullUnitTest() {
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
    }
}
