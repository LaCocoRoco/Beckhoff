package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.common.Symbol;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.SymbolLoader;

public class SymbolLoaderBigTypeUnitTest {
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String symbolName = ".junit_array_complex";

    @Test
    public void test() {
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(AmsNetId.LOCAL);
        symbolLoader.setAmsPort(AmsPort.TC2PLC1);
        symbolLoader.parseSymbolList();
        
        for (Symbol symbol : symbolLoader.getSymbolList(symbolName)) {
            String type = String.format("%-8s", symbol.getDataType().toString());
            logger.info("Type: " + type + "\t| Name: " + symbol.getSymbolName());
        }
    }
}
