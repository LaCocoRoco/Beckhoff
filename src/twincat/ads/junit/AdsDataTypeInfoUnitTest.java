package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsDataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;

public class AdsDataTypeInfoUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsDataTypeInfoUnitTest() {
        try {       
            String dataTypeName = "FB_JUNIT";

            AdsDataTypeInfo dataTypeInfo = ads.readDataTypeInfoByDataTypeName(dataTypeName);
            
            logger.info("Length        : " + dataTypeInfo.getLength());
            logger.info("Version       : " + dataTypeInfo.getVersion());
            logger.info("HashValue     : " + dataTypeInfo.getHashValue());
            logger.info("TypeHashValue : " + dataTypeInfo.getTypeHashValue());
            logger.info("Size          : " + dataTypeInfo.getSize());
            logger.info("Offset        : " + dataTypeInfo.getOffset());
            logger.info("DataType      : " + dataTypeInfo.getDataType());
            logger.info("DataTypeFlag  : " + dataTypeInfo.getDataTypeFlag());
            logger.info("DataTypeName  : " + dataTypeInfo.getDataTypeName());
            logger.info("Type          : " + dataTypeInfo.getType());
            logger.info("Comment       : " + dataTypeInfo.getComment());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }   
    }
    
    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
