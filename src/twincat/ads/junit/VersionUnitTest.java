package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;

public class VersionUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
	@Test
	public void test() {	
        logger.info("AdsVersion: " + adsClient.getVersion());
	}
}
