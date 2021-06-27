package twincat.ads.jni;

import java.lang.reflect.Field;

import de.beckhoff.jni.JNIByteBuffer;
import de.beckhoff.jni.JNILong;
import de.beckhoff.jni.tcads.AdsCallDllFunction;
import de.beckhoff.jni.tcads.AdsCallbackObject;
import de.beckhoff.jni.tcads.AdsDevName;
import de.beckhoff.jni.tcads.AdsNotificationAttrib;
import de.beckhoff.jni.tcads.AdsState;
import de.beckhoff.jni.tcads.AdsVersion;
import de.beckhoff.jni.tcads.AmsAddr;
import twincat.ads.AdsCallback;
import twincat.ads.AdsDeviceInfo;
import twincat.ads.AdsDeviceState;
import twincat.ads.AdsException;
import twincat.ads.AdsNotification;
import twincat.ads.constants.AdsError;
import twincat.ads.constants.AdsStatus;
import twincat.ads.constants.AmsNetId;
import twincat.ads.constants.AmsPort;

public class AdsNative {
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final String ADS_TO_JAVA_X86 = "C:\\TwinCAT\\AdsApi\\AdsToJava\\x86\\;";

    private static final String ADS_TO_JAVA_X64 = "C:\\TwinCAT\\AdsApi\\AdsToJava\\x64\\;";

    /*************************/
    /*** global attributes ***/
    /*************************/

    private long adsPort = 0;

    private AmsPort amsPort = AmsPort.PLC_RT1;

    private String amsNetId = AmsNetId.LOCAL;

    private AmsAddr amsAddress = new AmsAddr();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsNative() {
        setAdsToJavaLibraryPath();
        setAmsNetId(amsNetId);
        setAmsPort(amsPort);
    }

    public AdsNative(AmsPort amsPort) {
        setAdsToJavaLibraryPath();
        setAmsNetId(amsNetId);
        setAmsPort(amsPort);
    }

    public AdsNative(String amsNetId) {
        setAdsToJavaLibraryPath();
        setAmsNetId(amsNetId);
        setAmsPort(amsPort);
    }

