package twincat.ads.junit;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbolInfo;

public class AdsSymbolInfoListUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsSymbolTableUnitTest() {
		try {
			// get symbol info list
			List<AdsSymbolInfo> symbolInfoList = ads.readSymbolInfoTable();
			
			// print symbol info name
			for (AdsSymbolInfo symbolInfo : symbolInfoList) {
				System.out.println(symbolInfo.getName());			
			}
		} catch (AdsException e) {
			System.out.println(e.getAdsErrorMessage());
		}
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
