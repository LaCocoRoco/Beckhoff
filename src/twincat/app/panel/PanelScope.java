package twincat.app.panel;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twincat.app.constants.Resources;
import twincat.scope.Scope;

public class PanelScope extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final double DIVIDER_LOCATION = 0.5;

    /*************************/
    /*** local attributes ***/
    /*************************/

    public final List<Scope> scopeList = new ArrayList<Scope>();

    public final PanelChart panelChart = new PanelChart(this);

    public final PanelControl panelControl = new PanelControl(this);
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelScope() {
        this.setLeftComponent(panelChart);
        this.setRightComponent(panelControl);
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

    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public List<Scope> getScopeList() {
        return scopeList;
    }

    public PanelChart getPanelChart() {
        return panelChart;
    }

    public PanelControl getPanelControl() {
        return panelControl;
    }
    
}
