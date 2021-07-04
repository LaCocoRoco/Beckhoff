package twincat.app.scope;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import twincat.Utilities;
import twincat.app.constants.Resources;

public class PanelChart extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** global attributes ***/
    /*************************/
  
    private final PanelScope panelScope;
   
    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);  

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public PanelChart(PanelScope panelScope) {
        this.panelScope = panelScope;
        
        JScrollPane graphPanel = new JScrollPane();
        graphPanel.setViewportView(new LoremIpsum());
        graphPanel.setBorder(BorderFactory.createEmptyBorder()); 
        graphPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));

        
        
        JButton buttonChartPlay = new JButton();
        buttonChartPlay.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_PLAY));
        buttonChartPlay.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_PLAY)));
        buttonChartPlay.setFocusable(false);
  
        JButton buttonChartPause = new JButton();
        buttonChartPause.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_PAUSE));
        buttonChartPause.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_PAUSE)));
        buttonChartPause.setFocusable(false);
        
        JButton buttonChartStop = new JButton();
        buttonChartStop.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_STOP));
        buttonChartStop.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_STOP)));
        buttonChartStop.setFocusable(false);
  
        JToolBar chartToolBar = new JToolBar();
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(false);
        chartToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartToolBar.add(buttonChartPlay);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(buttonChartPause);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(buttonChartStop);
        
        
        
        this.setLayout(new BorderLayout());
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(chartToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());        
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/   
    
    public PanelScope getPanelScope() {
        return panelScope;
    }   
}
