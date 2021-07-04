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
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

	@Before
	public void startAds() {
		ads.open();
	}
	
	@Test
	public void adsAmsPortUnitTest() {	    
        for (AmsPort amsPort : AmsPort.values()) {
    		try {
    	        ads.setAmsNetId(AmsNetId.LOCAL);
    			ads.setAmsPort(amsPort);
    			ads.readDeviceInfo();
    			logger.info("OK : " + amsPort);
    		} catch (AdsException e) {
    			
    		}
        }
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
