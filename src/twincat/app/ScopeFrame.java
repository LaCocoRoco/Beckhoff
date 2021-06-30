package twincat.app;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import twincat.app.components.ConsolePanel;
import twincat.app.components.WindowPanel;

public class ScopeFrame extends JPanel {
    private static final long serialVersionUID = 1L;
    
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int DIVIDER_SIZE_OPEN = 3;
    
    private static final int DIVIDER_SIZE_CLOSED = 0;
    
    private static final double DIVIDER_LOCATION = 0.7;
    
    private static final String MENU_ON = "On";
    
    private static final String MENU_OFF = "Off";
    
    /*************************/
    /*** local attributes ****/
    /*************************/

    private final JMenuBar mainMenu = new JMenuBar();
    
    private final JSplitPane contentPanel = new JSplitPane();

    private final ConsolePanel consolePanel = new ConsolePanel();

    private final WindowPanel windowPanel = new WindowPanel();
    
    private final JMenuItem menuConsoleOn = new JMenuItem(MENU_ON);
    
    private final JMenuItem menuConsoleOff = new JMenuItem(MENU_OFF);
    
    private final JMenuItem menuScope = new JMenuItem(WindowPanel.WINDOW_SCOPE);
    
    private final JMenuItem menuAds = new JMenuItem(WindowPanel.WINDOW_ADS_INFO);
    
    private final JMenuItem menuAxxa = new JMenuItem(WindowPanel.WINDOW_AXIS);
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeFrame() {
        // TODO : overlap problem
        JScrollPane overlapPanel = new JScrollPane();
        overlapPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        overlapPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        overlapPanel.setViewportView(windowPanel);

        // content panel
        contentPanel.setLeftComponent(overlapPanel);
        contentPanel.setRightComponent(consolePanel);
        contentPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        contentPanel.setContinuousLayout(true);
        contentPanel.setOneTouchExpandable(false);
        contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
        
        contentPanel.getRightComponent().setVisible(false);
        contentPanel.getLeftComponent().setVisible(true);
        contentPanel.setDividerSize(DIVIDER_SIZE_CLOSED);

        menuConsoleOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                contentPanel.getRightComponent().setVisible(true);
                contentPanel.getLeftComponent().setVisible(true);
                contentPanel.setDividerSize(DIVIDER_SIZE_OPEN);
                contentPanel.setDividerLocation(DIVIDER_LOCATION);
                contentPanel.addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent componentEvent) {
                        contentPanel.setDividerLocation(DIVIDER_LOCATION);
                    }
                });
            }
        });


        menuConsoleOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                contentPanel.getRightComponent().setVisible(false);
                contentPanel.getLeftComponent().setVisible(true);
                contentPanel.setDividerSize(DIVIDER_SIZE_CLOSED);
            }
        });

        menuScope.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.displayWindow(WindowPanel.WINDOW_SCOPE);
            }
        });


        menuAds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.displayWindow(WindowPanel.WINDOW_ADS_INFO);
            }
        });

        menuAxxa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.displayWindow(WindowPanel.WINDOW_AXIS);
            }
        });

        JMenu menuConsole = new JMenu("Console");
        menuConsole.add(menuConsoleOn);
        menuConsole.add(menuConsoleOff);

        JMenu menuWindow = new JMenu("Window");
        menuWindow.add(menuScope);
        menuWindow.add(menuAxxa);
        menuWindow.add(menuAds);

        mainMenu.add(menuWindow);
        mainMenu.add(menuConsole);

        this.setLayout(new BorderLayout());
        this.add(mainMenu, BorderLayout.PAGE_START);
        this.add(contentPanel);
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public void mainMenuVisible(boolean flag) {
        mainMenu.setVisible(flag);
    }

    public void reverseMenuItems() {
        menuConsoleOn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuConsoleOn.setHorizontalAlignment(JMenuItem.LEFT);
        menuConsoleOn.setHorizontalTextPosition(JMenuItem.LEFT);
        menuConsoleOn.setIcon(null);
        menuConsoleOn.setIconTextGap(0);
        menuConsoleOn.setMargin(new Insets(0, 0, 0, 0));
        menuConsoleOn.setPressedIcon(null);
        
        menuConsoleOff.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuConsoleOff.setHorizontalAlignment(JMenuItem.LEFT);
        menuConsoleOff.setHorizontalTextPosition(JMenuItem.LEFT);
        menuConsoleOff.setIcon(null);
        menuConsoleOff.setIconTextGap(0);
        menuConsoleOff.setMargin(new Insets(0, 0, 0, 0));
        menuConsoleOff.setPressedIcon(null);
    
        menuScope.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuScope.setHorizontalAlignment(JMenuItem.LEFT);
        menuScope.setHorizontalTextPosition(JMenuItem.LEFT);
        menuScope.setIcon(null);
        menuScope.setIconTextGap(0);
        menuScope.setMargin(new Insets(0, 0, 0, 0));
        menuScope.setPressedIcon(null);
        
        menuAds.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuAds.setHorizontalAlignment(JMenuItem.LEFT);
        menuAds.setHorizontalTextPosition(JMenuItem.LEFT);
        menuAds.setIcon(null);
        menuAds.setIconTextGap(0);
        menuAds.setMargin(new Insets(0, 0, 0, 0));
        menuAds.setPressedIcon(null);
        
        menuAxxa.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuAxxa.setHorizontalAlignment(JMenuItem.LEFT);
        menuAxxa.setHorizontalTextPosition(JMenuItem.LEFT);
        menuAxxa.setIcon(null);
        menuAxxa.setIconTextGap(0);
        menuAxxa.setMargin(new Insets(0, 0, 0, 0));
        menuAxxa.setPressedIcon(null);
    }
}
