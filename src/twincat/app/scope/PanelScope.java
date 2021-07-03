package twincat.app.scope;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twincat.app.constants.Resources;

public class PanelScope extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final double DIVIDER_LOCATION = 0.5;

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final PanelChart chartPanel = new PanelChart();
    
    private final PanelControl controlPanel = new PanelControl();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelScope() {
        this.setLeftComponent(chartPanel);
        this.setRightComponent(controlPanel);
        this.setDividerSize(Resources.DEFAULT_DIVIDER_SIZE);
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setBackground(Color.WHITE);
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
