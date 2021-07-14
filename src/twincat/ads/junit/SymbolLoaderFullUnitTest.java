package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.constant.DataType;
import twincat.ads.common.Symbol;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.SymbolLoader;

public class SymbolLoaderFullUnitTest {
    private final Logger logger = TwincatLogger.getLogger();

    @Test
    public void test() {
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(AmsNetId.LOCAL);
        symbolLoader.setAmsPort(AmsPort.TC2PLC1);
        symbolLoader.parseSymbolList();

        // load symbols from symbol info
        for (Symbol symbol : symbolLoader.getSymbolList()) {
            if (symbol.getDataType().equals(DataType.BIGTYPE)) {

                // load symbols from big type symbol
                for (Symbol bigTypeSymbol : symbolLoader.getSymbolList(symbol)) {

                    // only print none big type symbols
                    if (!bigTypeSymbol.getDataType().equals(DataType.BIGTYPE)) {
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
    }
}
