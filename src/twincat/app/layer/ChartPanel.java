package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import twincat.Resources;
import twincat.Utilities;
import twincat.scope.Chart;
import twincat.scope.Scope;

public class ChartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private Chart chart = new Chart();

    private  final JLabel textHeader = new JLabel();
 
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ActionListener playActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.play();
        }
    };

    private final ActionListener pauseActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.pause();
        }
    };

    private final ActionListener stopActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chart.stop();
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
        
        // graph panel
        JPanel graphPanel = new JPanel();
        graphPanel.setBorder(BorderFactory.createEmptyBorder());
        
        // tool bar
        JLabel displayTime = new JLabel(Scope.TIME_FORMAT_MIN_TIME);
        displayTime.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));

        JButton playButton = new JButton();
        playButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PLAY)));
        playButton.setFocusable(false);
        playButton.addActionListener(playActionListener);

        JButton pauseButton = new JButton();
        pauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PAUSE)));
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(pauseActionListener);

        JButton stopButton = new JButton();
        stopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
        stopButton.setFocusable(false);
        stopButton.addActionListener(stopActionListener);

        JButton minimizeButton = new JButton();
        minimizeButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_MINIMIZE)));
        minimizeButton.setFocusable(false);
        minimizeButton.addActionListener(minimizeActionListener);

        JToolBar chartToolBar = new JToolBar();
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(false);
        chartToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartToolBar.add(playButton);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(pauseButton);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(stopButton);
        chartToolBar.addSeparator(new Dimension(15, 0));
        chartToolBar.add(displayTime);
        chartToolBar.add(Box.createHorizontalGlue());
        chartToolBar.add(minimizeButton);
        
        // default content
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

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void reloadChart()  {
        textHeader.setText(Long.toString(chart.getDisplayTime()));
    }
}
