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
import twincat.ads.constants.AdsIndexGroup;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsExportUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private final String netId    = AmsNetId.LOCAL;
    private final AmsPort amsPort = AmsPort.SYSTEMSERVICE;
    private final int readSize    = AdsIndexGroup.SYSTEM_ENUM_REMOTE.size;
    private final int indexGroup  = AdsIndexGroup.SYSTEM_ENUM_REMOTE.value;
    private final int indexOffset = 0;

    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsExportUnitTest() {
        try {
            adsClient.setAmsNetId(netId);
            adsClient.setAmsPort(amsPort);
            
            String exportDir = System.getProperty("user.dir") + "/exp/";
            byte[] readBuffer = new byte[readSize];
            adsClient.read(indexGroup, indexOffset, readBuffer);
            Files.write(Paths.get(exportDir + "data.txt"), readBuffer);
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } catch (IOException e) {
            logger.info(Utilities.exceptionToString(e));
        }
    }

    @After
    public void stopAds() throws AdsException {
        adsClient.close();
    }
}
