package twincat.test;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twincat.TwincatLogger;

public class GeneralFunctionTest {
    private final Logger logger = TwincatLogger.getLogger();
    
    public static void main(String[] args) {
        new GeneralFunctionTest();
    }
    
    public GeneralFunctionTest() {
        generalFunctionTest();
    }

    private void generalFunctionTest() {
        
        String input = ".*(array|string).*";
        
        String symbolName = ".JUNIT_ARRAY_COMPLEX[1,5].INTERNAL_STRUCTURE_ARRAY[3].ST_VALUE.Q";
        
        Pattern pattern = Pattern.compile(input, Pattern.CASE_INSENSITIVE);
        
        Matcher matcher = pattern.matcher(symbolName);
        
        if (matcher.matches()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                logger.info("" + matcher.group(i)); 
            }
        } else {
            logger.info("false");
        }  
    }
}
