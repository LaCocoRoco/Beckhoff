package twincat.ads.junit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsUploadInfo;
import twincat.ads.constants.AdsIndexGroup;

public class AdsExportUnitTest {
    Ads ads = new Ads();
    Logger logger = TwincatLogger.getSignedLogger();
    
    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsExportUnitTest() {
        try {
            String uploadDir = System.getProperty("user.dir") + "/exp/";
            AdsUploadInfo uploadInfo = ads.readUploadInfo();

            byte[] readBufferUpload = new byte[uploadInfo.getSymbolLength()];
            ads.read(AdsIndexGroup.SYMBOL_UPLOAD.value, 0, readBufferUpload);
            Files.write(Paths.get(uploadDir + "BUFFER_UPLOAD.txt"), readBufferUpload);
            
            byte[] readBufferDataTypeUpload = new byte[uploadInfo.getDataTypeLength()];
            ads.read(AdsIndexGroup.SYMBOL_DATA_TYPE_UPLOAD.value, 0, readBufferDataTypeUpload);
            Files.write(Paths.get(uploadDir + "BUFFER_DATA_TYPE_UPLOAD.txt"), readBufferDataTypeUpload);

        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
