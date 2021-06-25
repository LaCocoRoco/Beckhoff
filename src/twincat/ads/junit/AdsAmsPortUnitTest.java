package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.AmsPort;

public class AdsAmsPortUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}
	
	@Test
	public void adsAmsPortUnitTest() {
        for (AmsPort amsPort : AmsPort.values()) {
    		try {
    			ads.setAmsPort(amsPort);
    			ads.readDeviceInfo();
    			System.out.println("OK : " + amsPort);
    		} catch (AdsException e) {
    			System.out.println("NOK: " + amsPort);
    		}
        }
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
