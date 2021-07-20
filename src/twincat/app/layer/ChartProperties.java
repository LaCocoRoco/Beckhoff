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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import twincat.Resources;
import twincat.app.components.TextField;
import twincat.app.components.TitleBorder;
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

    private final TextField displayTimeTextField = new TextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final PropertyChangeListener borderColorChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();

            if (propertyChangeEvent.getPropertyName().equals("background")) {
                chart.setBorderColor(component.getBackground());
            }
        }
    };

    private final PropertyChangeListener chartColorChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();

            if (propertyChangeEvent.getPropertyName().equals("background")) {
                chart.setBorderColor(component.getBackground());
            }
        }
    };

    private final DocumentListener chartNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private final DocumentListener displayTimeTextFieldDocumentListener = new DocumentListener() {
        private String displayTime = Scope.TIME_FORMAT_MIN_TIME;

        private final Runnable task = new Runnable() {
            @Override
            public void run() {
                String input = displayTimeTextField.getText();
                if (!displayTime.equals(displayTimeTextField.getText())) {
                    StringBuilder builder = new StringBuilder(input);
                    int caret = displayTimeTextField.getCaretPosition();
                    if (input.length() > displayTime.length()) {
                        // value added
                        if (caret <= displayTime.length()) {
                            char value = displayTime.charAt(caret - 1);

                            if (value == ':' || value == '.') {
                                input = displayTime;
                            } else {
                                builder.deleteCharAt(caret);
                                input = builder.toString();
                            }
                        } else {
                            caret = caret - 1;
                            input = displayTime;
                        }
                    } else {
                        // value removed
                        if (caret < displayTime.length()) {
                            char value = displayTime.charAt(caret);

                            if (value == ':' || value == '.') {
                                input = displayTime;
                            } else {
                                builder.insert(caret, "0");
                                input = builder.toString();
                            }
                        }
                    }

                    // format time
                    String formatedInput = Scope.timeFormaterParse(input);

                    // update time
                    if (formatedInput.length() > Scope.TIME_FORMAT_MIN_TIME.length()) {
                        displayTime = formatedInput.substring(1);
                    } else {
                        displayTime = formatedInput;
                    }

                    // set display time
                    chart.setDisplayTime(displayTime);
                    displayTimeTextField.setText(displayTime);
                    displayTimeTextField.setCaretPosition(caret);
                }
            }
        };

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            SwingUtilities.invokeLater(task);
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            SwingUtilities.invokeLater(task);
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            SwingUtilities.invokeLater(task);
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartProperties(XReference xref) {
        this.xref = xref;

        // common properties
        chartNameTextField.setText(chart.getChartName());
        chartNameTextField.getDocument().addDocumentListener(chartNameTextFieldDocumentListener);
        chartNameTextField.setBounds(15, 25, 210, 25);

        JPanel commonPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
        commonPanel.add(chartNameTextField);

        // display time properties
        displayTimeTextField.setText(Scope.TIME_FORMAT_MIN_TIME);
        displayTimeTextField.setHorizontalAlignment(JTextField.CENTER);
        displayTimeTextField.getDocument().addDocumentListener(displayTimeTextFieldDocumentListener);
        displayTimeTextField.setBounds(15, 25, 100, 25);

        JLabel displayTimeText = new JLabel("[" + Scope.TIME_FORMAT_TEMPLATE + "]");
        displayTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        displayTimeText.setBounds(125, 23, 120, 25);

        JPanel displayTimePanel = new TitleBorder(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_DISPLAY_TIME));
        displayTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
        displayTimePanel.add(displayTimeTextField);
        displayTimePanel.add(displayTimeText);

        // style properties
        JPanel borderColor = new JPanel();
        borderColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        borderColor.setBackground(chart.getBorderColor());
        borderColor.setBounds(15, 25, 40, 40);
        borderColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        borderColor.addPropertyChangeListener(borderColorChanged);

        JLabel borderColorText = new JLabel("Border Color");
        borderColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        borderColorText.setBounds(60, 33, 120, 25);

        JPanel chartColor = new JPanel();
        chartColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chartColor.setBackground(chart.getChartColor());
        chartColor.setBounds(15, 75, 40, 40);
        chartColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        chartColor.addPropertyChangeListener(chartColorChanged);

        JLabel chartColorText = new JLabel("Chart Color");
        chartColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        chartColorText.setBounds(60, 83, 120, 25);

        //lineWidth = 1;
        //timeTickCount = 11;
        //axisTickCount = 11;

        // TODO : rework components
        // TODO : text field number
        // TODO : text field all
        
        TextField lineWidthTextField = new TextField();
        lineWidthTextField.setText(String.valueOf(chart.getLineWidth()));
        lineWidthTextField.setBounds(15, 130, 40, 25);
        
        JLabel lineWidthText = new JLabel("Line Width");
        lineWidthText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineWidthText.setBounds(60, 130, 120, 25);
        
        JPanel stylePanel = new TitleBorder(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_DISPLAY_STYLE));
        stylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 170));
        stylePanel.add(borderColor);
        stylePanel.add(borderColorText);
        stylePanel.add(chartColor);
        stylePanel.add(chartColorText);
        stylePanel.add(lineWidthTextField);
        stylePanel.add(lineWidthText);
        

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
            chartNameTextField.getDocument().removeDocumentListener(chartNameTextFieldDocumentListener);
            chartNameTextField.setText(chart.getChartName());
            chartNameTextField.setCaretPosition(0);
            chartNameTextField.getDocument().addDocumentListener(chartNameTextFieldDocumentListener);
        }

        // reload display time properties
        displayTimeTextField.setText(Scope.timeFormaterToString(chart.getDisplayTime()));
    }
}
