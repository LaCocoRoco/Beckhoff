package twincat.app.layer;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.scope.Scope;

public class ScopePanel extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final double DIVIDER_LOCATION = 0.5;


    /*********************************/
    /***** global final variable *****/
    /*********************************/

    private final List<Scope> scopeList = new ArrayList<Scope>();

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopePanel(XReference xref) {
        this.setLeftComponent(xref.chartPanel);
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
    /******** setter & getter ********/
    /*********************************/
    
    public List<Scope> getScopeList() {
        return scopeList;
    }
}
