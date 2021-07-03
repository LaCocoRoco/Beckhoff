package twincat.app;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import twincat.TwincatLogger;
import twincat.app.constants.Resources;
import twincat.app.scope.PanelConsole;
import twincat.app.scope.PanelWindow;

public class ScopeFrame extends JPanel {
    private static final long serialVersionUID = 1L;
    
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int DIVIDER_SIZE = 5;
   
    private static final double DIVIDER_LOCATION = 0.8;

    /*************************/
    /*** local attributes ****/
    /*************************/
    
    private final Logger logger = TwincatLogger.getLogger();
    
    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);
    
    private final JMenuBar mainMenu = new JMenuBar();
    
    private final JSplitPane contentPanel = new JSplitPane();

    private final PanelConsole consolePanel = new PanelConsole();

    private final PanelWindow windowPanel = new PanelWindow();
    
    private final JMenuItem menuItemFileNew = new JMenuItem();   
    
    private final JMenuItem menuItemFileOpen = new JMenuItem();

    private final JMenuItem menuItemConsole = new JMenuItem();
    
    private final JMenuItem menuItemSettings = new JMenuItem();
    
    private final JMenuItem menuItemWindowScope = new JMenuItem();
    
    private final JMenuItem menuItemWindowAds = new JMenuItem();
    
    private final JMenuItem menuItemWindowAxis = new JMenuItem();
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeFrame() {
        contentPanel.setLeftComponent(windowPanel);
        contentPanel.setRightComponent(consolePanel);
        contentPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        contentPanel.setContinuousLayout(true);
        contentPanel.setOneTouchExpandable(false);
        contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3));

        menuItemWindowScope.setText(languageBundle.getString(Resources.TEXT_WINDOW_SCOPE));
        menuItemWindowScope.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.setCard(PanelWindow.Card.SCOPE);
            }
        });

        menuItemWindowAds.setText(languageBundle.getString(Resources.TEXT_WINDOW_ADS));
        menuItemWindowAds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.setCard(PanelWindow.Card.ADS);
            }
        });

        menuItemWindowAxis.setText(languageBundle.getString(Resources.TEXT_WINDOW_AXIS));
        menuItemWindowAxis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.setCard(PanelWindow.Card.AXIS);
            }
        });

        menuItemSettings.setText(languageBundle.getString(Resources.TEXT_EXTRAS_SETTINGS));
        menuItemSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.setCard(PanelWindow.Card.SETTINGS);
            }
        });

        menuItemConsole.setText(languageBundle.getString(Resources.TEXT_EXTRAS_CONSOLE));
        menuItemConsole.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                consoleToggle();
            }
        });

        menuItemFileNew.setText(languageBundle.getString(Resources.TEXT_FILE_NEW));
        menuItemFileNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
               // TODO 
            }
        });
        
        menuItemFileOpen.setText(languageBundle.getString(Resources.TEXT_FILE_OPEN));
        menuItemFileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO
            }
        });
        
        JMenu menuFile = new JMenu(languageBundle.getString(Resources.TEXT_FILE));
        menuFile.add(menuItemFileNew);
        menuFile.add(menuItemFileOpen);   
 
        JMenu menuWindow = new JMenu(languageBundle.getString(Resources.TEXT_WINDOW));
        menuWindow.add(menuItemWindowScope);
        menuWindow.add(menuItemWindowAxis);
        
        JMenu menuExtras = new JMenu(languageBundle.getString(Resources.TEXT_EXTRAS));
        menuExtras.add(menuItemSettings);
        menuExtras.add(menuItemConsole);
        menuExtras.add(menuItemWindowAds);

        mainMenu.add(menuFile);
        mainMenu.add(menuWindow);
        mainMenu.add(menuExtras);

        consoleHide();
        
        this.setLayout(new BorderLayout());
        this.add(mainMenu, BorderLayout.PAGE_START);
        this.add(contentPanel);
    }

    /*************************/
    /********* public ********/
    /*************************/
    
    public Level getLoggerLevel() {
        return logger.getLevel();
    }

    public void setLoggerLevel(Level loggerLevel) {
        logger.setLevel(loggerLevel);
    }
  
    public void mainMenuVisible(boolean flag) {
        mainMenu.setVisible(flag);
    }

    public void minifyMenuItems() {
        menuItemConsole.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemConsole.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemConsole.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemConsole.setIcon(null);
        menuItemConsole.setIconTextGap(0);
        menuItemConsole.setMargin(new Insets(0, 0, 0, 0));
        menuItemConsole.setPressedIcon(null);
        
        menuItemSettings.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemSettings.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemSettings.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemSettings.setIcon(null);
        menuItemSettings.setIconTextGap(0);
        menuItemSettings.setMargin(new Insets(0, 0, 0, 0));
        menuItemSettings.setPressedIcon(null);
    
        menuItemWindowScope.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemWindowScope.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemWindowScope.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemWindowScope.setIcon(null);
        menuItemWindowScope.setIconTextGap(0);
        menuItemWindowScope.setMargin(new Insets(0, 0, 0, 0));
        menuItemWindowScope.setPressedIcon(null);
        
        menuItemWindowAds.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemWindowAds.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemWindowAds.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemWindowAds.setIcon(null);
        menuItemWindowAds.setIconTextGap(0);
        menuItemWindowAds.setMargin(new Insets(0, 0, 0, 0));
        menuItemWindowAds.setPressedIcon(null);
        
        menuItemWindowAxis.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemWindowAxis.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemWindowAxis.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemWindowAxis.setIcon(null);
        menuItemWindowAxis.setIconTextGap(0);
        menuItemWindowAxis.setMargin(new Insets(0, 0, 0, 0));
        menuItemWindowAxis.setPressedIcon(null);
    }
    
    /*************************/
    /******** private ********/
    /*************************/

    private void consoleToggle() {
        if (contentPanel.getDividerSize() != 0) {
            consoleHide();
        } else {
            consoleShow();
        }
    }
    
    private void consoleShow() {
        contentPanel.setDividerLocation(DIVIDER_LOCATION);
        contentPanel.getRightComponent().setVisible(true);
        contentPanel.getLeftComponent().setVisible(true);
        contentPanel.setDividerSize(DIVIDER_SIZE);
        contentPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                contentPanel.setDividerLocation(DIVIDER_LOCATION);
            }
        });
    }
    
    private void consoleHide() {
        contentPanel.getRightComponent().setVisible(false);
        contentPanel.getLeftComponent().setVisible(true);
        contentPanel.setDividerSize(0);
    }   
}
