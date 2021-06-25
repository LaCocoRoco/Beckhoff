package twincat.ads.junit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.constants.AdsIdxGrp;
import twincat.ads.enums.AmsPort;

public class AdsReadIdxGrpToFileUnitTest {
	Ads ads = new Ads();
	
	@Before
	public void startAds() {
		ads.open();
	}
	
	@Test
	public void adsReadIdxGrpToFileUnitTest() {
		try {
			// ads port
			ads.setAmsPort(AmsPort.SYSSERV);
			
			// index group
			int adsIdxGrp = AdsIdxGrp.DEVICE_DATA;
			
			// read data
			byte[] readBuffer = new byte[0xFFFF];
			ads.read(adsIdxGrp, 0, readBuffer);
			String uploadDir = System.getProperty("user.dir");
			Path uploadPath = Paths.get(uploadDir + "/exp/IDX_GRP_DATA.txt");
			Files.write(uploadPath, readBuffer);
		} catch (AdsException e) {
			System.out.println(e.getAdsErrorMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void stopAds() throws AdsException {
		ads.close();
	}
}
