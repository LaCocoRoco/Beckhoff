package twincat.app.scope;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import twincat.LoremIpsum;
import twincat.Utilities;
import twincat.app.constants.Resources;

public class ChartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);  
    
    /*************************/
    /****** constructor ******/
    /*************************/
    
    public ChartPanel() {
        JScrollPane graphPanel = new JScrollPane();
        graphPanel.setViewportView(new LoremIpsum());

        JButton buttonChartPlayPause = new JButton();
        buttonChartPlayPause.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_PLAY_PAUSE));
        buttonChartPlayPause.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_PLAY_PAUSE)));
        buttonChartPlayPause.setFocusable(false);
  
        JToolBar chartToolBar = new JToolBar();
        chartToolBar.add(buttonChartPlayPause);
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(false);
        chartToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.setLayout(new BorderLayout());
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(chartToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());        
    } 
}
