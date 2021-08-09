package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.NumberTextField;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TextField;
import twincat.app.components.TimeTextField;
import twincat.app.components.TitledPanel;
import twincat.app.constant.Propertie;
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
    
    private final JScrollPane scrollPanel = new JScrollPane();
    
    private final TextField chartName = new TextField();

    private final TimeTextField displayTime = new TimeTextField();

    private final JPanel borderColor = new JPanel();

    private final JPanel chartColor = new JPanel();

    private final NumberTextField lineWidth = new NumberTextField();

    private final NumberTextField timeTickCount = new NumberTextField();

    private final NumberTextField axisTickCount = new NumberTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final PropertyChangeListener borderColorPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("background")) {
                Component component = (Component) propertyChangeEvent.getSource();
                chart.setBorderColor(component.getBackground());         
            }
        }
    };

    private final PropertyChangeListener chartColorPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("background")) {
                Component component = (Component) propertyChangeEvent.getSource();
                chart.setChartColor(component.getBackground());              
            }
        }
    };

    private PropertyChangeListener chartNamePropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("text")) {
                chart.setChartName(chartName.getText());
                xref.scopeBrowser.reloadSelectedTreeNode();             
            }
        }
    };

    private PropertyChangeListener displayTimePropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("time")) {
                TimeTextField timeTextField = (TimeTextField) propertyChangeEvent.getSource();
                chart.setDisplayTime(timeTextField.getText());               
            }
        }
    };

    private PropertyChangeListener lineWidthPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                chart.setLineWidth((int) numberTextField.getValue());               
            }
        }
    };

    private PropertyChangeListener timeTickCountPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                chart.setTimeTickCount((int) numberTextField.getValue());                
            }
        }
    };

    private PropertyChangeListener axisTickCountPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                chart.setAxisTickCount((int) numberTextField.getValue());           
            }
        }
    };

    private AbstractAction scrollPanelDisableKey = new AbstractAction() {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            /* empty */
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartProperties(XReference xref) {
        this.xref = xref;

        // name properties
        chartName.setText(chart.getChartName());
        chartName.addPropertyChangeListener(chartNamePropertyChangeListener);
        chartName.setBounds(15, 25, 210, 23);

        TitledPanel namePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_NAME));
        namePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        namePanel.add(chartName);
        
        JPanel namePanelContainer = new JPanel();
        namePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        namePanelContainer.add(namePanel); 
        
        // display time properties
        displayTime.setText(Scope.TIME_FORMAT_MIN_TIME);
        displayTime.addPropertyChangeListener(displayTimePropertyChangeListener);
        displayTime.setBounds(15, 25, 100, 23);

        JLabel displayTimeText = new JLabel("[" + Scope.TIME_FORMAT_TEMPLATE + "]");
        displayTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        displayTimeText.setBounds(125, 24, 160, 23);

        TitledPanel displayTimePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_DISPLAY_TIME));
        displayTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        displayTimePanel.add(displayTime);
        displayTimePanel.add(displayTimeText);
        
        JPanel displayTimePanelContainer = new JPanel();
        displayTimePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        displayTimePanelContainer.add(displayTimePanel); 
        
        // color properties
        borderColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        borderColor.setBackground(chart.getBorderColor());
        borderColor.setBounds(15, 25, 40, 40);
        borderColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        borderColor.addPropertyChangeListener(borderColorPropertyChangeListener);

        JLabel borderColorText = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_COLOR_BORDER));
        borderColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        borderColorText.setBounds(60, 34, 120, 23);

        chartColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chartColor.setBackground(chart.getChartColor());
        chartColor.setBounds(15, 75, 40, 40);
        chartColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        chartColor.addPropertyChangeListener(chartColorPropertyChangeListener);

        JLabel chartColorText = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_COLOR_CHART));
        chartColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        chartColorText.setBounds(60, 84, 120, 23);

        TitledPanel colorPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_COLOR));
        colorPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 130));
        colorPanel.add(borderColor);
        colorPanel.add(borderColorText);
        colorPanel.add(chartColor);
        colorPanel.add(chartColorText);
        
        JPanel colorPanelContainer = new JPanel();
        colorPanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        colorPanelContainer.add(colorPanel); 
        
        // style properties
        lineWidth.setValue(chart.getLineWidth());
        lineWidth.setMinValue(0);
        lineWidth.setMaxValue(100);
        lineWidth.addPropertyChangeListener(lineWidthPropertyChangeListener);
        lineWidth.setBounds(15, 25, 40, 20);

        JLabel lineWidthText = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_LINE_WIDTH));
        lineWidthText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineWidthText.setBounds(60, 25, 160, 21);

        timeTickCount.setValue(chart.getTimeTickCount());
        timeTickCount.setMinValue(0);
        timeTickCount.setMaxValue(100);
        timeTickCount.addPropertyChangeListener(timeTickCountPropertyChangeListener);
        timeTickCount.setBounds(15, 55, 40, 20);

        JLabel timeTickCountText = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_TIME_TICK_COUNT));
        timeTickCountText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        timeTickCountText.setBounds(60, 55, 160, 21);

        axisTickCount.setValue(chart.getAxisTickCount());
        axisTickCount.setMinValue(0);
        axisTickCount.setMaxValue(100);
        axisTickCount.addPropertyChangeListener(axisTickCountPropertyChangeListener);
        axisTickCount.setBounds(15, 85, 40, 20);

        JLabel axisTickCountText = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_AXIS_TICK_COUNT));
        axisTickCountText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisTickCountText.setBounds(60, 85, 160, 21);

        TitledPanel stylePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_STYLE));
        stylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 120));
        stylePanel.add(lineWidth);
        stylePanel.add(lineWidthText);
        stylePanel.add(timeTickCount);
        stylePanel.add(timeTickCountText);
        stylePanel.add(axisTickCount);
        stylePanel.add(axisTickCountText);
        
        JPanel stylePanelContainer = new JPanel();
        stylePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        stylePanelContainer.add(stylePanel); 
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(namePanelContainer);
        contentPanel.add(displayTimePanelContainer);
        contentPanel.add(colorPanelContainer);
        contentPanel.add(stylePanelContainer);

        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);

        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout());
        this.add(scrollPanel, BorderLayout.CENTER);
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

    public void setChart(Chart chart) {
        this.chart = chart;
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

    private void reload() {
        // reload common properties
        chartName.removePropertyChangeListener(chartNamePropertyChangeListener);
        chartName.setText(chart.getChartName());
        chartName.setCaretPosition(0);
        chartName.addPropertyChangeListener(chartNamePropertyChangeListener);

        // reload display time properties
        displayTime.setText(chart.getDisplayTime());

        // reload color properties
        borderColor.removePropertyChangeListener(borderColorPropertyChangeListener);
        borderColor.setBackground(chart.getBorderColor());
        borderColor.addPropertyChangeListener(borderColorPropertyChangeListener);
        
        chartColor.removePropertyChangeListener(chartColorPropertyChangeListener);
        chartColor.setBackground(chart.getChartColor());
        chartColor.addPropertyChangeListener(chartColorPropertyChangeListener);

        // reload style properties
        lineWidth.setValue(chart.getLineWidth());
        timeTickCount.setValue(chart.getTimeTickCount());
        axisTickCount.setValue(chart.getAxisTickCount());

        // display chart properties
        scrollPanel.getVerticalScrollBar().setValue(0);
        xref.propertiesPanel.setCard(Propertie.CHART);
    }
}
