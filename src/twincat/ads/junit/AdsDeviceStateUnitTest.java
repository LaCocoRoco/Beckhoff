package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsDeviceState;
import twincat.ads.AdsException;
import twincat.ads.enums.AdsStatus;

public class AdsDeviceStateUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsDeviceStateUnitTest() {
		try {
			AdsDeviceState adsDeviceState = new AdsDeviceState();
			
			adsDeviceState = ads.readDeviceState();
			
			System.out.println(adsDeviceState.getAdsState());
			System.out.println(adsDeviceState.getDeviceState());
			
			switch(adsDeviceState.getAdsState()) {
				case STOP:
					adsDeviceState.setAdsState(AdsStatus.RUN);
				case RUN:
					adsDeviceState.setAdsState(AdsStatus.STOP);
				default:
					break;
			}

			ads.writeDeviceState(adsDeviceState, null);
			
			System.out.println(adsDeviceState.getAdsState());
			System.out.println(adsDeviceState.getDeviceState());		
		} catch (AdsException e) {
			System.out.println(e.getAdsErrorMessage());
		}
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
