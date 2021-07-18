package twincat.test;

import java.util.EnumSet;
import java.util.Set;

import twincat.ads.constant.AmsPort;

public class GeneralFunctionTest {
    
    private static final Set<AmsPort> AMS_PORT_LIST = EnumSet.of(AmsPort.NC, AmsPort.NCSAF,
            AmsPort.NCSVB, AmsPort.TC2PLC1, AmsPort.TC2PLC2, AmsPort.TC2PLC3, AmsPort.TC2PLC4,
            AmsPort.TC3PLC1, AmsPort.TC3PLC2, AmsPort.TC3PLC3, AmsPort.TC3PLC4, AmsPort.TC3PLC5);

    public static void main(String[] args) {
        new GeneralFunctionTest();
    }

    public GeneralFunctionTest() {
        testRun();
    }

    private void testRun() {
        for (AmsPort test : AMS_PORT_LIST) {
            System.out.println(test); 
        }
    }
}
