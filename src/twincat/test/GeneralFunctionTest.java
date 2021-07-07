package twincat.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.SymbolLoader;

public class GeneralFunctionTest {
    private final Logger logger = TwincatLogger.getLogger();
    
    public static void main(String[] args) {
        new GeneralFunctionTest();
    }
    
    public GeneralFunctionTest() {
        generalFunctionTest();
    }

    private void generalFunctionTest() {
        logger.setLevel(Level.FINE);
        
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(AmsNetId.LOCAL);
        symbolLoader.setAmsPort(AmsPort.TC2PLC1);
        symbolLoader.parseSymbolTable(); 
    }
}
