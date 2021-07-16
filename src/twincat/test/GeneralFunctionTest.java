package twincat.test;

import twincat.scope.Scope;

public class GeneralFunctionTest {
    public static void main(String[] args) {
        new GeneralFunctionTest();
    }

    public GeneralFunctionTest() {
        testRun();
    }

    private void testRun() {
        String timeString = Scope.timeFormaterToString(4325252);
        long timeLong = Scope.timeFormaterToLong(timeString);
           
        System.out.println(timeString);
        System.out.println(timeLong);
    }
}
