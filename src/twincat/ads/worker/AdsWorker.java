package twincat.ads.worker;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.constant.AdsData;
import twincat.ads.constant.AmsPort;

public class AdsWorker {
    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int READ_SYMBOL_HANDLE_TIMEOUT = 10;
    
    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final AmsPort findAmsPort(String amsNetId, String symbolName) {
        AdsClient adsClient = new AdsClient();
        
        for (AmsPort amsPort : AdsData.AMS_PORT_SYMBOL_LIST) {
            try {
                adsClient.open();
                adsClient.setAmsNetId(amsNetId);
                adsClient.setAmsPort(amsPort);
                adsClient.setTimeout(READ_SYMBOL_HANDLE_TIMEOUT);               
                adsClient.readHandleOfSymbolName(symbolName);
                
                // ams port holds symbol name
                return amsPort;  
            } catch (AdsException e) {
                // skip read exception
            } finally {
                adsClient.close();
            }
        }
            
        return AmsPort.UNKNOWN;
    } 
}
