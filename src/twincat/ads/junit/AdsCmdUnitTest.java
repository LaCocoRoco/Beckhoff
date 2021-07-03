package twincat.ads.junit;

import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.AdsCmd;

public class AdsCmdUnitTest {
    private final AdsCmd adsCmd = new AdsCmd();
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    @Test
    public void adsAmsPortUnitTest() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            logger.info("Enter Command (Enter \"E\" to exit)");
            String command = scanner.nextLine();
            
            if (command.equals("E")) running = false;

            adsCmd.send(command);  
        }
        
        scanner.close();
    }
}
