package twincat.test;

import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.common.TypeInfo;

public class GeneralFunctionTest {
    private final Logger logger = TwincatLogger.getLogger();

    private final String type = "POINTER TO ARRAY [0..C_I_MAXSIPOINTER] OF TLIVEANALYSISINTERFACESTEP";

    public static void main(String[] args) {
        new GeneralFunctionTest();
    }

    public GeneralFunctionTest() {
        test();
    }

    private void test() {
        
        
        
        TypeInfo typeInfo = new TypeInfo();
        
        
        
        typeInfo.parseTypeInfo(type);

        
        
        
        logger.info("Type   : " + typeInfo.getType());
        logger.info("Array  : " + typeInfo.getArray());
        logger.info("Pointer: " + typeInfo.isPointer());
    }
}
