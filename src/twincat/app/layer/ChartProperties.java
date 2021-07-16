package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
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

    private final JTextField chartNameTextField = new JTextField();
    
    private final JTextField displayTimeTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener displayTimeTextFieldDocumentListener = new DocumentListener() {
        private String time = Scope.TIME_FORMAT_TEMPLATE;

        private final Runnable task = new Runnable() {
            @Override
            public void run() {
                String input = displayTimeTextField.getText();
                if (!time.equals(displayTimeTextField.getText())) {
                    StringBuilder builder = new StringBuilder(input);
                    int caret = displayTimeTextField.getCaretPosition();
                    if (input.length() > time.length()) {
                        // value added
                        if (caret <= time.length()) {
                            char value = time.charAt(caret - 1);

                            if (value == ':' || value == '.') {
                                input = time;
                            } else {
                                builder.deleteCharAt(caret);
                                input = builder.toString();
                            }
                        } else {
                            caret = caret - 1;
                            input = time;
                        }
                    } else {
                        // value removed
                        char value = time.charAt(caret);

                        if (value == ':' || value == '.') {
                            input = time;
                        } else {
                            builder.insert(caret, "0");
                            input = builder.toString();
                        }
                    }   
                    
                    // format time 
                    String formatedInput = Scope.timeFormaterParse(input);
                 
                    // update time
                    if (formatedInput.length() > Scope.TIME_FORMAT_TEMPLATE.length()) {
                        time = formatedInput.substring(1);
                    } else {
                        time = formatedInput;
                    }
                    
                    // set display time
                    chart.setDisplayTime(time);
                    displayTimeTextField.setText(time);
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

    private final DocumentListener chartNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            chart.setChartName(chartNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChartProperties(XReference xref) {
        this.xref = xref;
        
        // common 
        chartNameTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));       
        chartNameTextField.setText(chart.getChartName());
        chartNameTextField.getDocument().addDocumentListener(chartNameTextFieldDocumentListener); 
        chartNameTextField.setBounds(15, 25, 140, 25);

        JPanel commonPanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 70));
        commonPanel.add(chartNameTextField);
        
        // display time
        displayTimeTextField.setText(Scope.TIME_FORMAT_TEMPLATE);
        displayTimeTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        displayTimeTextField.setHorizontalAlignment(JTextField.CENTER);
        displayTimeTextField.getDocument().addDocumentListener(displayTimeTextFieldDocumentListener);
        displayTimeTextField.setBounds(10, 25, 100, 25);
            
        JPanel displayTimePanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_DISPLAY_TIME));
        displayTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 70));
        displayTimePanel.add(displayTimeTextField);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        contentPanel.add(displayTimePanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_CHART_PROPERTIES_TITLE));
        textHeader.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        textHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
        reloadChart();
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void reloadChart()  {
        // TODO : String index out of range: 12
        System.out.println(Scope.timeFormaterToString(chart.getDisplayTime()));
        displayTimeTextField.setText(Scope.timeFormaterToString(chart.getDisplayTime()));
    }
}
