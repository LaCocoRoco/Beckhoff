package twincat.app.components;

import java.awt.CardLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import twincat.ads.Ads;

public class ScopePanel extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    /*************************/
    /*** global attributes ***/
    /*************************/

    /*************************/
    /*** local attributes ***/
    /*************************/

    Ads ads = new Ads();

    JScrollPane scopeTopPanel = new JScrollPane();
    
    JPanel chartPanel = new JPanel();
    
    JSplitPane bottomPanel = new JSplitPane();
    
    
    
    JScrollPane leftPanel = new JScrollPane();
    
    JScrollPane rightPanel = new JScrollPane();
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopePanel() {
        // scope panel
        this.setDividerSize(5);   
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setLeftComponent(scopeTopPanel);
        this.setRightComponent(bottomPanel);
        this.setBorder(new EmptyBorder(3, 3, 3, 3));
        this.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                setDividerLocation(0.5);    // TODO : on resize
            }
        });
        
        
        // top panel
        chartPanel.setAlignmentX(TOP_ALIGNMENT);
        chartPanel.setAlignmentY(CENTER_ALIGNMENT);
        chartPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
        chartPanel.add(new JLabel("Chart Panel"));
        scopeTopPanel.setViewportView(chartPanel);   
        
        // bottom panel
        bottomPanel.setDividerSize(5);   
        bottomPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        bottomPanel.setContinuousLayout(true);
        bottomPanel.setOneTouchExpandable(false);
        bottomPanel.setLeftComponent(leftPanel);
        bottomPanel.setRightComponent(rightPanel);
        bottomPanel.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                bottomPanel.setDividerLocation(0.5);    // TODO : on resize
            }
        });
    }
}
