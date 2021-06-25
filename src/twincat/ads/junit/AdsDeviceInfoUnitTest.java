package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twincat.ads.Ads;
import twincat.ads.AdsDeviceInfo;
import twincat.ads.AdsException;

public class AdsDeviceInfoUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsDeviceInfoUnitTest() {
		try {
			AdsDeviceInfo adsDeviceInfo = ads.readDeviceInfo();
			
			System.out.println(adsDeviceInfo.getDeviceName());
			System.out.println(adsDeviceInfo.getMajorVersion());
			System.out.println(adsDeviceInfo.getMinorVersion());
			System.out.println(adsDeviceInfo.getBuildVersion());
		} catch (AdsException e) {
			System.out.println(e.getAdsErrorMessage());
		}
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
