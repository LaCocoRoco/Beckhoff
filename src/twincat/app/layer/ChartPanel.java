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

    private final JToolBar toolBar = new JToolBar();
    
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final AncestorListener acncestorListener = new AncestorListener() {
        @Override
        public void ancestorAdded(AncestorEvent event) {
            chart.start();
            updateStartStopButton();

        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            chart.stop();
            updateStartStopButton();
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
            /* empty */
        }
    };

    private final ActionListener playPauseActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            togglePlayPause();
            updatePlayPauseButton();
        }
    };
    
    private final ActionListener startStopActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            toggleStartStop();
            updateStartStopButton();
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
        graphPanel.setVisible(false);

        // tool bar
        startStopButton.setFocusable(false);
        startStopButton.addActionListener(startStopActionListener);     
        
        playPauseButton.setFocusable(false);
        playPauseButton.addActionListener(playPauseActionListener);
        
        updateStartStopButton();
        updatePlayPauseButton();
        
        recordTime.setText(Scope.TIME_FORMAT_MIN_TIME);
        recordTime.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        
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

        toolBar.setFloatable(false);
        toolBar.setRollover(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        toolBar.add(startStopButton);
        toolBar.addSeparator(new Dimension(20, 0));
        toolBar.add(recordTime);
        toolBar.addSeparator(new Dimension(20, 0));
        toolBar.add(playPauseButton);
        toolBar.addSeparator(new Dimension(20, 0));
        toolBar.add(displayTime);
        toolBar.addSeparator(new Dimension(20, 0));
        toolBar.add(backwardButton);
        toolBar.addSeparator(new Dimension(5, 0));
        toolBar.add(forwardButton);
        toolBar.addSeparator(new Dimension(30, 0));
        toolBar.add(zoomInButton);
        toolBar.addSeparator(new Dimension(5, 0));
        toolBar.add(zoomOutButton);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(minimizeButton);

        // default content
        this.addAncestorListener(acncestorListener);
        this.setLayout(new BorderLayout());
        this.add(toolBar, BorderLayout.PAGE_END);
        this.add(graphPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        if (!chart.equals(this.chart)) {
            int width = graphPanel.getWidth() > 0 ? graphPanel.getWidth() : this.getWidth();
            int height = graphPanel.getHeight() > 0 ? graphPanel.getHeight() : this.getHeight() - toolBar.getHeight();
            int refreshRate = xref.settingsPanel.getRefreshRate();
            boolean debugEnabled = xref.settingsPanel.isDebugEnabled();
              
            this.chart.deleteObserver(this);
            this.chart.close();
            this.chart = chart;
            this.chart.addObserver(this);
            this.chart.setWidth(width);
            this.chart.setHeight(height);
            this.chart.setRefreshRate(refreshRate);
            this.chart.setDebug(debugEnabled);
            this.chart.start();
        }
    }

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public void update(Observable observable, Object object) {
        // update display time
        String displayTimeText = Scope.timeFormaterToString(chart.getCurrentDisplayTime());
        displayTime.setText(displayTimeText);
        
        // update record time
        String recordTimeText = Scope.timeFormaterToString(chart.getCurrentRecordTime());
        recordTime.setText(recordTimeText);

        // update start stop button
        updateStartStopButton();
        
        // update play pause button
        updatePlayPauseButton();
        
        // update graph
        graphPanel.repaint();
    }   
    
    /*********************************/
    /********* public method *********/
    /*********************************/

    public void resetChart() {
        // reset chart
        this.chart = new Chart();
        
        // hide graph
        graphPanel.setVisible(false);
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

    private void togglePlayPause() {
        if (chart.isPaused()) {
            chart.play();               
        } else {
            chart.pause();
        }
    }
    
    private void toggleStartStop() {
        if (chart.isRecording()) {
            chart.stop();
            chart.pause();
        } else {
            chart.start();
            chart.play();               
        }
    }

    private void updateStartStopButton() {
        if (chart.isRecording()) {
            playPauseButton.setEnabled(true);
            startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
        } else {
            playPauseButton.setEnabled(false);
            startStopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_START)));
        }
    }
    
    private void updatePlayPauseButton() {
        if (chart.isPaused()) {
            playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PLAY))); 
        } else {
            playPauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PAUSE))); 
        }     
    }
    
    private void reload() {
        // show graph
        graphPanel.setVisible(true);
        
        // update start stop button
        updateStartStopButton();
        
        // update play pause button
        updatePlayPauseButton();
    }
}
