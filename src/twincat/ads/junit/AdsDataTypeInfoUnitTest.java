package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsSymbolDataTypeInfo;
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
            AdsSymbolDataTypeInfo dataTypeInfo = ads.readDataTypeInfoByDataTypeName("JUNIT_REAL64");  // fb_junit
            
            printAdsDataTypeInfo(dataTypeInfo, true);
            
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }   
    }
    
    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
    
    private void printAdsDataTypeInfo(AdsSymbolDataTypeInfo dataTypeInfo, boolean printSubData) {
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
        
        if (printSubData) {
            logger.info("##############:");
            for (AdsSymbolDataTypeInfo subDataTypeInfo : dataTypeInfo.getSubSymbolDataTypeInfoList()) {
                printAdsDataTypeInfo(subDataTypeInfo, printSubData);
            }
        }
    }
}
