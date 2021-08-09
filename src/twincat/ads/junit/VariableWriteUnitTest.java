package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.wrapper.Variable;

public class VariableWriteUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String symbolName = ".junit_int16";
    private final int value = 1000;
    
    @Before
    public void start() throws AdsException {
        adsClient.open();
        adsClient.setAmsNetId(AmsNetId.LOCAL);
        adsClient.setAmsPort(AmsPort.TC2PLC1);
    }

    @Test
    public void test() {
        try {
            Variable variable = adsClient.getVariableBySymbolName(symbolName);
            variable.write(value);
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }  
    }

    @After
    public void stop() throws AdsException {
        adsClient.close();
    }
}
