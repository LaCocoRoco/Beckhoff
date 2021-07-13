package twincat.ads.constant;

import java.util.Arrays;

public class AmsNetId {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final String LOCAL = "127.0.0.1.1.1";

    public static final int DATA_ID_COUNT = 6;

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final String netIdByteArraytoString(byte[] netId) {
        StringBuilder stringBuilder = new StringBuilder();
        
        if (netId.length == DATA_ID_COUNT) {
            for (int i = 0; i < netId.length; i++) {
                stringBuilder.append(netId[i] & 0xFF);
                
                if (i != DATA_ID_COUNT - 1) {
                    stringBuilder.append(".");  
                }
            }
        }
        
        return stringBuilder.toString();
    }

    public static final byte[] netIdStringtoByteArray(String netId) {
        String[] ids = netId.split("\\.");
        byte[] buffer = new byte[DATA_ID_COUNT];
        
        if (ids.length == DATA_ID_COUNT) {
            try {
                for (int i = 0; i < buffer.length; i++) {
                    int id = Integer.valueOf(ids[i]);
                    buffer[i] = (byte) id;
                }
            } catch (NumberFormatException e) {
                Arrays.fill(buffer, (byte) 0);
            }
        }
        
        return buffer;
    }
    
    public static final String netIdSTringToAddress(String netId) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] ids = netId.split("\\.");

        if (ids.length == DATA_ID_COUNT) {
            for (int i = 0; i < DATA_ID_COUNT - 2; i++) {
                stringBuilder.append(ids[i]);

                if (i != DATA_ID_COUNT - 3) {
                    stringBuilder.append(".");  
                }
            } 
        }

        return stringBuilder.toString();
    }
}