    public AdsNative(String amsNetId, AmsPort amsPort) {
        setAdsToJavaLibraryPath();
        setAmsNetId(amsNetId);
        setAmsPort(amsPort);
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public long getAdsPort() {
        return adsPort;
    }

    public AmsPort getAmsPort() {
        return amsPort;
    }

    public void setAmsPort(AmsPort amsPort) {
        this.amsPort = amsPort;
        amsAddress.setPort(amsPort.value);
    }

    public String getAmsNetId() {
        return amsNetId;
    }

    public void setAmsNetId(String amsNetId) {
        this.amsNetId = amsNetId;
        amsAddress.setNetIdStringEx(amsNetId);
    }

    /*************************/
    /********* public ********/
    /*************************/

    protected void adsOpenPort() {
        if (adsPort == 0) {
            adsPort = AdsCallDllFunction.adsPortOpen();
        }
    }

    protected void adsClosePort() throws AdsException {
        if (adsPort != 0) {
            adsPort = 0;
            long errorCode = AdsCallDllFunction.adsPortClose();
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected String adsGetLocalAddress() throws AdsException {
        if (adsPort != 0) {
            AmsAddr amsAddress = new AmsAddr();
            long errorCode = AdsCallDllFunction.getLocalAddress(amsAddress);
            if (errorCode != 0) throw new AdsException(errorCode);
            return amsAddress.getNetIdString();
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected void adsSetTimeout(long timeout) throws AdsException {
        if (adsPort != 0) {
            long errorCode = AdsCallDllFunction.adsSyncSetTimeout(timeout);
            if (errorCode != 0) throw new AdsException(errorCode);
        }
    }

    protected String adsReadVersion() {
        AdsVersion adsDllVersion = AdsCallDllFunction.adsGetDllVersion();

        String version = String.valueOf(new Integer(adsDllVersion.getVersion()));
        String revision = String.valueOf(new Integer(adsDllVersion.getRevision()));
        String build = String.valueOf(new Integer(adsDllVersion.getBuild()));

        return version + "." + revision + "." + build;
    }

    protected AdsDeviceInfo adsReadDeviceInfo() throws AdsException {
        if (adsPort != 0) {
            AdsDevName adsDevName = new AdsDevName();
            AdsVersion adsVersion = new AdsVersion();
            long errorCode = AdsCallDllFunction.adsSyncReadDeviceInfoReq(amsAddress, adsDevName, adsVersion);
            if (errorCode != 0) throw new AdsException(errorCode);
            AdsDeviceInfo adsDeviceInfo = new AdsDeviceInfo();
            adsDeviceInfo.setDeviceName(adsDevName.getDevName());
            adsDeviceInfo.setMinorVersion(adsVersion.getVersion());
            adsDeviceInfo.setMajorVersion(adsVersion.getRevision());
            adsDeviceInfo.setBuildVersion(adsVersion.getBuild());
            return adsDeviceInfo;
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected AdsDeviceState adsReadState() throws AdsException {
        if (adsPort != 0) {
            AdsState adsState = new AdsState();
            AdsState deviceState = new AdsState();
            long errorCode = AdsCallDllFunction.adsSyncReadStateReq(amsAddress, adsState, deviceState);
            AdsDeviceState adsDeviceState = new AdsDeviceState();
            adsDeviceState.setAdsState(AdsStatus.getByValue(adsState.getState()));
            adsDeviceState.setDeviceState(AdsStatus.getByValue(deviceState.getState()));
            if (errorCode != 0) throw new AdsException(errorCode);
            return adsDeviceState;
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected void adsWriteControl(AdsDeviceState adsDeviceState, byte[] writeBuffer) throws AdsException {
        if (adsPort != 0) {
            JNIByteBuffer jniWriteBuffer = new JNIByteBuffer(writeBuffer);
            int jniWriteBufferLength = jniWriteBuffer.getUsedBytesCount();
            int adsState = adsDeviceState.getAdsState().value;
            int deviceState = adsDeviceState.getDeviceState().value;
            long errorCode = AdsCallDllFunction.adsSyncWriteControlReq(amsAddress,
                    adsState, deviceState, jniWriteBufferLength, jniWriteBuffer);
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected void adsRead(long idxGrp, long idxOffs, byte[] readBuffer) throws AdsException {
        if (adsPort != 0) {
            JNIByteBuffer jniReadBuffer = new JNIByteBuffer(readBuffer);
            int jniReadBufferL = jniReadBuffer.getUsedBytesCount();
            long errorCode = AdsCallDllFunction.adsSyncReadReq(amsAddress,
                    idxGrp, idxOffs, jniReadBufferL, jniReadBuffer);
            System.arraycopy(jniReadBuffer.getByteArray(), 0, readBuffer, 0, readBuffer.length);
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected void adsWrite(long idxGrp, long idxOffs, byte[] writeBuffer) throws AdsException {
        if (adsPort != 0) {
            JNIByteBuffer jniWriteBuffer = new JNIByteBuffer(writeBuffer);
            int jniWriteBufferLength = jniWriteBuffer.getUsedBytesCount();
            long errorCode = AdsCallDllFunction.adsSyncWriteReq(amsAddress,
                    idxGrp, idxOffs, jniWriteBufferLength, jniWriteBuffer);
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected void adsReadWrite(long idxGrp, long idxOffs, byte[] readBuffer, byte[] writeBuffer) throws AdsException {
        if (adsPort != 0) {
            JNIByteBuffer jniWriteBuffer = new JNIByteBuffer(writeBuffer);
            int jniWriteBufferLength = jniWriteBuffer.getUsedBytesCount();
            JNIByteBuffer jniReadBuffer = new JNIByteBuffer(readBuffer);
            int jniReadBufferL = jniReadBuffer.getUsedBytesCount();
            long errorCode = AdsCallDllFunction.adsSyncReadWriteReq(amsAddress, idxGrp, idxOffs,
                    jniReadBufferL, jniReadBuffer, jniWriteBufferLength, jniWriteBuffer);
            System.arraycopy(jniReadBuffer.getByteArray(), 0, readBuffer, 0, readBuffer.length);
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected long adsAddDeviceNotification(long idxGrp, long idxOffs, long user,
            AdsNotification notification, AdsCallback callback) throws AdsException {
        JNILong jniLong = new JNILong();

        if (adsPort != 0) {
            AdsNotificationAttrib adsNotificationAttribute = new AdsNotificationAttrib();
            adsNotificationAttribute.setCbLength(notification.getDataLength());
            adsNotificationAttribute.setNTransMode(notification.getTransmissionMode().value);
            adsNotificationAttribute.setDwChangeFilter(notification.getCycleTime());
            adsNotificationAttribute.setNMaxDelay(notification.getMaxDelay());
            long errorCode = AdsCallDllFunction.adsSyncAddDeviceNotificationReq(amsAddress,
                    idxGrp, idxOffs, adsNotificationAttribute, user, jniLong);
            AdsCallbackObject adsCallbackObject = new AdsCallbackObject();
            adsCallbackObject.addListenerCallbackAdsState(callback);
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);

        return jniLong.getLong();
    }

    protected void adsDeleteDeviceNotification(long notification, AdsCallback callback) throws AdsException {
        if (adsPort != 0) {
            JNILong jniLong = new JNILong(notification);
            long errorCode = AdsCallDllFunction.adsSyncDelDeviceNotificationReq(amsAddress, jniLong);
            AdsCallbackObject adsCallbackObject = new AdsCallbackObject();
            adsCallbackObject.removeListenerCallbackAdsState(callback);
            if (errorCode != 0) throw new AdsException(errorCode);
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    /*************************/
    /******** private ********/
    /*************************/

    private void setAdsToJavaLibraryPath() {
        // get java library path
        String javaLibraryPath = System.getProperty("java.library.path");
        StringBuilder stringBuilder = new StringBuilder(javaLibraryPath);

        // add ads to java DLL
        if (System.getProperty("os.arch").contains("x86")) {
            if (!stringBuilder.toString().contains(ADS_TO_JAVA_X86)) {
                stringBuilder.insert(0, ADS_TO_JAVA_X86);
            }
        } else {
            if (!stringBuilder.toString().contains(ADS_TO_JAVA_X64)) {
                stringBuilder.insert(0, ADS_TO_JAVA_X64);
            }
        }

        // set java library path
        System.setProperty("java.library.path", stringBuilder.toString());

        try {
            // force reload system path
            Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {}
    }
}
