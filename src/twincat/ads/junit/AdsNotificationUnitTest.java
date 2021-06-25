package twincat.ads.junit;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsCallback;
import twincat.ads.AdsException;
import twincat.ads.AdsNotification;
import twincat.ads.constants.AdsIdxGrp;
import twincat.ads.datatype.LREAL;
import twincat.ads.enums.AdsTransMode;
import twincat.ads.enums.DataType;

public class AdsNotificationUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsNotificationUnitTest() {
		try {
			// variable name
			String symbolName = "MAIN.lr_channel_1";
			
			// variable data size
			byte[] data = new byte[DataType.REAL64.size];
			
			// get handle from control
			int symbolHandle = ads.readHandleOfSymbolName(symbolName);
			
			// notification settings
			AdsNotification adsNotification = new AdsNotification();
			adsNotification.setDataLength(data.length);
			adsNotification.setTransmissionMode(AdsTransMode.SERVERCYCLE);
			adsNotification.setCycleTime(500 * AdsNotification.TIME_RATIO_NS_TO_MS);

			// add notification to control
			ads.addDeviceNotification(AdsIdxGrp.SYM_VALBYHND, symbolHandle, adsNotification, new AdsCallback() {
				@Override
				public void update(long notification, long timeStampe, byte[] data) {
					double value = LREAL.arrayToValue(data);
					System.out.println("Valu: " + value);
					System.out.println("Time: " + timeStampe);
				}
			});

			//wait for input
			Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            scanner.close();
            
		} catch (AdsException e) {
			System.out.println(e.getAdsErrorMessage());
		}
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
