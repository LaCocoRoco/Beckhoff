package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.Variable;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

/** 
 * VAR_GLOBAL
 *     junit_bit       :BOOL;
 *     junit_int8      :SINT;
 *     junit_int16     :INT;
 *     junit_int32     :DINT;
 *     junit_uint8     :BYTE;
 *     junit_uint16    :WORD;
 *     junit_uint32    :DWORD;
 *     junit_real32    :REAL;
 *     junit_real64    :LREAL;
 *     junit_time      :TIME;
 *     junit_string    :STRING(100);
 * END_VAR
 */

public class VariableReadUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
 
    @Before
    public void start() throws AdsException {
        adsClient.open();
        adsClient.setAmsNetId(AmsNetId.LOCAL);
        adsClient.setAmsPort(AmsPort.TC2PLC1);
    }

    @Test
    public void test() {
        try {
            Variable variable = adsClient.getVariableBySymbolName(".junit_int16");
            variable.read();
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }  
    }

    @After
    public void stop() throws AdsException {
        adsClient.close();
    }
}
