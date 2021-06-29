package twincat.ads.junit;

import java.util.List;
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

public class AdsDataTypeInfoListUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
<<<<<<< HEAD
    public void adsDataTypeInfoListUnitTest() {
=======
    public void adsDataTypeInfoTableUnitTest() {
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
        try {
            List<AdsDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();
            
            for (AdsDataTypeInfo dataTypeInfo : dataTypeInfoList) {
                logger.info("DataTypeName    : " + dataTypeInfo.getDataTypeName());
                logger.info("DataTypeDataType: " + dataTypeInfo.getDataType());
                logger.info("DataTypeType    : " + dataTypeInfo.getType());
<<<<<<< HEAD
                logger.info("SubDataTypeSize : " + dataTypeInfo.getSubDataTypeInfoList().size());
=======
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
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
