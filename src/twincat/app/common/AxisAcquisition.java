package twincat.app.common;

import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class AxisAcquisition {
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private String axisName = new String();

    private String axisSymbolName = new String();
    
    private String amsNetId = AmsNetId.LOCAL;
       
    private AmsPort amsPort = AmsPort.UNKNOWN;

    /*********************************/
    /******** setter & getter ********/
    /*********************************/
  
    public String getAxisName() {
        return axisName;
    }

    public void setAxisName(String axisName) {
        this.axisName = axisName;
    }

    public String getAxisSymbolName() {
        return axisSymbolName;
    }

    public void setAxisSymbolName(String axisSymbolName) {
        this.axisSymbolName = axisSymbolName;
    }

    public String getAmsNetId() {
        return amsNetId;
    }

    public void setAmsNetId(String amsNetId) {
        this.amsNetId = amsNetId;
    }

    public AmsPort getAmsPort() {
        return amsPort;
    }

    public void setAmsPort(AmsPort amsPort) {
        this.amsPort = amsPort;
    }
}
