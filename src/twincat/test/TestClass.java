package twincat.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class TestClass {

    public static void main(String[] args) {
        new TestClass();
    }

    public TestClass() {
        doSomething();
    }
    
    private static final int MAX_AXIS_VALUE_LENGTH = 5;
    
    private void doSomething() {
        double axisValue = 0134341431.41312314;
        
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String axisValueText = decimalFormat.format(axisValue);
        
        if (axisValueText.length() > MAX_AXIS_VALUE_LENGTH) {
            decimalFormat = new DecimalFormat("0");
            axisValueText = decimalFormat.format(axisValue);

            if (axisValueText.length() > MAX_AXIS_VALUE_LENGTH)  {
                int scientificLength = MAX_AXIS_VALUE_LENGTH - 2;
                int scientificOffset = axisValueText.length() - scientificLength;
                axisValueText = axisValueText.substring(0, scientificLength); 
                axisValueText = axisValueText.concat("E").concat(String.valueOf(scientificOffset));  
            }
        }
        
        System.out.println("A: " + axisValueText);
    }
    
    @SuppressWarnings("unused")
    private void doSomethingOther() {
        long value = 1000000000000L;
       
        BigInteger bi = BigInteger.valueOf(value);
        BigDecimal bd = new BigDecimal(bi);
        String test = bd.toString();
        
        DecimalFormat decimalFormat = new DecimalFormat("0000.0");
        String axisValueText = decimalFormat.format(value);
        
        System.out.println(test);
    }   
}
