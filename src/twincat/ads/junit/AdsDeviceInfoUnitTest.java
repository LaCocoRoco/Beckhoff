package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsDeviceInfo;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsDeviceInfoUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
	
	@Before
	public void startAds() {
		ads.open();
        ads.setAmsNetId(AmsNetId.LOCAL);
        ads.setAmsPort(AmsPort.TC2PLC1);
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
