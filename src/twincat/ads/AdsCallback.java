package twincat.ads;

import de.beckhoff.jni.tcads.AdsNotificationHeader;
import de.beckhoff.jni.tcads.AmsAddr;
import de.beckhoff.jni.tcads.CallbackListenerAdsState;

public interface AdsCallback extends CallbackListenerAdsState {

	void update(long notification, long timeStampe, byte[] data);
	
	@Override
	default void onEvent(AmsAddr amsAddr, AdsNotificationHeader header, long user) {
		update(header.getHNotification(), header.getNTimeStamp(), header.getData());
	}
}
