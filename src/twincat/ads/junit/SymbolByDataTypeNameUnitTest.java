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
import twincat.ads.container.DataTypeInfo;
import twincat.ads.container.Symbol;

public class SymbolByDataTypeNameUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
    
    private final String dataTypeName = "junit_st";
    
    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    @Test
    public void symbolByDataTypeUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);
            
            DataTypeInfo dataTypeInfo = adsClient.readDataTypeInfoByDataTypeName(dataTypeName);
            List<DataTypeInfo> dataTypeInfoList = adsClient.readDataTypeInfoList();
            List<Symbol> symbolList = dataTypeInfo.getSymbolList(dataTypeInfoList);
            
            for (Symbol symbol : symbolList) {
                logger.info("Type: " + symbol.getDataType() + "\tName: " + symbol.getSymbolName());        
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
