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

public class AmsPortUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

	@Before
	public void startAdsClient() {
		adsClient.open();
	}
	
	@Test
	public void amsPortUnitTest() {	    
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
	public void stopAdsClient() throws AdsException {
		adsClient.close();
	}
}
