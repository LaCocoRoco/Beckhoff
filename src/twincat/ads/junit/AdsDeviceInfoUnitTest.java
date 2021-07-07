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
import twincat.ads.container.AdsDeviceInfo;

public class AdsDeviceInfoUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
	
	@Before
	public void startAds() {
		adsClient.open();
	}

	@Test
	public void adsDeviceInfoUnitTest() {   
		try {
	        adsClient.setAmsNetId(AmsNetId.LOCAL);
	        adsClient.setAmsPort(AmsPort.TC2PLC1);
		    
			AdsDeviceInfo adsDeviceInfo = adsClient.readDeviceInfo();
			
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
		adsClient.close();
	}
}
