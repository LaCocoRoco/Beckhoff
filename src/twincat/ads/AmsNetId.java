package twincat.ads;

import java.util.Arrays;

// TODO : net id to address

public class AmsNetId {
    /*************************/
    /** constant attributes **/
    /*************************/

    public static final String LOCAL = "127.0.0.1.1.1";

    public static final int DATA_LENGTH = 6;

    /*************************/
    /** public static final **/
    /*************************/
 
    public static final String arrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        
        if (data.length == DATA_LENGTH) {
            for (int i = 0; i < data.length; i++) {
                stringBuilder.append(data[i] & 0xFF);
                
                if (i != DATA_LENGTH - 1) {
                    stringBuilder.append(".");  
                }
            }
        }
        
        return stringBuilder.toString();
    }

    public static final byte[] stringToArray(String data) {
        String[] values = data.split(".");
        byte[] buffer = new byte[DATA_LENGTH];
        
        if (values.length == DATA_LENGTH) {
            try {
                for (int i = 0; i < buffer.length; i++) {
                    int id = Integer.valueOf(values[i]);
                    buffer[i] = (byte) id;
                }
            } catch (NumberFormatException e) {
                Arrays.fill(buffer, (byte) 0);
            }
        }
        
        return buffer;
    }
}
