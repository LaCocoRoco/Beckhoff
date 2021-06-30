package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.AdsSymbolInfo;

public class AdsSymbolInfoUnitTest {
	Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();
    
	@Before
	public void startAds() {
		ads.open();
	}

	@Test
	public void adsSymbolInfoUnitTest() {
		try {		
			AdsSymbolInfo symbolInfo = ads.readSymbolInfoBySymbolName(".junit_time");
			
			logger.info("InfoLength : " + symbolInfo.getLength());
			logger.info("IndexGroup : " + symbolInfo.getIndexGroup());
			logger.info("IndexOffset: " + symbolInfo.getIndexOffset());
			logger.info("DataSize   : " + symbolInfo.getDataSize());
			logger.info("DataType   : " + symbolInfo.getDataType());
			logger.info("SymbolFlag : " + symbolInfo.getSymbolFlag());
			logger.info("SymbolName : " + symbolInfo.getSymbolName());
			logger.info("Type       : " + symbolInfo.getType());
			logger.info("Comment    : " + symbolInfo.getComment());
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}	
	}
	
	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
