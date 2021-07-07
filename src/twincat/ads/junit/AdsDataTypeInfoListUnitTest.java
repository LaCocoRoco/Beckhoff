package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;
import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.AdsException;

public class AdsDataTypeInfoListUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();

    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsDataTypeInfoListUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            List<AdsDataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            
            for (AdsDataTypeInfo dataTypeInfo : dataTypeInfoList) {
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
        adsClient.close();
    }
}
