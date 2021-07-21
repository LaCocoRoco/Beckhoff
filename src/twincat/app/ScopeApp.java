package twincat.app;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.JFrame;

import twincat.Resources;
import twincat.TwincatLogger;
import twincat.Utilities;
import twincat.app.constant.Window;

public class ScopeApp extends JFrame {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int WIDTH_FRAME = 800;

    private static final int HEIGHT_FRAME = 800;

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeApp(String[] args) {
        parseArgs(args);
        
        ScopeFrame scopeFrame = new ScopeFrame();
        scopeFrame.minifyMenuItems();
        scopeFrame.setMenuVisible(true);
        scopeFrame.setConsoleVisible(false);
        scopeFrame.setWindow(Window.SCOPE);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int framePositionX = screenSize.width / 2 - WIDTH_FRAME / 2;
        int framePositionY = screenSize.height / 2 - HEIGHT_FRAME / 2;
        
        this.setContentPane(scopeFrame);
        this.setTitle(languageBundle.getString("applicationName"));
        this.setIconImage(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        this.setBounds(framePositionX, framePositionY, WIDTH_FRAME, HEIGHT_FRAME);
        this.pack();
        this.setVisible(true);
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void parseArgs(String[] args)  {
        for(String arg : args) {
            switch(arg) {
                case "log":
                    TwincatLogger.addFileLogger(); 
                    break;
                
                case "off":
                    TwincatLogger.setLevel(Level.OFF);
                    break;

                case "fine":
                    TwincatLogger.setLevel(Level.FINE);
                    break; 
                    
                case "info":
                    TwincatLogger.setLevel(Level.INFO);
                    break; 
                    
                case "warning":
                    TwincatLogger.setLevel(Level.WARNING);
                    break;
                    
                case "severe":
                    TwincatLogger.setLevel(Level.SEVERE);
                    break;
            }
        }
    }
    
    /*********************************/
    /** public static final method ***/
    /*********************************/
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ScopeApp(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
