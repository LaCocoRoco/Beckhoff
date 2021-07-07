package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;
import twincat.ads.container.AdsTypeInfo;

public class AdsTypInfoUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private final String type = "POINTER TO ARRAY [0..C_I_MAXSIPOINTER] OF TLIVEANALYSISINTERFACESTEP";
    
    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsDeviceInfoUnitTest() {   
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            AdsTypeInfo typeInfo = new AdsTypeInfo(type);
  
            logger.info("Type   : " + typeInfo.getType());
            logger.info("Array  : " + typeInfo.getArray());
            logger.info("Pointer: " + typeInfo.isPointer());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }
    
    @After
    public void stopAds() throws AdsException {
        adsClient.close();
    }
}
