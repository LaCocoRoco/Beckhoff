package twincat.app.scope;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import twincat.Utilities;
import twincat.app.constants.Resources;

public class PanelBrowser extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);  
        
    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelBrowser() {
        JScrollPane browserPanel = new JScrollPane();
        browserPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        browserPanel.setBorder(BorderFactory.createEmptyBorder()); 
        browserPanel.setViewportView(new LoremIpsum());
        
        JButton browserButtonAddScope = new JButton();
        browserButtonAddScope.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_SCOPE));
        browserButtonAddScope.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_SCOPE)));
        browserButtonAddScope.setFocusable(false);

        JButton browserButtonAddChart = new JButton();
        browserButtonAddChart.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_CHART));
        browserButtonAddChart.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CHART)));
        browserButtonAddChart.setFocusable(false);
  
        JButton browserButtonAddAxis = new JButton();
        browserButtonAddAxis.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_AXIS));
        browserButtonAddAxis.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_AXIS)));
        browserButtonAddAxis.setFocusable(false);
        
        JButton browserButtonAddChannel = new JButton();
        browserButtonAddChannel.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_CHANNEL));
        browserButtonAddChannel.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CHANNEL)));
        browserButtonAddChannel.setFocusable(false);

        JButton browserButtonSearch = new JButton();
        browserButtonSearch.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_SEARCH));
        browserButtonSearch.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_SEARCH)));
        browserButtonSearch.setFocusable(false);
        browserButtonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });     
        
        JButton browserButtonDelete = new JButton();
        browserButtonDelete.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_DELETE));
        browserButtonDelete.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_DELETE)));
        browserButtonDelete.setFocusable(false);
 
        JToolBar browserToolBar = new JToolBar();
        browserToolBar.setFloatable(false);
        browserToolBar.setRollover(false);
        browserToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        browserToolBar.add(browserButtonAddScope);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(browserButtonAddChart);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(browserButtonAddAxis);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(browserButtonAddChannel);
        browserToolBar.addSeparator(new Dimension(30, 0));
        browserToolBar.add(browserButtonSearch);
        browserToolBar.addSeparator(new Dimension(30, 0));
        browserToolBar.add(browserButtonDelete);

        this.setLayout(new BorderLayout());
        this.add(browserPanel, BorderLayout.CENTER);
        this.add(browserToolBar, BorderLayout.PAGE_START);
        this.setBorder(BorderFactory.createEmptyBorder()); 
    }
}
