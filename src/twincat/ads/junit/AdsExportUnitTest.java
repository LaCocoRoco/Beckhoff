package twincat.ads.junit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.AdsUploadInfo;
import twincat.ads.constants.AdsIndexGroup;
import twincat.ads.datatype.STRING;

public class AdsExportUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();
    
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
            
            byte [] writeBufferDataType = STRING.valueToArray("TON");
            byte[] radBufferDataType = new byte[AdsIndexGroup.DATA_TYPE_INFO_BY_NAME_EX.size];
            ads.readWrite(AdsIndexGroup.DATA_TYPE_INFO_BY_NAME_EX.value, 0, radBufferDataType, writeBufferDataType);
            Files.write(Paths.get(uploadDir + "BUFFER_DATA_TYPE.txt"), radBufferDataType);
            
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
