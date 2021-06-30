package twincat.ads.constants;

public enum AmsPort {
    /*************************/
    /** constant attributes **/
    /*************************/

    NONE                (0x0000),
    ROUTER              (0x0001),
    DEBUGGER            (0x0002),
    TCOMSERVER          (0x000A),
    TCOMSERVERTASK      (0x000B),
    TCOMSERVERPL        (0x000C),
    TCDEBUGGER          (0x0014),
    TCDEBUGGERTASK      (0x0015),
    LICENSESERVER       (0x001E),
    LOGGER              (0x0064),
    EVENTLOG            (0x006E),
    DEVICEAPPLICATION   (0x0078),
    EVENTLOGUM          (0x0082),
    EVENTLOGRT          (0x0083),
    EVENTLOGPUBLISHER   (0x0084),
    REALTIME            (0x00C8),
    TRACE               (0x0122),
    IO                  (0x012C),
    NC                  (0x01F4),
    NCSAF               (0x01F5),
    NCSVB               (0x01FF),
    NCINSTANCE          (0x0208),
    ISG                 (0x0226),
    CNC                 (0x0258),
    LINE                (0x02BC),
    PLC                 (0x0320),
    TC2PLC1             (0x0321),
    TC2PLC2             (0x032B),
    TC2PLC3             (0x0335),
    TC2PLC4             (0x033F),
    RTS                 (0x0352),
    TC3PLC1             (0x0353),
    TC3PLC2             (0x0354),
    TC3PLC3             (0x0355),
    TC3PLC4             (0x0356),
    TC3PLC5             (0x0357),
    CAMSHAFTCONTROLLER  (0x0384),
    CAMTOOL             (0x03B6),
    USER                (0x07D0),
    CTRLPROG            (0x2710),
    SYSTEMSERVICE       (0x2710),
    SYSCTRL             (0x2711),
    SYSSAMPLER          (0x2774),
    TCPRAWCONN          (0x27D8),
    TCPIPSERVER         (0x27D9),
    SYSMANAGER          (0x283C),
    SMSSERVER           (0x28A0),
    MODBUSSERVER        (0x2904),
    AMSLOGGER           (0x2906),
    XMLDATASERVER       (0x2968),
    AUTOCONFIG          (0x29CC),
    PLCCONTROL          (0x2A30),
    FTPCLIENT           (0x2A94),
    NCCTRL              (0x2AF8),
    NCINTERPRETER       (0x2CEC),
    GSTINTERPRETER      (0x2D50),
    STRECKECTRL         (0x2EE0),
    CAMCTRL             (0x32C8),
    SCOPE               (0x36B0),
    CONDITIONMON        (0x3714),
    SINECH1             (0x3A98),
    CONTROLNET          (0x3E80),
    OPCSERVER           (0x4268),
    OPCCLIENT           (0x445C),
    MAILSERVER          (0x4650),
    EL60XX              (0x4A38),
    MANAGEMENT          (0x4A9C),
    MIELEHOME           (0x4B00),
    CPLINK3             (0x4B64),
    VNSERVICE           (0x4C2C),
    MULTIUSER           (0x4C90),
    UNKNOWN             (0xFFFF);

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
