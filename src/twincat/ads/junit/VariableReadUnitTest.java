package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class VariableReadUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String symbolName = "ACHSEN.DUMMY.POINT SPACE.ACTACC";
    
    @Before
    public void start() throws AdsException {
        adsClient.open();
        adsClient.setAmsNetId(AmsNetId.LOCAL);
        adsClient.setAmsPort(AmsPort.NCSAF);
    }

    @Test
    public void test() {
        try {
            Variable variable = adsClient.getVariableBySymbolName(symbolName);
            variable.read();
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }  
    }

    @After
    public void stop() throws AdsException {
        adsClient.close();
    }
}
