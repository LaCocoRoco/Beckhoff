package twincat.ads.constant;

public enum AmsPort {
    /***********************************/
    /**** global constant variable *****/
    /***********************************/

    NONE                (0x0000),   // 00000
    ROUTER              (0x0001),   // 00001
    DEBUGGER            (0x0002),   // 00002
    TCOMSERVER          (0x000A),   // 00010
    TCOMSERVERTASK      (0x000B),   // 00011
    TCOMSERVERPL        (0x000C),   // 00012
    TCDEBUGGER          (0x0014),   // 00020
    TCDEBUGGERTASK      (0x0015),   // 00021
    LICENSESERVER       (0x001E),   // 00030
    LOGGER              (0x0064),   // 00100
    EVENTLOG            (0x006E),   // 00110
    DEVICEAPPLICATION   (0x0078),   // 00120
    EVENTLOGUM          (0x0082),   // 00130
    EVENTLOGRT          (0x0083),   // 00131
    EVENTLOGPUBLISHER   (0x0084),   // 00132
    REALTIME            (0x00C8),   // 00200
    TRACE               (0x0122),   // 00290
    IO                  (0x012C),   // 00300
    NC                  (0x01F4),   // 00500
    NCSAF               (0x01F5),   // 00501
    NCSVB               (0x01FF),   // 00511
    NCINSTANCE          (0x0208),   // 00520
    ISG                 (0x0226),   // 00550
    CNC                 (0x0258),   // 00600
    LINE                (0x02BC),   // 00700
    PLC                 (0x0320),   // 00800
    TC2PLC1             (0x0321),   // 00801
    TC2PLC2             (0x032B),   // 00811
    TC2PLC3             (0x0335),   // 00821
    TC2PLC4             (0x033F),   // 00831
    RTS                 (0x0352),   // 00850
    TC3PLC1             (0x0353),   // 00851
    TC3PLC2             (0x0354),   // 00852
    TC3PLC3             (0x0355),   // 00853
    TC3PLC4             (0x0356),   // 00854
    TC3PLC5             (0x0357),   // 00855
    CAMSHAFTCONTROLLER  (0x0384),   // 00900
    CAMTOOL             (0x03B6),   // 00950
    USER                (0x07D0),   // 02000
    SYSTEMSERVICE       (0x2710),   // 10000
    SYSCTRL             (0x2711),   // 10001
    SYSSAMPLER          (0x2774),   // 10100
    TCPRAWCONN          (0x27D8),   // 10200
    TCPIPSERVER         (0x27D9),   // 10201
    SYSMANAGER          (0x283C),   // 10300
    SMSSERVER           (0x28A0),   // 10400
    MODBUSSERVER        (0x2904),   // 10500
    AMSLOGGER           (0x2906),   // 10502
    XMLDATASERVER       (0x2968),   // 10600
    AUTOCONFIG          (0x29CC),   // 10700
    PLCCONTROL          (0x2A30),   // 10800
    FTPCLIENT           (0x2A94),   // 10900
    NCCTRL              (0x2AF8),   // 11000
    NCINTERPRETER       (0x2CEC),   // 11500
    GSTINTERPRETER      (0x2D50),   // 11600
    STRECKECTRL         (0x2EE0),   // 12000
    CAMCTRL             (0x32C8),   // 13000
    SCOPE               (0x36B0),   // 14000
    CONDITIONMON        (0x3714),   // 14100
    SINECH1             (0x3A98),   // 15000
    CONTROLNET          (0x3E80),   // 16000
    OPCSERVER           (0x4268),   // 17000
    OPCCLIENT           (0x445C),   // 17500
    MAILSERVER          (0x4650),   // 18000
    EL60XX              (0x4A38),   // 19000
    MANAGEMENT          (0x4A9C),   // 19100
    MIELEHOME           (0x4B00),   // 19200
    CPLINK3             (0x4B64),   // 19300
    VNSERVICE           (0x4C2C),   // 19500
    MULTIUSER           (0x4C90),   // 19600
    UNKNOWN             (0xFFFF);   // 65535

    /***********************************/
    /********* global variable *********/
    /***********************************/

    public final int value;

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

    private AmsPort(int value) {
        this.value = value;
    }

    /***********************************/
    /** public static final function ***/
    /***********************************/

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
