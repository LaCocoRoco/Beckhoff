package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsSymbolDataTypeInfo;
import twincat.ads.AmsNetId;
import twincat.ads.enums.AmsPort;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;

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
            
            AdsSymbolDataTypeInfo dataTypeInfo = adsClient.readDataTypeInfoByDataTypeName(dataTypeName);
            
            List<AdsSymbolDataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            List<AdsSymbol> dataTypeNodeList = dataTypeInfo.getDataTypeSymbolList(dataTypeInfoList);

            for (AdsSymbol node : dataTypeNodeList) {
                logger.info("Type: " + node.getDataType() + "\tName: " + node.getName());        
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
