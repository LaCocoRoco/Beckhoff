package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.wrapper.Variable;

public class AdsVariableReadUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();
    
    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolInfoUnitTest() {
        try {       
            Variable variable;
            variable = ads.getVariableBySymbolName(".JUINIT_P_BIG_ARRAY[0].ST_VALUE.I_VALUE");
            variable.read();
            variable.close();
            
            logger.info(variable.toString());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }   
    }
    
    @After
    public void stopAds() throws AdsException {
        ads.close();
    }  
}
