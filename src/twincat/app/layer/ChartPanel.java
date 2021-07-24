package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

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
import twincat.Utilities;
import twincat.scope.Chart;
import twincat.scope.Scope;

public class ChartPanel extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;

    // TODO : delete on close
    // TODO : take over data from channel to acquisition
    // TODO : disable reload on node select (setter)
    // TODO : on exit close everything
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

    private final int DISPLAY_MOVE_RANGE = 100;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Chart chart = new Chart();

    /*********************************/
    /****** local final variable *****/
    /*********************************/
    
    private final JButton startStopButton = new JButton();

    private final JButton playPauseButton = new JButton();

    private final JLabel recordTime = new JLabel();
  
    private final JLabel displayTime = new JLabel();

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final AncestorListener acncestorListener = new AncestorListener() {
        @Override
        public void ancestorAdded(AncestorEvent event) {
            chartStart();
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            chartStop();
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
            /* empty */
        }
    };

    private final ActionListener playPauseActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (chart.isPaused()) {
                chartPlay();               
            } else {
                chartPause();
            }
        }
    };
    
    private final ActionListener startStopActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (chart.isStoped()) {
                chartStop();
                chartPause();
            } else {
                chartStart();
                chartPlay();                 
            }
        }
    };

    private final ActionListener backwardActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.backward(DISPLAY_MOVE_RANGE);
        }
    };

    private final ActionListener forwardActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.forward(DISPLAY_MOVE_RANGE);
        }
    };

    private final ActionListener zoomInActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.setDisplayTime(chart.getDisplayTime() - DISPLAY_TIME_ZOOM_FACTOR);
        }
    };

    private final ActionListener zoomOutActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.setDisplayTime(chart.getDisplayTime() + DISPLAY_TIME_ZOOM_FACTOR);
        }
    };

    private final ActionListener minimizeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            xref.scopePanel.controlToggle();
        }
    };

    private final ComponentAdapter componentListener = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent componentEvent) {
            Component component = componentEvent.getComponent();
            chart.setWidth(component.getWidth());
            chart.setHeight(component.getHeight());
        }
    };
    
    private final JPanel graphPanel = new JPanel() {
        private static final long serialVersionUID = 1L;
        
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(chart.getImage(), 0, 0, this);
        }
    };
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartPanel(XReference xref) {
        this.xref = xref;

        // graph panel
        graphPanel.addComponentListener(componentListener);
        graphPanel.setBorder(BorderFactory.createEmptyBorder());

        // tool bar
        startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
        startStopButton.setFocusable(false);
        startStopButton.addActionListener(startStopActionListener);     

        recordTime.setText(Scope.TIME_FORMAT_MIN_TIME);
        recordTime.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        
        playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PAUSE)));
        playPauseButton.setFocusable(false);
        playPauseButton.addActionListener(playPauseActionListener);

        displayTime.setText(Scope.TIME_FORMAT_MIN_TIME);
        displayTime.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
 
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
        
        chartToolBar.add(startStopButton);
        chartToolBar.addSeparator(new Dimension(20, 0));
        chartToolBar.add(recordTime);
        chartToolBar.addSeparator(new Dimension(20, 0));
        chartToolBar.add(playPauseButton);
        chartToolBar.addSeparator(new Dimension(20, 0));
        chartToolBar.add(displayTime);
        chartToolBar.addSeparator(new Dimension(20, 0));
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
    /******** override method ********/
    /*********************************/

    @Override
    public void update(Observable observable, Object object) {
        String displayTimeText = Scope.timeFormaterToString(chart.getCurrentDisplayTime());
        displayTime.setText(displayTimeText);
        
        String recordTimeText = Scope.timeFormaterToString(chart.getCurrentRecordTime());
        recordTime.setText(recordTimeText);
        
        graphPanel.repaint();
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
            
            this.chart.addObserver(this);
            this.chart.setWidth(this.getWidth());
            this.chart.setHeight(this.getHeight());
            this.chart.start();
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

    private void chartStart() {
        chart.start();
        playPauseButton.setEnabled(true);
        startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
    }
    
    private void chartStop()  {
        chart.stop();
        playPauseButton.setEnabled(false);
        startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_START)));
    }
 
    private void chartPlay() {
        chart.play();
        playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PAUSE)));
    }
    
    private void chartPause() {
        chart.pause();
        playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PLAY))); 
    }

    private void reload() {
        // reload start stop state
        if (chart.isStoped()) {
            playPauseButton.setEnabled(true);
            startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
        } else {
            playPauseButton.setEnabled(false);
            startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_START)));
        }
        
        // reload play pause state
        if (chart.isPaused()) {
            playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PLAY))); 
        } else {
            playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PAUSE))); 
        }
    }
}
