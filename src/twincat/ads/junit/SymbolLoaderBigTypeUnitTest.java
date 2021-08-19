package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.common.Symbol;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.SymbolLoader;

/** 
 * VAR_GLOBAL
 *     junit_array_small       :ARRAY[0..2] OF INT;
 *     junit_array_simple      :ARRAY[0..2, 2..4, 4..6] OF INT;
 *     junit_array_complex     :ARRAY[0..2, 4..8] OF TON;
 *     junit_array_index_var   :ARRAY[junit_a..junit_b, junit_c..junit_d] OF INT;
 * END_VAR
 *
 * VAR_GLOBAL CONSTANT
 *     junit_a                 :INT := 2;
 *     junit_b                 :INT := 5;
 *     junit_c                 :INT := 3;
 *     junit_d                 :INT := 20;
 * END_VAR
 */

public class SymbolLoaderBigTypeUnitTest {
    private final Logger logger = TwincatLogger.getLogger();
    
    @Test
    public void test() {
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(AmsNetId.LOCAL);
        symbolLoader.setAmsPort(AmsPort.TC2PLC1);
        symbolLoader.parseSymbolList();
        
        for (Symbol symbol : symbolLoader.getSymbolList(".junit_array_complex")) {
            String type = String.format("%-8s", symbol.getDataType().toString());
            logger.info("Type: " + type + "\t| Name: " + symbol.getSymbolName());
        }
    }
}
