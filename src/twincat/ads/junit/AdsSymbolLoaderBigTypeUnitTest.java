package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolLoader;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsSymbolLoaderBigTypeUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    private final String symbolName = ".juinit_p_simple";
    
    @Test
    public void adsSymbolLoaderUnitTest() {
        ads.setAmsNetId(AmsNetId.LOCAL);
        ads.setAmsPort(AmsPort.TC2PLC1);
        
        AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);

        List<AdsSymbol> symbolList = symbolLoader.getSymbolBySymbolName(symbolName);
        
        for (AdsSymbol symbol : symbolList) {
            String type = String.format("%-8s", symbol.getType().toString());
            logger.info("Type: " + type + "\t| Name: " + symbol.getName());
        }
    }
}
