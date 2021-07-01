package twincat.app;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import twincat.TwincatLogger;
import twincat.app.components.ConsolePanel;
import twincat.app.components.WindowPanel;

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
    
    private final ResourceBundle textBundle = ResourceBundle.getBundle("resources/string/text");
    
    private final JMenuBar mainMenu = new JMenuBar();
    
    private final JSplitPane contentPanel = new JSplitPane();

    private final ConsolePanel consolePanel = new ConsolePanel();

    private final WindowPanel windowPanel = new WindowPanel();
    
    private final JMenuItem menuItemConsoleOn = new JMenuItem();
    
    private final JMenuItem menuItemConsoleOff = new JMenuItem();
    
    private final JMenuItem menuItemScope = new JMenuItem();
    
    private final JMenuItem menuItemAds = new JMenuItem();
    
    private final JMenuItem menuItemAxis = new JMenuItem();
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeFrame() {
        logger.setLevel(TwincatLogger.LOGGER_DEFAULT_LEVEL);

        contentPanel.setLeftComponent(windowPanel);
        contentPanel.setRightComponent(consolePanel);
        contentPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        contentPanel.setContinuousLayout(true);
        contentPanel.setOneTouchExpandable(false);
        contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3));

        menuItemConsoleOn.setText(textBundle.getString("menuItemConsoleOn"));
        menuItemConsoleOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                consoleShow();
            }
        });

        menuItemConsoleOff.setText(textBundle.getString("menuItemConsoleOff"));
        menuItemConsoleOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                consoleHide();
            }
        });

        menuItemScope.setText(textBundle.getString("menuItemScope"));
        menuItemScope.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.displayWindow(WindowPanel.Card.SCOPE.toString());
            }
        });

        menuItemAds.setText(textBundle.getString("menuItemAds"));
        menuItemAds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.displayWindow(WindowPanel.Card.ADS.toString());
            }
        });

        menuItemAxis.setText(textBundle.getString("menuItemAxis"));
        menuItemAxis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                windowPanel.displayWindow(WindowPanel.Card.AXIS.toString());
            }
        });

        JMenu menuConsole = new JMenu(textBundle.getString("menuConsole"));
        menuConsole.add(menuItemConsoleOn);
        menuConsole.add(menuItemConsoleOff);

        JMenu menuWindow = new JMenu(textBundle.getString("menuWindow"));
        menuWindow.add(menuItemScope);
        menuWindow.add(menuItemAxis);
        menuWindow.add(menuItemAds);

        mainMenu.add(menuWindow);
        mainMenu.add(menuConsole);

        consoleShow();
        
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
    
        menuItemScope.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemScope.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemScope.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemScope.setIcon(null);
        menuItemScope.setIconTextGap(0);
        menuItemScope.setMargin(new Insets(0, 0, 0, 0));
        menuItemScope.setPressedIcon(null);
        
        menuItemAds.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemAds.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemAds.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemAds.setIcon(null);
        menuItemAds.setIconTextGap(0);
        menuItemAds.setMargin(new Insets(0, 0, 0, 0));
        menuItemAds.setPressedIcon(null);
        
        menuItemAxis.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuItemAxis.setHorizontalAlignment(JMenuItem.LEFT);
        menuItemAxis.setHorizontalTextPosition(JMenuItem.LEFT);
        menuItemAxis.setIcon(null);
        menuItemAxis.setIconTextGap(0);
        menuItemAxis.setMargin(new Insets(0, 0, 0, 0));
        menuItemAxis.setPressedIcon(null);
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
