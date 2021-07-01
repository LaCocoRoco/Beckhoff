package twincat.ads.junit;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.Ads;
import twincat.ads.AdsSymbolDataTypeInfo;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolInfo;

public class AdsSymbolInfoNodeUnitTest {
    Ads ads = new Ads();
    Logger logger = TwincatLogger.getSignedLogger();

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
            List<AdsSymbolDataTypeInfo> dataTypeInfoList = ads.readDataTypeInfoList();

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
