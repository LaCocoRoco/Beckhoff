package twincat.app;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import twincat.TwincatLogger;
import twincat.Utilities;

public class ScopeApp extends JFrame {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int WIDTH_FRAME = 800;

    private static final int HEIGHT_FRAME = 600;

    private static final String APP_ICON_PATH = "/resources/images/app.png";
    
    /*************************/
    /*** local attributes ***/
    /*************************/
  
    private final Logger logger = TwincatLogger.getSignedLogger();
    
    private final ResourceBundle textBundle = ResourceBundle.getBundle("resources/string/text");
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeApp(String[] args) {
        parseArgs(args);
        
        ScopeFrame scopeFrame = new ScopeFrame();
        scopeFrame.mainMenuVisible(true);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int framePositionX = screenSize.width / 2 - WIDTH_FRAME / 2;
        int framePositionY = screenSize.height / 2 - HEIGHT_FRAME / 2;
        
        this.setContentPane(scopeFrame);
        this.setTitle(textBundle.getString("applicationName"));
        this.setIconImage(Utilities.getImageFromFilePath(APP_ICON_PATH));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        this.setBounds(framePositionX, framePositionY, WIDTH_FRAME, HEIGHT_FRAME);
        this.pack();
        this.setVisible(true);
    }

    /*************************/
    /******** private ********/
    /*************************/

    private void parseArgs(String[] args)  {
        for(String arg : args) {
            switch(arg) {
                case "log":
                    TwincatLogger.addFileLogger(); 
                    break;
                
                case "off":
                    logger.setLevel(Level.OFF);
                    break;

                case "fine":
                    logger.setLevel(Level.FINE);
                    break; 
                    
                case "info":
                    logger.setLevel(Level.INFO);
                    break; 
                    
                case "warning":
                    logger.setLevel(Level.WARNING);
                    break;
                    
                case "severe":
                    logger.setLevel(Level.SEVERE);
                    break;
            }
        }
    }
    
    /*************************/
    /** public static final **/
    /*************************/ 
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ScopeApp application = new ScopeApp(args);
                    application.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
