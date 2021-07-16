package twincat.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralFunctionTest {
    public static void main(String[] args) {
        new GeneralFunctionTest();
    }

    public GeneralFunctionTest() {
        testRun();
    }

    private void testRun() {
                                             // hh:mm:ss:ms
        String testStringA = "10:02";        // 00:10:02.000
        String testStringB = "1000:01";      // 62:40:01.000
        String testStringC = "40:130:60";    // 42:10:60.000
        String testStringD = "303:120.0013"; // 05:05:00.003
        String testStringE = "303:120.0033232"; // 05:0.35:00.003
        String testSTringF = "2345adf:3.3´5=)(§%$ß";
        
        String input = testStringE;
        
        // replace unnecessary data
        String value = input.replaceAll("[^0-9|^:|^.]", "");

        int valueLength = value.length();
        int msIndex = value.lastIndexOf(".");
        
        List<String> valueList = new ArrayList<String>();    
        if (msIndex != -1) {
            // milliseconds present in input string
            int msLength = valueLength - msIndex < 4 ? valueLength : msIndex + 4;
            String msValue = value.substring(msIndex + 1, msLength);
            valueList.add(msValue.replaceAll("\\:", "")); 
            // remove milliseconds from input string 
            value = value.substring(0, msIndex);
        } else {
            // no milliseconds  present in string
            valueList.add("000");
        }
        
        // TODO : remove everything > 3
        List<String> test = Arrays.asList(value.replaceAll("\\.", "").split("\\:"));
        
        for (String str : test) {
            System.out.println(str);       
        }
        


        /*
        
        String[] numberArray = testStringA.split("\\:");
        
        
        
        List<String> numberList = Arrays.asList(numberArray);

        for (String str : numberList) {
            str.replaceAll("\\.", "");
        }
        
        String[] numbersB = numbersA[0].split("\\.");
        
        
        
        // double value is present
        if (numbersB.length > 1) {
            numbersB[]
        }
        
        
        
        // System.out.println(array.length);

        for (int i = 0; i < array.length; i++) {
            switch (i) {
                case 0: // 
                    break;
                    
                case 1:
                    break;
                    
                case 2:
                    break;
                    
                case 3:
                    break;
            }
        }

        for (String str : array) {
            System.out.println(testStringD);
        }
        */
    }
}
