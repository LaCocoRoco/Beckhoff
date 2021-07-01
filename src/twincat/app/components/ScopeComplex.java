package twincat.app.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import twincat.Utilities;

// TODO : split into panel class

public class ScopeComplex extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    private static final int DIVIDER_SIZE = 5;

    private static final double DIVIDER_LOCATION_BOTTOM = 0.5;

    private static final double DIVIDER_LOCATION_TOP = 0.5;

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final JPanel topPanel = new JPanel();

    private final JSplitPane bottomPanel = new JSplitPane();

    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeComplex() {
        this.setLeftComponent(topPanel);
        this.setRightComponent(bottomPanel);
        this.setDividerSize(DIVIDER_SIZE);
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setBackground(Color.WHITE);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setDividerLocation(DIVIDER_LOCATION_TOP);
            }
        });

        /**************************************/
        
        // TODO : chart panel
        JScrollPane chartPanel = new JScrollPane();
        chartPanel.setViewportView(new LoremIpsumText());
        
        JButton button = new JButton();
        button.setToolTipText("Play/Pause");
        button.setIcon(new ImageIcon(Utilities.getImageFromFilePath("/resources/images/play_pause.png")));
        button.setFocusable(false);
        /*
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
        button.setMargin(new Insets(50, 50, 50, 50));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        */

        JToolBar chartToolBar = new JToolBar();
        chartToolBar.add(button);
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(false);
        chartToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // top panel
        topPanel.setLayout(new BorderLayout());
        topPanel.add(chartPanel, BorderLayout.CENTER);
        topPanel.add(chartToolBar, BorderLayout.PAGE_END);
        topPanel.setBorder(BorderFactory.createEmptyBorder());
        
        /**************************************/
        
        // TODO: browser panel
        JScrollPane leftPanel = new JScrollPane();
        leftPanel.setViewportView(new LoremIpsumText());
        
        // TODO : settings panel
        JScrollPane rightPanel = new JScrollPane();
        rightPanel.setViewportView(new LoremIpsumText());
        
        // bottom panel
        bottomPanel.setLeftComponent(leftPanel);
        bottomPanel.setRightComponent(rightPanel);
        bottomPanel.setDividerSize(DIVIDER_SIZE);
        bottomPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        bottomPanel.setContinuousLayout(true);
        bottomPanel.setOneTouchExpandable(false);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder());
        bottomPanel.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                bottomPanel.setDividerLocation(DIVIDER_LOCATION_BOTTOM);
            }
        });
        bottomPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                bottomPanel.setDividerLocation(DIVIDER_LOCATION_BOTTOM);
            }
        });
    }
}
