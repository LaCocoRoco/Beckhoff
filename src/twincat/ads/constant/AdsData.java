package twincat.ads.constant;

import java.util.EnumSet;
import java.util.Set;

public class AdsData {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final Set<AmsPort> AMS_PORT_SYMBOL_LIST = EnumSet.of(AmsPort.NC, AmsPort.NCSAF,
        AmsPort.NCSVB, AmsPort.TC2PLC1, AmsPort.TC2PLC2, AmsPort.TC2PLC3, AmsPort.TC2PLC4,
        AmsPort.TC3PLC1, AmsPort.TC3PLC2, AmsPort.TC3PLC3, AmsPort.TC3PLC4, AmsPort.TC3PLC5);
}
