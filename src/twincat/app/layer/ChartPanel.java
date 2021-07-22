package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import twincat.Resources;
import twincat.TwincatLogger;
import twincat.Utilities;
import twincat.scope.Chart;
import twincat.scope.Scope;

public class ChartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // TODO : remove tree select parent properties
    // TODO : axis show hide problem (values)
    // TODO : remove trigger not recognized
    // TODO : auto restart channel when new channel
    // TODO : swap start stop button
    // TODO : swap play pause button
    // TODO : chart add axis name design
    // TODO : update static content set display
    // TODO : time marker problem
    // TODO : axis marker problem
    
    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final int DISPLAY_TIME_ZOOM_FACTOR = 100;
    
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Chart chart = new Chart();
    
    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JButton stopButton = new JButton();
    
    private final JLabel displayTime = new JLabel();
    
    private final GraphPanel graphPanel = new GraphPanel();

    private final Logger logger = TwincatLogger.getLogger();
  
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final AncestorListener acncestorListener = new AncestorListener() {
        @Override
        public void ancestorAdded(AncestorEvent event) { 
            chart.start();
            logger.fine("ancestorAdded");
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            chart.stop();
            logger.fine("ancestorRemoved");
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
            /* empty */
        }
    };

    private final ActionListener playPauseActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.togglePlayPause();
        }
    };

    private final ActionListener backwardActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // TODO
        }
    };

    private final ActionListener forwardActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
           // TODO  
        }
    };

    private final ActionListener zoomInActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.setDisplayTime(chart.getDisplayTime() + DISPLAY_TIME_ZOOM_FACTOR);
        }
    };

    private final ActionListener zoomOutActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.setDisplayTime(chart.getDisplayTime() - DISPLAY_TIME_ZOOM_FACTOR);
        }
    };

    private final ActionListener stopActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.toggleStartStop();
        }
    };

    private final ActionListener minimizeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            xref.scopePanel.controlToggle();
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartPanel(XReference xref) {
        this.xref = xref;

        // tool bar
        displayTime.setText(Scope.TIME_FORMAT_MIN_TIME);
        displayTime.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));

        JButton playPauseButton = new JButton();
        playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PLAY_PAUSE)));
        playPauseButton.setFocusable(false);
        playPauseButton.addActionListener(playPauseActionListener);
        
        stopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
        stopButton.setFocusable(false);
        stopButton.addActionListener(stopActionListener);
        
        JButton backwardButton = new JButton();
        backwardButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_BACKWARD)));
        backwardButton.setFocusable(false);
        backwardButton.addActionListener(backwardActionListener);

        JButton forwardButton = new JButton();
        forwardButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_FORWARD)));
        forwardButton.setFocusable(false);
        forwardButton.addActionListener(forwardActionListener);
        
        JButton zoomInButton = new JButton();
        zoomInButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_ZOOM_IN)));
        zoomInButton.setFocusable(false);
        zoomInButton.addActionListener(zoomInActionListener);

        JButton zoomOutButton = new JButton();
        zoomOutButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_ZOOM_OUT)));
        zoomOutButton.setFocusable(false);
        zoomOutButton.addActionListener(zoomOutActionListener); 

        JButton minimizeButton = new JButton();
        minimizeButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_MINIMIZE)));
        minimizeButton.setFocusable(false);
        minimizeButton.addActionListener(minimizeActionListener);

        JToolBar chartToolBar = new JToolBar();
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(false);
        chartToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartToolBar.add(playPauseButton);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(stopButton);
        chartToolBar.addSeparator(new Dimension(30, 0));
        chartToolBar.add(displayTime);
        chartToolBar.addSeparator(new Dimension(30, 0)); 
        chartToolBar.add(backwardButton);
        chartToolBar.addSeparator(new Dimension(5, 0)); 
        chartToolBar.add(forwardButton);      
        chartToolBar.addSeparator(new Dimension(30, 0)); 
        chartToolBar.add(zoomInButton);
        chartToolBar.addSeparator(new Dimension(5, 0)); 
        chartToolBar.add(zoomOutButton); 
        chartToolBar.add(Box.createHorizontalGlue());
        chartToolBar.add(minimizeButton);
        
        // default content
        this.addAncestorListener(acncestorListener);
        this.setLayout(new BorderLayout());
        this.add(chartToolBar, BorderLayout.PAGE_END);
        this.add(graphPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public Chart getChart() {
        return chart;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void hideGraph() {
        graphPanel.setVisible(false);
    }
    
    public void setChart(Chart chart) {
        if (!chart.equals(this.chart)) {          
            this.chart.close();
            this.chart = chart;
            
            graphPanel.setChart(chart);
        }  
    }

    public void load() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                reload();
            }
        });
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void reload()  {
        // reload graph
        
    }
}
