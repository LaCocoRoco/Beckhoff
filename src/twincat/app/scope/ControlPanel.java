package twincat.app.scope;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class ControlPanel extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int DIVIDER_SIZE = 5;

    private static final double DIVIDER_LOCATION = 0.5;

    /*************************/
    /*** local attributes ***/
    /*************************/
    
    private BrowserPanel browserPanel = new BrowserPanel();
    
    private SettingsPanel settingsPanel = new SettingsPanel();
    
    /*************************/
    /****** constructor ******/
    /*************************/
    
    public ControlPanel() {
        this.setLeftComponent(browserPanel);
        this.setRightComponent(settingsPanel);
        this.setDividerSize(DIVIDER_SIZE);
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder()); 
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setDividerLocation(DIVIDER_LOCATION);
            }
        });
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setDividerLocation(DIVIDER_LOCATION);
            }
        }); 
    } 
}
