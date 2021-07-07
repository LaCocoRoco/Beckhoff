package twincat.ads.junit;

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

public class SymbolInfoBySymbolNameUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    private final String symbolName = ".junit_time";

    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    @Test
    public void symbolInfoBySymbolNameUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);

            SymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolName);

            logger.info("InfoLength : " + symbolInfo.getLength());
            logger.info("IndexGroup : " + symbolInfo.getIndexGroup());
            logger.info("IndexOffset: " + symbolInfo.getIndexOffset());
            logger.info("DataSize   : " + symbolInfo.getDataSize());
            logger.info("DataType   : " + symbolInfo.getDataType());
            logger.info("SymbolFlag : " + symbolInfo.getSymbolFlag());
            logger.info("SymbolName : " + symbolInfo.getSymbolName());
            logger.info("Type       : " + symbolInfo.getType());
            logger.info("Comment    : " + symbolInfo.getComment());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }
}
