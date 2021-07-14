package twincat.ads.junit;

import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

import twincat.TwincatLogger;
import twincat.ads.worker.CommandLine;

public class CommandLineUnitTest {
    private final CommandLine adsCmd = new CommandLine();
    private final Logger logger = TwincatLogger.getLogger();
    
    @Test
    public void test() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        logger.info("Enter Command (\"e\" to exit)");
        
        while (running) {
            
            String command = scanner.nextLine();
            if (command.equals("E")) running = false;

            adsCmd.send(command);  
        }
        
        scanner.close();
    }
}
