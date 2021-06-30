package twincat.app;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

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

    private final ResourceBundle textBundle = ResourceBundle.getBundle("resources/string/text");
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeApp() {
        ScopeFrame scopeFrame = new ScopeFrame();
        scopeFrame.mainMenuVisible(true);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int framePositionX = screenSize.width / 2 - WIDTH_FRAME / 2;
        int framePositionY = screenSize.height / 2 - HEIGHT_FRAME / 2;

        this.setTitle(textBundle.getString("applicationName"));
        this.setIconImage(new ImageIcon(getClass().getResource(APP_ICON_PATH)).getImage());
        this.setContentPane(scopeFrame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        this.setBounds(framePositionX, framePositionY, WIDTH_FRAME, HEIGHT_FRAME);
        this.pack();
        this.setVisible(true);
    }

    /*************************/
    /** public static final **/
    /*************************/ 
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ScopeApp application = new ScopeApp();
                    application.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
