package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.container.TypeInfo;

public class TypInfoUnitTest {
    private final Logger logger = TwincatLogger.getLogger();

    private final String type = "POINTER TO ARRAY [0..C_I_MAXSIPOINTER] OF TLIVEANALYSISINTERFACESTEP";

    @Test
    public void typInfoUnitTest() {
        TypeInfo typeInfo = new TypeInfo(type);

        logger.info("Type   : " + typeInfo.getType());
        logger.info("Array  : " + typeInfo.getArray());
        logger.info("Pointer: " + typeInfo.isPointer());
    }

}
