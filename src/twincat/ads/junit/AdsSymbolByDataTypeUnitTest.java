package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsSymbolDataTypeInfo;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;

public class AdsSymbolByDataTypeUnitTest {
    private final AdsClient ads = new AdsClient();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private final String dataTypeName = "junit_st";
    
    @Before
    public void startAds() {
        ads.open();
        ads.setAmsNetId(AmsNetId.LOCAL);
        ads.setAmsPort(AmsPort.TC2PLC1);
    }

    @Test
    public void adsDataTypeInfoTableUnitTest() {
        try {
            AdsSymbolDataTypeInfo dataTypeInfo = ads.readDataTypeInfoByDataTypeName(dataTypeName);
            
            List<AdsSymbolDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();
            List<AdsSymbol> dataTypeNodeList = dataTypeInfo.getDataTypeSymbolList(dataTypeInfoList);

            for (AdsSymbol node : dataTypeNodeList) {
                logger.info("Type: " + node.getType() + "\tName: " + node.getName());        
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
