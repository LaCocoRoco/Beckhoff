package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbolInfo;

public class AdsSymbolInfoUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsSymbolInfoUnitTest() {

		try {		
			// variable name
			String symbolName = "MAIN.FB_CHANNEL_1";

			// get symbol info
			AdsSymbolInfo symbolInfo = ads.readSymbolInfoBySymbolName(symbolName);
			
			// print symbol info
			System.out.println(symbolInfo.getInfoLength());
			System.out.println(symbolInfo.getIndexGroup());
			System.out.println(symbolInfo.getIndexOffset());
			System.out.println(symbolInfo.getDataSize());
			System.out.println(symbolInfo.getDataType());
			System.out.println(symbolInfo.getFlags());
			System.out.println(symbolInfo.getName());
			System.out.println(symbolInfo.getType());
			System.out.println(symbolInfo.getComment());
			
		} catch (AdsException e) {
			System.out.println(e.getAdsErrorMessage());
		}	
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
