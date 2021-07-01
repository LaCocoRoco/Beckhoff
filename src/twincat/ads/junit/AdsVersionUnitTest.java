package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsException;

public class AdsVersionUnitTest {
	Ads ads = new Ads();
	Logger logger = TwincatLogger.getSignedLogger();
    
	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsSymbolInfoUnitTest() {	
		logger.info("AdsVersion: " + ads.getVersion());
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
