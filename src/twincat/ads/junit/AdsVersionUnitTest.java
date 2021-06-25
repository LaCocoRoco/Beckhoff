package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twincat.ads.Ads;
import twincat.ads.AdsException;

public class AdsVersionUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsSymbolInfoUnitTest() {	
		// get ads version
		String adsVersion = ads.getVersion();
		
		System.out.println(adsVersion);
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
