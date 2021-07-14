package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.DeviceInfo;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class DeviceInfoUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
	
	@Before
	public void start() {
		adsClient.open();
	}

	@Test
	public void test() {   
		try {
	        adsClient.setAmsNetId(AmsNetId.LOCAL);
	        adsClient.setAmsPort(AmsPort.TC2PLC1);
		    
			DeviceInfo adsDeviceInfo = adsClient.readDeviceInfo();
			
			logger.info("DeviceName  : " + adsDeviceInfo.getDeviceName());
			logger.info("MajorVersion: " + adsDeviceInfo.getMajorVersion());
			logger.info("MinorVersion: " + adsDeviceInfo.getMinorVersion());
			logger.info("DeviceInfo  : " + adsDeviceInfo.getBuildVersion());
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}
	}
	
	@After
	public void stop() throws AdsException {
		adsClient.close();
	}
}
