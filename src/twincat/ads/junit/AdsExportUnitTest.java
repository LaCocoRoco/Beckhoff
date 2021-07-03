package twincat.ads.junit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.Utilities;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsExportUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private final String netId      = AmsNetId.LOCAL;
    private final AmsPort amsPort   = AmsPort.SYSTEMSERVICE;
    private final int readSize      = 256;
    private final int indexGroup    = 702;
    private final int indexOffset   = 0;

    @Before
    public void startAds() {
        ads.open();
        ads.setAmsNetId(netId);
        ads.setAmsPort(amsPort);
    }

    @Test
    public void adsExportUnitTest() {
        try {
            String uploadDir = System.getProperty("user.dir") + "/exp/";
            byte[] readBufferUpload = new byte[readSize];
            ads.read(indexGroup, indexOffset, readBufferUpload);
            Files.write(Paths.get(uploadDir + "EXPORT.txt"), readBufferUpload);
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } catch (IOException e) {
            logger.info(Utilities.exceptionToString(e));
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
