package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsDeviceState;
import twincat.ads.AdsException;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AdsStatus;
import twincat.ads.enums.AmsPort;

public class AdsDeviceStateUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
	
	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsDeviceStateUnitTest() {
		try {
	        ads.setAmsNetId(AmsNetId.LOCAL);
	        ads.setAmsPort(AmsPort.TC2PLC1);
		    
			AdsDeviceState adsDeviceState = new AdsDeviceState();
			
			adsDeviceState = ads.readDeviceState();
			logger.info("AdsState   : " + adsDeviceState.getAdsState());
			logger.info("DeviceState: " + adsDeviceState.getDeviceState());
			
			switch(adsDeviceState.getAdsState()) {
				case STOP:
					adsDeviceState.setAdsState(AdsStatus.RUN);
					break;
					
				case RUN:
					adsDeviceState.setAdsState(AdsStatus.STOP);
					break;
					
				default:
				    adsDeviceState.setAdsState(AdsStatus.INVALID);
				    break;  
			}

			ads.writeDeviceState(adsDeviceState, null);
			logger.info("AdsState   : " + adsDeviceState.getAdsState());
			logger.info("DeviceState: " + adsDeviceState.getDeviceState());		
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
