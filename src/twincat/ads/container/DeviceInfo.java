package twincat.ads.container;

public class DeviceInfo {
    /*********************************/
    /******** global variable ********/
    /*********************************/
		
	private String deviceName = new String();

	private int majorVersion = 0;
	
	private int minorVersion = 0;
	
	private int buildVersion = 0;

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getBuildVersion() {
		return buildVersion;
	}

	public void setBuildVersion(int buildVersion) {
		this.buildVersion = buildVersion;
	}

}
