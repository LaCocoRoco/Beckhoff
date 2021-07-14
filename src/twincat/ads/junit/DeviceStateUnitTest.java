package twincat.ads.junit;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.DeviceState;
import twincat.ads.constant.State;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class DeviceStateUnitTest {
    private final AdsClient adsClient = new AdsClient();
    private final Logger logger = TwincatLogger.getLogger();
	
	@Before
	public void start() {
		adsClient.open();
	}

	@Test
	public void test() {
		try {
	        adsClient.setAmsNetId(AmsNetId.LOCAL);
	        adsClient.setAmsPort(AmsPort.TC2PLC1);
		    
			DeviceState deviceState = new DeviceState();
			
			deviceState = adsClient.readDeviceState();
			logger.info("AdsState   : " + deviceState.getAdsState());
			logger.info("DeviceState: " + deviceState.getDevState());
			
			switch(deviceState.getAdsState()) {
				case STOP:
					deviceState.setAdsState(State.RUN);
					break;
					
				case RUN:
					deviceState.setAdsState(State.STOP);
					break;
					
				default:
				    deviceState.setAdsState(State.INVALID);
				    break;  
			}

			adsClient.writeDeviceState(deviceState, null);
			logger.info("AdsState   : " + deviceState.getAdsState());
			logger.info("DeviceState: " + deviceState.getDevState());		
		} catch (AdsException e) {
			logger.info(e.getAdsErrorMessage());
		}
	}

	@After
	public void stop() throws AdsException {
		adsClient.close();
	}
}
