package twincat.ads.junit;

import java.util.Scanner;

import org.junit.Test;

import twincat.ads.AdsCmd;

public class AdsCmdUnitTest {
    @Test
    public void adsAmsPortUnitTest() {
        AdsCmd adsCmd = new AdsCmd();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("Enter Command:");
            String command = scanner.nextLine();
            
            if (command.equals("exit")) running = false;

            adsCmd.send(command);     
        }
        
        scanner.close();
    }
}
