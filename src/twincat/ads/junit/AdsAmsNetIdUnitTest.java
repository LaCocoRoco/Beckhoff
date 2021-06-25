package twincat.ads.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;

public class AdsAmsNetIdUnitTest {
    Ads ads = new Ads();

    @Before
    public void startAds() {
        ads.open();
    }
    
    @Test
    public void adsAmsNetIdUnitTest() {
        try {
            String amsNetId = ads.readAmsNetId();
            System.out.println(amsNetId);
        } catch (AdsException e) {
            System.out.println(e.getAdsErrorMessage());
        }

    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
