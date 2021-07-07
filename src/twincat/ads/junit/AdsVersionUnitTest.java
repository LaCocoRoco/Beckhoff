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

public class AdsVersionUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
	@Before
	public void startAds() {
		adsClient.open();

	}

	@Test
	public void adsSymbolInfoUnitTest() {	
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            logger.info("AdsVersion: " + adsClient.getVersion());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
	}
	
	@After
	public void stopAds() throws AdsException {
		adsClient.close();
	}
}
