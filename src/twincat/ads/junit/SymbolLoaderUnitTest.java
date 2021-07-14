package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.common.Symbol;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.SymbolLoader;

public class SymbolLoaderUnitTest {
    private final Logger logger = TwincatLogger.getLogger();

    @Test
    public void test() {
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(AmsNetId.LOCAL);
        symbolLoader.setAmsPort(AmsPort.TC2PLC1);
        symbolLoader.parseSymbolList();

        for (Symbol symbol : symbolLoader.getSymbolList()) {
            String name = symbol.getSymbolName();
            String type = String.format("%-8s", symbol.getDataType().toString());
            logger.info("Type: " + type + "| Name: " + name);
        }
    }
}
