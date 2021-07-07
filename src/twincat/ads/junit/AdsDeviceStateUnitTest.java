package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsStatus;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;
import twincat.ads.container.AdsDeviceState;

public class AdsDeviceStateUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
	
	@Before
	public void startAds() {
		adsClient.open();
	}

	@Test
	public void adsDeviceStateUnitTest() {
		try {
	        adsClient.setAmsNetId(AmsNetId.LOCAL);
	        adsClient.setAmsPort(AmsPort.TC2PLC1);
		    
			AdsDeviceState deviceState = new AdsDeviceState();
			
			deviceState = adsClient.readDeviceState();
			logger.info("AdsState   : " + deviceState.getAdsState());
			logger.info("DeviceState: " + deviceState.getDevState());
			
			switch(deviceState.getAdsState()) {
				case STOP:
					deviceState.setAdsState(AdsStatus.RUN);
					break;
					
				case RUN:
					deviceState.setAdsState(AdsStatus.STOP);
					break;
					
				default:
				    deviceState.setAdsState(AdsStatus.INVALID);
				    break;  
			}

			adsClient.writeDeviceState(deviceState, null);
			logger.info("AdsState   : " + deviceState.getAdsState());
			logger.info("DeviceState: " + deviceState.getDevState());		
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}
	}

	@After
	public void stopAds() throws AdsException {
		adsClient.close();
	}
}
