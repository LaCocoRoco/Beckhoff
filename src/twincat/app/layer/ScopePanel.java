package twincat.app.layer;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twincat.Resources;

public class ScopePanel extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int DIVIDER_SIZE = 5;

    private static final double DIVIDER_LOCATION = 0.5;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopePanel(XReference xref) {
        this.setLeftComponent(xref.navigationPanel);
        this.setRightComponent(xref.controlPanel);
        this.setDividerSize(Resources.DEFAULT_DIVIDER_SIZE);
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
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

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void controlToggle() {
        if (this.getDividerSize() != 0) {
            controlHide();
        } else {
            controlShow();
        }
    }

    public void controlShow() {
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

    public void controlHide() {
        getRightComponent().setVisible(false);
        getLeftComponent().setVisible(true);
        setDividerSize(0);
    }
}
