package twincat.ads.constants;

public enum AmsPort {
    /*************************/
    /** constant attributes **/
    /*************************/

    LOGGER(0x0064),
    EVENTLOG(0x006E),
    RTIME(0x00C8),
    TRACE(0x0122),
    IO(0x012C),
    SPS(0x0190),
    NC(0x01F4),
    NCSAF(0x01F5),
    NCSVB(0x01FF),
    CNC(0x0258),
    PLC_RT1(0x0321),
    PLC_RT2(0x032B),
    PLC_RT3(0x0335),
    PLC_RT4(0x033F),
    CAM(0x0384),
    CAMTOOL(0x03B6),
    SYSSERV(0x2710),
    UNKNOWN(0xFFFF);

    /*************************/
    /*** global attributes ***/
    /*************************/

    public final int value;

    /*************************/
    /****** constructor ******/
    /*************************/

    private AmsPort(int value) {
        this.value = value;
    }

    /*************************/
    /** public static final **/
    /*************************/

    public static final AmsPort getByValue(int value) {
        for (AmsPort dataType : AmsPort.values()) {
            if (dataType.value == value) {
                return dataType;
            }
        }

        return AmsPort.UNKNOWN;
    }

    public static final AmsPort getByString(String value) {
        for (AmsPort amsPort : AmsPort.values()) {
            if (amsPort.name().equalsIgnoreCase(value)) {
                return amsPort;
            }
        }
        return AmsPort.UNKNOWN;
    }
}
