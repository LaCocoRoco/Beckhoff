package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.DataTypeInfo;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class DataTypeInfoListUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void start() {
        adsClient.open();
    }

    @Test
    public void test() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            List<DataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            
            for (DataTypeInfo dataTypeInfo : dataTypeInfoList) {
                logger.info("DataTypeName    : " + dataTypeInfo.getDataTypeName());
                logger.info("DataTypeDataType: " + dataTypeInfo.getDataType());
                logger.info("DataTypeType    : " + dataTypeInfo.getType());
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stop() throws AdsException {
        adsClient.close();
    }
}
