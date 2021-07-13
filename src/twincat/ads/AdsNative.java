package twincat.ads;

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
import twincat.ads.constant.AdsError;
import twincat.ads.constant.State;
import twincat.ads.container.DeviceInfo;
import twincat.ads.container.DeviceState;

public class AdsNative {
    /***********************************/
    /***** local constant variable *****/
    /***********************************/

    private static final String ADS_TO_JAVA_X86 = "C:\\TwinCAT\\AdsApi\\AdsToJava\\x86\\;";

    private static final String ADS_TO_JAVA_X64 = "C:\\TwinCAT\\AdsApi\\AdsToJava\\x64\\;";

    /***********************************/
    /********* local variable **********/
    /***********************************/

    protected long adsPort = 0;

    protected final AmsAddr amsAddress = new AmsAddr();

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

    public AdsNative() {
        // load java library path
        String javaLibraryPath = System.getProperty("java.library.path");
        StringBuilder stringBuilder = new StringBuilder(javaLibraryPath);

        // add ads library
        if (System.getProperty("os.arch").contains("x86")) {
            if (!stringBuilder.toString().contains(ADS_TO_JAVA_X86)) {
                stringBuilder.insert(0, ADS_TO_JAVA_X86);
            }
        } else {
            if (!stringBuilder.toString().contains(ADS_TO_JAVA_X64)) {
                stringBuilder.insert(0, ADS_TO_JAVA_X64);
            }
        }

        // set new library path
        System.setProperty("java.library.path", stringBuilder.toString());

        try {
            // force system library reload
            Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
            AdsCallDllFunction.adsGetDllVersion();
        } catch (NoSuchFieldException | IllegalAccessException | UnsatisfiedLinkError e) {}
    }

    /***********************************/
    /********* public function *********/
    /***********************************/

    protected void adsOpenPort() {
        if (adsPort == 0) {
            adsPort = AdsCallDllFunction.adsPortOpen();
        }
    }

    protected void adsClosePort() {
        adsPort = 0;
        AdsCallDllFunction.adsPortClose();
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

    protected DeviceInfo adsReadDeviceInfo() throws AdsException {
        if (adsPort != 0) {
            AdsDevName devName = new AdsDevName();
            AdsVersion version = new AdsVersion();

            long errorCode = AdsCallDllFunction.adsSyncReadDeviceInfoReq(amsAddress, devName, version);
            if (errorCode != 0) throw new AdsException(errorCode);

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceName(devName.getDevName());
            deviceInfo.setMinorVersion(version.getVersion());
            deviceInfo.setMajorVersion(version.getRevision());
            deviceInfo.setBuildVersion(version.getBuild());

            return deviceInfo;
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected DeviceState adsReadState() throws AdsException {
        if (adsPort != 0) {
            AdsState adsState = new AdsState();
            AdsState devState = new AdsState();

            long errorCode = AdsCallDllFunction.adsSyncReadStateReq(amsAddress, adsState, devState);

            DeviceState deviceState = new DeviceState();
            deviceState.setAdsState(State.getByValue(adsState.getState()));
            deviceState.setDevState(State.getByValue(devState.getState()));

            if (errorCode != 0) throw new AdsException(errorCode);

            return deviceState;
        } else throw new AdsException(AdsError.ADS_ADSPORT_CLOSED);
    }

    protected void adsWriteControl(DeviceState adsDeviceState, byte[] writeBuffer) throws AdsException {
        if (adsPort != 0) {
            JNIByteBuffer jniWriteBuffer = new JNIByteBuffer(writeBuffer);
            int jniWriteBufferLength = jniWriteBuffer.getUsedBytesCount();

            int adsState = adsDeviceState.getAdsState().value;
            int deviceState = adsDeviceState.getDevState().value;

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
}
