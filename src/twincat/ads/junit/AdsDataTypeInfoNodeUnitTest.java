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
import twincat.ads.AdsSymbol;

public class AdsDataTypeInfoNodeUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsDataTypeInfoTableUnitTest() {
        try {
            // data type info from data type
            AdsSymbolDataTypeInfo dataTypeInfo = ads.readDataTypeInfoByDataTypeName("fb_getTime_stiwa");
            
            // read data type info list
            List<AdsSymbolDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();
            
            // get data type node list
            List<AdsSymbol> dataTypeNodeList = dataTypeInfo.getDataTypeSymbolList(dataTypeInfoList);
            
            // print node data
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
