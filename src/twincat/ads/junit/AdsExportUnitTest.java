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

public class AdsExportUnitTest {
	Ads ads = new Ads();
	
	@Before
	public void startAds() {
		ads.open();
		ads.setAmsPort(AmsPort.ROUTER);
	}
	
	@Test
	public void adsExportUnitTest() {
		try {
		    int indexGroup = AdsIdxGrp.SYM_UPLOAD;
			byte[] uploadBuffer = new byte[0xFFFF];
			ads.read(indexGroup, 0, uploadBuffer);
			String uploadDir = System.getProperty("user.dir");
			Path uploadPath = Paths.get(uploadDir + "/exp/SYM_UPLOAD.txt");
			Files.write(uploadPath, uploadBuffer);
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
