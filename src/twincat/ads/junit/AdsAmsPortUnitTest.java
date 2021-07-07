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

public class AdsAmsPortUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

	@Before
	public void startAds() {
		adsClient.open();
	}
	
	@Test
	public void adsAmsPortUnitTest() {	    
        for (AmsPort amsPort : AmsPort.values()) {
    		try {
    	        adsClient.setAmsNetId(AmsNetId.LOCAL);
    			adsClient.setAmsPort(amsPort);
    			
    			adsClient.readDeviceInfo();
    			logger.info("OK : " + amsPort);
    		} catch (AdsException e) {
    			
    		}
        }
	}

	@After
	public void stopAds() throws AdsException {
		adsClient.close();
	}
}
