package twincat.app.components;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import twincat.ads.Ads;

public class ScopePanel extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ***/
    /*************************/

    Ads ads = new Ads();

    JScrollPane topPanel = new JScrollPane();
    
    JPanel chartPanel = new JPanel();
    
    JSplitPane bottomPanel = new JSplitPane();
    
    JScrollPane leftPanel = new JScrollPane();
    
    JScrollPane rightPanel = new JScrollPane();
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopePanel() {
        // scope panel
        this.setDividerSize(3);   
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setLeftComponent(topPanel);
        this.setRightComponent(bottomPanel);
        this.setBackground(Color.WHITE);
        this.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                setDividerLocation(0.5);
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setDividerLocation(0.5);
            }
        });
        
        // top panel
        topPanel.setViewportView(chartPanel); 
        
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setAlignmentX(TOP_ALIGNMENT);
        chartPanel.setAlignmentY(CENTER_ALIGNMENT);
        chartPanel.add(new JLabel("Chart Panel"));

        // bottom panel
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setDividerSize(3);   
        bottomPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        bottomPanel.setContinuousLayout(true);
        bottomPanel.setOneTouchExpandable(false);
        bottomPanel.setLeftComponent(leftPanel);
        bottomPanel.setRightComponent(rightPanel);
        
        // test left
        JLabel leftTestLabel = new JLabel("Left Panel");
        JPanel leftTestPanel = new JPanel();
        leftTestPanel.setBackground(Color.WHITE);
        leftTestPanel.add(leftTestLabel);
        
        leftPanel.setViewportView(leftTestPanel);
        
        // test right
        JLabel rightTestLabel = new JLabel("Right Panel");
        JPanel rightTsetPanel = new JPanel();
        rightTsetPanel.setBackground(Color.WHITE);
        rightTsetPanel.add(rightTestLabel);
        
        rightPanel.setViewportView(rightTsetPanel);
        
        bottomPanel.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                bottomPanel.setDividerLocation(0.5);
            }
        });
        
        bottomPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                bottomPanel.setDividerLocation(0.5);
            }
        });
    }
}
