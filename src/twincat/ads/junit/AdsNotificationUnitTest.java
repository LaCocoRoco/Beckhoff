package twincat.ads.junit;

import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsCallback;
import twincat.ads.AdsException;
import twincat.ads.AdsNotification;
import twincat.ads.constants.AdsDataType;
import twincat.ads.constants.AdsIndexGroup;
import twincat.ads.constants.AdsTransmitMode;
import twincat.ads.datatype.LREAL;

public class AdsNotificationUnitTest {
	Ads ads = new Ads();
	Logger logger = TwincatLogger.getSignedLogger();
    
	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsNotificationUnitTest() {
		try {
			// variable data size
			byte[] data = new byte[AdsDataType.REAL64.size];
			
			// get handle from control
			int symbolHandle = ads.readHandleOfSymbolName("MAIN.lr_channel_1");
			
			// notification settings
			AdsNotification adsNotification = new AdsNotification();
			adsNotification.setDataLength(data.length);
			adsNotification.setTransmissionMode(AdsTransmitMode.SERVER_CYCLE);
			adsNotification.setCycleTime(500 * AdsNotification.TIME_RATIO_NS_TO_MS);

			// add notification to control
			ads.addDeviceNotification(AdsIndexGroup.SYMBOL_VALUE_BY_HANDLE.value, symbolHandle, adsNotification, new AdsCallback() {
				@Override
				public void update(long notification, long timeStampe, byte[] data) {
					double value = LREAL.arrayToValue(data);
					logger.info("Value: " + value);
					logger.info("Time : " + timeStampe);
				}
			});

			//wait for input
			Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            scanner.close();
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
