package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.container.DataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class DataTypeInfoUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String dataTypeName = "junit_st";
    
    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    @Test
    public void dataTypeInfoUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            DataTypeInfo dataTypeInfo = adsClient.readDataTypeInfoByDataTypeName(dataTypeName);
            
            printDataTypeInfo(dataTypeInfo, true);
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }   
    }
    
    @After
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }
    
    private void printDataTypeInfo(DataTypeInfo dataTypeInfo, boolean printSubData) {
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
            for (DataTypeInfo subDataTypeInfo : dataTypeInfo.getSubSymbolDataTypeInfoList()) {
                printDataTypeInfo(subDataTypeInfo, printSubData);
            }
        }
    }
}
