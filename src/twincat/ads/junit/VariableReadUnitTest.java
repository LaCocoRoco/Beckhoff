package twincat.ads.junit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.datatype.UDINT;
import twincat.ads.datatype.UINT8;

public class VariableReadUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();

    private final String symbolName = ".junit_time";

    @Before
    public void startAdsClient() {
        adsClient.open();
    }

    private static final String PATTERN_TIME = "T#:";
    private static final String PATTERN_MINUTE = "m";
    private static final String PATTERN_SECOND = "s";
    private static final String PATTERN_MILLISECOND = "ms";
    private static final String PATTERN_DELIMITER = "m|s|ms";

    @Test
    public void variableReadUnitTest() {
        try {
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.TC2PLC1);

            int symbolHandle = adsClient.readHandleOfSymbolName(symbolName);
            byte[] readBuffer = new byte[4];
            adsClient.readBySymbolHandle(symbolHandle, readBuffer);
            long udint = UDINT.arrayToValue(readBuffer);

            
            // build time string
            long milliseconds = udint % 1000;
            long seconds = udint / 1000 % 60;
            long minutes = udint / 60000;

            StringBuilder stringBuilder = new StringBuilder();
            
            if  (minutes != 0) {
                stringBuilder.append(PATTERN_TIME);
                stringBuilder.append(minutes);
                stringBuilder.append(PATTERN_MINUTE);
                stringBuilder.append(seconds);
                stringBuilder.append(PATTERN_SECOND);
                stringBuilder.append(milliseconds);
                stringBuilder.append(PATTERN_MILLISECOND);               
            } else if (seconds != 0) {
                stringBuilder.append(PATTERN_TIME);
                stringBuilder.append(seconds);
                stringBuilder.append(PATTERN_SECOND);
                stringBuilder.append(milliseconds);
                stringBuilder.append(PATTERN_MILLISECOND);            
            } else if (milliseconds != 0) {
                stringBuilder.append(PATTERN_TIME);
                stringBuilder.append(milliseconds);
                stringBuilder.append(PATTERN_MILLISECOND);            
            }
            
            String timeFrom = stringBuilder.toString();

            
            // build time value
            long timeTo = 0;
            String[] timeArray = timeFrom.replace(PATTERN_TIME, "").split(PATTERN_DELIMITER);
            List<String> timeList = Arrays.asList(timeArray);
            Collections.reverse(timeList);

            try {
                for (int i = 0; i < timeList.size(); i++) {
                    switch (i) {
                        case 0: // milliseconds
                            timeTo += Long.valueOf(timeList.get(i));
                            break;
                        case 1: // seconds
                            timeTo += Long.valueOf(timeList.get(i)) * 1000;
                            break;
                        case 2: // minutes
                            timeTo += Long.valueOf(timeList.get(i)) * 60000;
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                timeTo = 0;
            }

            logger.info("FROM: " + timeFrom);
            logger.info("TO  : " + timeTo);

            /*
             * Variable variable; variable = adsClient.getVariableBySymbolName(symbolName);
             * variable.read(); variable.close();
             * 
             * logger.info(variable.toString());
             */
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    @After
    public void stopAdsClient() throws AdsException {
        adsClient.close();
    }
}
