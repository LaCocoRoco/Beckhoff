package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsLogger;
import twincat.ads.container.AdsDataTypeInfo;
import twincat.ads.container.AdsSymbol;
import twincat.ads.container.AdsSymbolInfo;

public class AdsSymbolInfoNodeUnitTest {
    Ads ads = new Ads();
    Logger logger = AdsLogger.getLogger();

    @Before
    public void startAds() {
        ads.open();
    }

    @Test
    public void adsSymbolInfoNodeUnitTest() {
        try {
            // symbol info from symbol name
            AdsSymbolInfo symbolInfo = ads.readSymbolInfoBySymbolName(".ACTUALVELOCITY ");

            // read data type info list
            List<AdsDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();

            // get symbol node list
            List<AdsSymbol> symbolNodeList = symbolInfo.getSymbolList(dataTypeInfoList);

            // print node data
            for (AdsSymbol node : symbolNodeList) {
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
