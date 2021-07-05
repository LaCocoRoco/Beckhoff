package twincat.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import twincat.ads.datatype.STRING;

public class AdsRoute {
    /*************************/
    /** constant attributes **/
    /*************************/
    
    private static final int UNKNOWN_DATA_LENGTH = 38;

    private static final int ADDRESS_MAX_DATA_LENGTH = 24;
    
    private static final int HOST_NAME_MAX_DATA_LENGTH = 32;

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private String hostName = new String();

    private String hostAddress = new String();
 
    private String amsNetId = new String();

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public AdsRoute() { 
        /* empty */
    }  
    
    public AdsRoute(byte[] buffer) { 
        parseRoute(buffer);
    }  
 
    /*************************/
    /**** setter & getter ****/
    /*************************/
 
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getAmsNetId() {
        return amsNetId;
    }

    public void setAmsNetId(String amsNetId) {
        this.amsNetId = amsNetId;
    }
  
    /*************************/
    /******** private ********/
    /*************************/

    public void parseRoute(byte[] buffer) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        if (byteBuffer.remaining() >= AmsNetId.DATA_LENGTH) {
            byte[] readBuffer = new byte[AmsNetId.DATA_LENGTH];
            byteBuffer.get(readBuffer, 0, AmsNetId.DATA_LENGTH);
            amsNetId = AmsNetId.arrayToString(readBuffer);
        }
     
        if (byteBuffer.remaining() >= UNKNOWN_DATA_LENGTH) {
            byteBuffer.position(byteBuffer.position() + UNKNOWN_DATA_LENGTH);
        }

        if (byteBuffer.remaining() >= ADDRESS_MAX_DATA_LENGTH) {
            byte[] readBuffer = new byte[ADDRESS_MAX_DATA_LENGTH];
            byteBuffer.get(readBuffer, 0, ADDRESS_MAX_DATA_LENGTH);
            hostAddress = STRING.arrayToValue(readBuffer);
            int positionOffset = ADDRESS_MAX_DATA_LENGTH - hostAddress.length();
            byteBuffer.position(byteBuffer.position() - positionOffset + 1);
        }
        
        if (byteBuffer.remaining() >= HOST_NAME_MAX_DATA_LENGTH) {
            byte[] readBuffer = new byte[HOST_NAME_MAX_DATA_LENGTH];
            byteBuffer.get(readBuffer, 0, HOST_NAME_MAX_DATA_LENGTH);
            hostName = STRING.arrayToValue(readBuffer);
            int positionOffset = HOST_NAME_MAX_DATA_LENGTH - hostName.length();
            byteBuffer.position(byteBuffer.position() - positionOffset + 1);
        }
    }
}
