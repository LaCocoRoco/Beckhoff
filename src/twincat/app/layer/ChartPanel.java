package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import twincat.Resources;
import twincat.Utilities;
import twincat.scope.Chart;

public class ChartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private Chart chart = new Chart();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

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

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartPanel(XReference xref) {
        JButton playButton = new JButton();
        playButton.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_PLAY));
        playButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PLAY)));
        playButton.setFocusable(false);
        playButton.addActionListener(playActionListener);

        JButton pauseButton = new JButton();
        pauseButton.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_PAUSE));
        pauseButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_PAUSE)));
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(pauseActionListener);

        JButton stopButton = new JButton();
        stopButton.setToolTipText(languageBundle.getString(Resources.TEXT_CHART_STOP));
        stopButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_CONTROL_STOP)));
        stopButton.setFocusable(false);
        stopButton.addActionListener(stopActionListener);

        JToolBar chartToolBar = new JToolBar();
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(false);
        chartToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartToolBar.add(playButton);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(pauseButton);
        chartToolBar.addSeparator(new Dimension(5, 0));
        chartToolBar.add(stopButton);

        this.setLayout(new BorderLayout());
        this.add(chartToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    // TODO : update data on set chart
    
    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }
    
    
}
