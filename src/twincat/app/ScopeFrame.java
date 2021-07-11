package twincat.app;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import twincat.Resources;
import twincat.TwincatLogger;
import twincat.app.component.PanelContent;
import twincat.app.component.PanelWindow;
import twincat.app.constant.Window;

public class ScopeFrame extends JPanel {
    private static final long serialVersionUID = 1L;
 
    /*************************/
    /*** local attributes ****/
    /*************************/
    
    private final PanelWindow panelWindow;

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);
    
    private final JMenuBar scopeMenu = new JMenuBar();

    private final PanelContent panelContent = new PanelContent();

    private final JMenuItem menuItemFileNew = new JMenuItem();   
    
    private final JMenuItem menuItemFileOpen = new JMenuItem();

    private final JMenuItem menuItemConsole = new JMenuItem();
    
    private final JMenuItem menuItemSettings = new JMenuItem();
    
    private final JMenuItem menuItemWindowScope = new JMenuItem();
    
    private final JMenuItem menuItemWindowAds = new JMenuItem();
    
    private final JMenuItem menuItemWindowAxis = new JMenuItem();
    
    private final Logger logger = TwincatLogger.getLogger();
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeFrame() {
        panelWindow = panelContent.getPanelWindow();
        
        menuItemWindowScope.setText(languageBundle.getString(Resources.TEXT_WINDOW_SCOPE));
        menuItemWindowScope.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                panelWindow.setCard(Window.SCOPE);
            }
        });

        menuItemWindowAds.setText(languageBundle.getString(Resources.TEXT_WINDOW_ADS));
        menuItemWindowAds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                panelWindow.setCard(Window.ADS);
            }
        });

        menuItemWindowAxis.setText(languageBundle.getString(Resources.TEXT_WINDOW_AXIS));
        menuItemWindowAxis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                panelWindow.setCard(Window.AXIS);
            }
        });

        menuItemSettings.setText(languageBundle.getString(Resources.TEXT_EXTRAS_SETTINGS));
        menuItemSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                panelWindow.setCard(Window.SETTINGS);
            }
        });

        menuItemConsole.setText(languageBundle.getString(Resources.TEXT_EXTRAS_CONSOLE));
        menuItemConsole.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                panelContent.consoleToggle();
            }
        });

        menuItemFileNew.setText(languageBundle.getString(Resources.TEXT_FILE_NEW));
        menuItemFileNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
               // TODO : menu new file
            }
        });
        
        menuItemFileOpen.setText(languageBundle.getString(Resources.TEXT_FILE_OPEN));
        menuItemFileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO : menu open file
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

        scopeMenu.add(menuFile);
        scopeMenu.add(menuWindow);
        scopeMenu.add(menuExtras);
     
        panelContent.consoleHide();
        
        this.setLayout(new BorderLayout());
        this.add(scopeMenu, BorderLayout.PAGE_START);
        this.add(panelContent, BorderLayout.CENTER);
    }

    /*************************/
    /********* public ********/
    /*************************/
    
    public Level getLoggerLevel() {
        return logger.getLevel();
    }

    public void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
  
    public void setMenuVisible(boolean flag) {
        scopeMenu.setVisible(flag);
    }

    public void setConsoleVisible(boolean flag) {
        if (flag) {
            panelContent.consoleShow();   
        } else {
            panelContent.consoleHide();
        }
    }
    
    public void setWindow(Window card) {
        panelWindow.setCard(card);
    }
    
    public void minifyMenuItems() {
        menuItemFileNew.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemFileNew.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemFileNew.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemFileNew.setIcon(null);
        menuItemFileNew.setIconTextGap(0);
        menuItemFileNew.setMargin(new Insets(0, 0, 0, 0));
        menuItemFileNew.setPressedIcon(null);
        
        menuItemFileOpen.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemFileOpen.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemFileOpen.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemFileOpen.setIcon(null);
        menuItemFileOpen.setIconTextGap(0);
        menuItemFileOpen.setMargin(new Insets(0, 0, 0, 0));
        menuItemFileOpen.setPressedIcon(null);
        
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
}
