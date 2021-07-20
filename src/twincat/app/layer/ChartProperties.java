package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.NumberTextField;
import twincat.app.components.TextField;
import twincat.app.components.TimeTextField;
import twincat.app.components.TitleBorder;
import twincat.app.constant.Propertie;
import twincat.java.ScrollablePanel;
import twincat.java.WrapTopLayout;
import twincat.scope.Chart;
import twincat.scope.Scope;

public class ChartProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Chart chart = new Chart();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final TextField chartNameTextField = new TextField();

    private final TimeTextField displayTimeTextField = new TimeTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final PropertyChangeListener borderColorChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();
            chart.setBorderColor(component.getBackground());
        }
    };

    private final PropertyChangeListener chartColorChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();
            chart.setBorderColor(component.getBackground());
        }
    };

    private PropertyChangeListener chartNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private PropertyChangeListener displayTimePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            TimeTextField component = (TimeTextField) propertyChangeEvent.getSource();
            chart.setDisplayTime(component.getText());
        }
    };

    private PropertyChangeListener lineWidthPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField component = (NumberTextField) propertyChangeEvent.getSource();
            chart.setLineWidth(Integer.valueOf(component.getText()));
        }
    };

    private PropertyChangeListener timeTickCountPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField component = (NumberTextField) propertyChangeEvent.getSource();
            chart.setTimeTickCount(Integer.valueOf(component.getText()));
        }
    };

    private PropertyChangeListener axisTickCountPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField component = (NumberTextField) propertyChangeEvent.getSource();
            chart.setAxisTickCount(Integer.valueOf(component.getText()));
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartProperties(XReference xref) {
        this.xref = xref;

        // common properties
        chartNameTextField.setText(chart.getChartName());
        chartNameTextField.addPropertyChangeListener("text", chartNamePropertyChanged);
        chartNameTextField.setBounds(15, 25, 210, 23);

        JPanel commonPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        commonPanel.add(chartNameTextField);

        // display time properties
        displayTimeTextField.setText(Scope.TIME_FORMAT_MIN_TIME);
        displayTimeTextField.addPropertyChangeListener("time", displayTimePropertyChanged);
        displayTimeTextField.setBounds(15, 25, 100, 23);

        JLabel displayTimeText = new JLabel("[" + Scope.TIME_FORMAT_TEMPLATE + "]");
        displayTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        displayTimeText.setBounds(125, 24, 120, 23);

        JPanel displayTimePanel = new TitleBorder(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_DISPLAY_TIME));
        displayTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        displayTimePanel.add(displayTimeTextField);
        displayTimePanel.add(displayTimeText);

        // style properties
        JPanel borderColor = new JPanel();
        borderColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        borderColor.setBackground(chart.getBorderColor());
        borderColor.setBounds(15, 25, 40, 40);
        borderColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        borderColor.addPropertyChangeListener("background", borderColorChanged);

        JLabel borderColorText = new JLabel("Border Color");
        borderColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        borderColorText.setBounds(60, 34, 120, 23);

        JPanel chartColor = new JPanel();
        chartColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chartColor.setBackground(chart.getChartColor());
        chartColor.setBounds(15, 72, 40, 40);
        chartColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        chartColor.addPropertyChangeListener("background", chartColorChanged);

        JLabel chartColorText = new JLabel("Chart Color");
        chartColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        chartColorText.setBounds(60, 81, 120, 23);

        NumberTextField lineWidthTextField = new NumberTextField();
        lineWidthTextField.setText(String.valueOf(chart.getLineWidth()));
        lineWidthTextField.addPropertyChangeListener("number", lineWidthPropertyChanged);     
        lineWidthTextField.setBounds(15, 120, 40, 23);

        JLabel lineWidthText = new JLabel("Line Width");
        lineWidthText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineWidthText.setBounds(60, 120, 120, 23);

        NumberTextField timeTickCountTextField = new NumberTextField();
        timeTickCountTextField.setText(String.valueOf(chart.getTimeTickCount()));
        lineWidthTextField.addPropertyChangeListener("number", timeTickCountPropertyChanged);  
        timeTickCountTextField.setBounds(15, 150, 40, 23);

        JLabel timeTickCountText = new JLabel("Time Tick Count");
        timeTickCountText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        timeTickCountText.setBounds(60, 150, 120, 23);      
        
        NumberTextField axisTickCountTextField = new NumberTextField();
        axisTickCountTextField.setText(String.valueOf(chart.getAxisTickCount()));
        lineWidthTextField.addPropertyChangeListener("number", axisTickCountPropertyChanged); 
        axisTickCountTextField.setBounds(15, 180, 40, 23);

        JLabel axisTickCountText = new JLabel("Axis Tick Count");
        axisTickCountText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisTickCountText.setBounds(60, 180, 120, 23);    
        
        JPanel stylePanel = new TitleBorder(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_DISPLAY_STYLE));
        stylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 220));
        stylePanel.add(borderColor);
        stylePanel.add(borderColorText);
        stylePanel.add(chartColor);
        stylePanel.add(chartColorText);
        stylePanel.add(lineWidthTextField);
        stylePanel.add(lineWidthText);
        stylePanel.add(timeTickCountTextField);
        stylePanel.add(timeTickCountText);
        stylePanel.add(axisTickCountTextField);
        stylePanel.add(axisTickCountText);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        contentPanel.add(displayTimePanel);
        contentPanel.add(stylePanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_TITLE));
        textHeader.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        textHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout());
        this.add(textHeader, BorderLayout.PAGE_START);
        this.add(scrollPanel, BorderLayout.CENTER);
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

    public void reloadChart() {
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

    private void reload() {
        // reload common properties
        if (!chartNameTextField.getText().equals(chart.getChartName())) {
            chartNameTextField.setText(chart.getChartName());
            chartNameTextField.setCaretPosition(0);
        }

        // reload display time properties
        displayTimeTextField.setText(Scope.timeFormaterToString(chart.getDisplayTime()));
        
        // reload style
        // TODO
        
        // display chart properties
        xref.propertiesPanel.setCard(Propertie.CHART);
    }
}
