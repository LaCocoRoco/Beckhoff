package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsSymbolDataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;

public class AdsDataTypeInfoListUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsDataTypeInfoListUnitTest() {
        try {
            List<AdsSymbolDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();
            
            for (AdsSymbolDataTypeInfo dataTypeInfo : dataTypeInfoList) {
                logger.info("DataTypeName    : " + dataTypeInfo.getDataTypeName());
                logger.info("DataTypeDataType: " + dataTypeInfo.getDataType());
                logger.info("DataTypeType    : " + dataTypeInfo.getType());
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAds() throws AdsException {
        ads.close();
    }
}
