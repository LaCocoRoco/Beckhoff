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
import twincat.ads.constant.IndexGroup;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class ExportUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String netId    = AmsNetId.LOCAL;
    private final AmsPort amsPort = AmsPort.SYSTEMSERVICE;
    private final int readSize    = IndexGroup.SYSTEM_ENUM_REMOTE.size;
    private final int indexGroup  = IndexGroup.SYSTEM_ENUM_REMOTE.value;
    private final int indexOffset = 0;

    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    @Test
    public void exportUnitTest() {
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
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }
}
