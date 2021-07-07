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
import twincat.ads.container.SymbolInfo;

public class SymbolInfoListUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    @Test
    public void symbolInfoListUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            List<SymbolInfo> symbolInfoList = adsClient.readSymbolInfoList();

            for (SymbolInfo symbolInfo : symbolInfoList) {
                logger.info("SymbolName    : " + symbolInfo.getSymbolName());
                logger.info("SymbolDataType: " + symbolInfo.getDataType());
                logger.info("SymbolType    : " + symbolInfo.getType());
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }
}
