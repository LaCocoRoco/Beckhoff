package twincat.ads.junit;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsPort;

public class AdsReadIdxGrpOffsUnitTest {
    Ads ads = new Ads();
    Logger logger = TwincatLogger.getSignedLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolInfoListUnitTest() {
        try {
            // read idx offs size
            
            ads.setAmsPort(AmsPort.TC2PLC1);
            byte[] readBuffer = new byte[0xFFF];
            ads.read(803, 0, readBuffer);
            
            String result = new String(readBuffer, "UTF-8");
            logger.info(result);
            
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
