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

public class AdsVariableReadUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String symbolName = ".JUNIT_ARRAY_COMPLEX[1,5].INTERNAL_PRIMITIVE";
    
    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsSymbolInfoUnitTest() {
        try {       
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            Variable variable;
            variable = adsClient.getVariableBySymbolName(symbolName);
            variable.read();
            variable.close();
            
            logger.info(variable.toString());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }   
    }
    
    @After
    public void stopAds() throws AdsException {
        adsClient.close();
    }  
}
