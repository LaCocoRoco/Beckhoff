package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
<<<<<<< HEAD
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.container.AdsDataTypeInfo;
=======
import twincat.ads.AdsDataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4

public class AdsDataTypeInfoUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsDataTypeInfoUnitTest() {
<<<<<<< HEAD
        try {
            AdsDataTypeInfo dataTypeInfo = ads.readDataTypeInfoByDataTypeName("JUNIT_REAL64");  // fb_junit
            
            printAdsDataTypeInfo(dataTypeInfo, true);
            
=======
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
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }   
    }
    
    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
<<<<<<< HEAD
    
    private void printAdsDataTypeInfo(AdsDataTypeInfo dataTypeInfo, boolean printSubData) {
        logger.info("Length        : " + dataTypeInfo.getLength());
        logger.info("Version       : " + dataTypeInfo.getVersion());
        logger.info("HashValue     : " + dataTypeInfo.getHashValue());
        logger.info("TypeHashValue : " + dataTypeInfo.getTypeHashValue());
        logger.info("DataSize      : " + dataTypeInfo.getDataSize());
        logger.info("Offset        : " + dataTypeInfo.getOffset());
        logger.info("DataType      : " + dataTypeInfo.getDataType());
        logger.info("DataTypeFlag  : " + dataTypeInfo.getDataTypeFlag());
        logger.info("DataTypeName  : " + dataTypeInfo.getDataTypeName());
        logger.info("TypeName      : " + dataTypeInfo.getType());
        logger.info("Comment       : " + dataTypeInfo.getComment());
        logger.info("SubDataSize   : " + dataTypeInfo.getSubDataTypeInfoList().size());
        
        if (printSubData) {
            logger.info("##############:");
            for (AdsDataTypeInfo subDataTypeInfo : dataTypeInfo.getSubDataTypeInfoList()) {
                printAdsDataTypeInfo(subDataTypeInfo, printSubData);
            }
        }
    }
=======
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
}
