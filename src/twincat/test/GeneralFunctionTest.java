package twincat.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class GeneralFunctionTest {

    private byte[] buffer = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    public static void main(String[] args) {
        new GeneralFunctionTest();
    }

    public GeneralFunctionTest() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byteBuffer.getInt();

        byte[] test = new byte[byteBuffer.remaining()];
        byteBuffer.get(test);

        System.out.println(Arrays.toString(test));
    }
}
