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
import twincat.app.scope.ConsolePanel;
import twincat.app.scope.WindowPanel;

public class ScopeFrame extends JPanel {
    private static final long serialVersionUID = 1L;
    
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int DIVIDER_SIZE = 5;
   
    private static final int DIVIDER_LOCATION = 150;

    /*************************/
    /*** local attributes ****/
    /*************************/
    
    private final Logger logger = TwincatLogger.getLogger();
    
    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);
    
    private final JMenuBar mainMenu = new JMenuBar();
    
    private final JSplitPane contentPanel = new JSplitPane();

    private final ConsolePanel consolePanel = new ConsolePanel();

    private final WindowPanel windowPanel = new WindowPanel();
    
    private final JMenuItem menuItemFileNew = new JMenuItem();   
    
    private final JMenuItem menuItemFileOpen = new JMenuItem();

    private final JMenuItem menuItemConsoleOn = new JMenuItem();
    
    private final JMenuItem menuItemConsoleOff = new JMenuItem();
    
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
                windowPanel.setCard(WindowPanel.Card.SCOPE);
            }
        });

        menuItemWindowAds.setText(languageBundle.getString(Resources.TEXT_WINDOW_ADS));
        menuItemWindowAds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.setCard(WindowPanel.Card.ADS);
            }
        });

        menuItemWindowAxis.setText(languageBundle.getString(Resources.TEXT_WINDOW_AXIS));
        menuItemWindowAxis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.setCard(WindowPanel.Card.AXIS);
            }
        });

        menuItemConsoleOn.setText(languageBundle.getString(Resources.TEXT_CONSOLE_ON));
        menuItemConsoleOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                consoleShow();
            }
        });

        menuItemConsoleOff.setText(languageBundle.getString(Resources.TEXT_CONSOLE_OFF));
        menuItemConsoleOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                consoleHide();
            }
        });

        menuItemFileNew.setText(languageBundle.getString(Resources.TEXT_FILE_NEW));
        menuItemFileOpen.setText(languageBundle.getString(Resources.TEXT_FILE_OPEN));

        JMenu menuFile = new JMenu(languageBundle.getString(Resources.TEXT_FILE_OPEN));
        menuFile.add(menuItemFileNew);
        menuFile.add(menuItemFileOpen);   
 
        JMenu menuWindow = new JMenu(languageBundle.getString(Resources.TEXT_WINDOW));
        menuWindow.add(menuItemWindowScope);
        menuWindow.add(menuItemWindowAxis);
        menuWindow.add(menuItemWindowAds);
        
        JMenu menuConsole = new JMenu(languageBundle.getString(Resources.TEXT_CONSOLE));
        menuConsole.add(menuItemConsoleOn);
        menuConsole.add(menuItemConsoleOff);

        mainMenu.add(menuFile);
        mainMenu.add(menuWindow);
        mainMenu.add(menuConsole);

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
        menuItemConsoleOn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemConsoleOn.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemConsoleOn.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemConsoleOn.setIcon(null);
        menuItemConsoleOn.setIconTextGap(0);
        menuItemConsoleOn.setMargin(new Insets(0, 0, 0, 0));
        menuItemConsoleOn.setPressedIcon(null);
        
        menuItemConsoleOff.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemConsoleOff.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemConsoleOff.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemConsoleOff.setIcon(null);
        menuItemConsoleOff.setIconTextGap(0);
        menuItemConsoleOff.setMargin(new Insets(0, 0, 0, 0));
        menuItemConsoleOff.setPressedIcon(null);
    
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

    private void consoleShow() {
        int dividerLocation = contentPanel.getHeight() - DIVIDER_LOCATION;
        contentPanel.setDividerLocation(dividerLocation);
        contentPanel.getRightComponent().setVisible(true);
        contentPanel.getLeftComponent().setVisible(true);
        contentPanel.setDividerSize(DIVIDER_SIZE);
        contentPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int dividerLocation = contentPanel.getHeight() - DIVIDER_LOCATION;
                contentPanel.setDividerLocation(dividerLocation);
            }
        });
    }
    
    private void consoleHide() {
        contentPanel.getRightComponent().setVisible(false);
        contentPanel.getLeftComponent().setVisible(true);
        contentPanel.setDividerSize(0);
    }   
}
