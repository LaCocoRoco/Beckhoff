package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.container.AdsSymbolInfo;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsSymbolByDataTypeUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private final String dataTypeName = "junit_st";
    
    @Before
    public void startAds() {
        adsClient.open();
    }

    @Test
    public void adsDataTypeInfoTableUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            AdsDataTypeInfo symbolDataTypeInfo = adsClient.readDataTypeInfoByDataTypeName(dataTypeName);
            List<AdsDataTypeInfo> symbolDataTypeInfoList = adsClient.readDataTypeInfoList();
            List<AdsSymbolInfo> symbolInfoList = symbolDataTypeInfo.getSymbolInfoList(symbolDataTypeInfoList);
            
            for (AdsSymbolInfo symbolInfo : symbolInfoList) {
                logger.info("Type: " + symbolInfo.getDataType() + "\tName: " + symbolInfo.getSymbolName());        
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
