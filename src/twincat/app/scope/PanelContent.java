package twincat.app.scope;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

public class PanelContent extends JSplitPane {
    private static final long serialVersionUID = 1L;
    
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int DIVIDER_SIZE = 5;
   
    private static final double DIVIDER_LOCATION = 0.8;

    /*************************/
    /*** global attributes ***/
    /*************************/

    private final PanelConsole panelConsole = new PanelConsole();

    private final PanelWindow panelWindow = new PanelWindow();
      
    /*************************/
    /****** constructor ******/
    /*************************/
   
    public PanelContent() {
        this.setLeftComponent(panelWindow);
        this.setRightComponent(panelConsole);
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setBorder(new EmptyBorder(3, 3, 3, 3));
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public PanelConsole getPanelConsole() {
        return panelConsole;
    }

    public PanelWindow getPanelWindow() {
        return panelWindow;
    }

    /*************************/
    /******** public *********/
    /*************************/

    public void consoleToggle() {
        if (this.getDividerSize() != 0) {
            consoleHide();
        } else {
            consoleShow();
        }
    }
    
    public void consoleShow() {
        setDividerLocation(DIVIDER_LOCATION);
        getRightComponent().setVisible(true);
        getLeftComponent().setVisible(true);
        setDividerSize(DIVIDER_SIZE);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setDividerLocation(DIVIDER_LOCATION);
            }
        });
    }
    
    public void consoleHide() {
        getRightComponent().setVisible(false);
        getLeftComponent().setVisible(true);
        setDividerSize(0);
    }      
}
