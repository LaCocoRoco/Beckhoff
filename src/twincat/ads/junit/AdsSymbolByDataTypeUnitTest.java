package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.container.AdsSymbol;

public class AdsSymbolByDataTypeUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
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
            
            AdsDataTypeInfo dataTypeInfo = adsClient.readDataTypeInfoByDataTypeName(dataTypeName);
            List<AdsDataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            List<AdsSymbol> symbolList = dataTypeInfo.getSymbolList(dataTypeInfoList);
            
            for (AdsSymbol symbolInfo : symbolList) {
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
