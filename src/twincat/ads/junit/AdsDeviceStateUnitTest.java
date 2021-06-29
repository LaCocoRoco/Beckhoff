package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.constants.AdsStatus;
<<<<<<< HEAD
import twincat.ads.container.AdsDeviceState;
=======
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4

public class AdsDeviceStateUnitTest {
	Ads ads = new Ads();
	Logger logger = AdsLogger.getLogger();
	
	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsDeviceStateUnitTest() {
		try {
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
