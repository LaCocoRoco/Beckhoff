package twincat.ads.junit;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;

public class AdsSymbolNameListUnitTest {
	Ads ads = new Ads();

	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsSymbolNameListUnitTest() {
		try {
			// get symbol name list
			List<String> symbolNameList = ads.readVariableSymbolNameList();
			
			// print symbol name
			for (String symbolName : symbolNameList) {
				System.out.println(symbolName);
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
