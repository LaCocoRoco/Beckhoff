package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.container.AdsDeviceInfo;

public class AdsDeviceInfoUnitTest {
	Ads ads = new Ads();
	Logger logger = AdsLogger.getLogger();
	
	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsDeviceInfoUnitTest() {
		try {
			AdsDeviceInfo adsDeviceInfo = ads.readDeviceInfo();
			
			logger.info("DeviceName  : " + adsDeviceInfo.getDeviceName());
			logger.info("MajorVersion: " + adsDeviceInfo.getMajorVersion());
			logger.info("MinorVersion: " + adsDeviceInfo.getMinorVersion());
			logger.info("DeviceInfo  : " + adsDeviceInfo.getBuildVersion());
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
