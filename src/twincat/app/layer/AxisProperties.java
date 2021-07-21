package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.NumberTextField;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TextField;
import twincat.app.components.TitledPanel;
import twincat.app.constant.Propertie;
import twincat.scope.Axis;

public class AxisProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Axis axis = new Axis();

    /*********************************/
    /****** local final variable *****/
    /*********************************/
    
    private final JScrollPane scrollPanel = new JScrollPane();
    
    private final TextField axisName = new TextField();

    private final JPanel axisColor = new JPanel();

    private final JCheckBox axisNameVisible = new JCheckBox();

    private final JCheckBox axisVisible = new JCheckBox();

    private final NumberTextField lineWidth = new NumberTextField();

    private final NumberTextField valueMin = new NumberTextField();

    private final NumberTextField valueMax = new NumberTextField();

    private final JCheckBox autoscale = new JCheckBox();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener axisNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            axis.setAxisName(axisName.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private final PropertyChangeListener axisColorChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();
            axis.setAxisColor(component.getBackground());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private final ItemListener axisNameVisibleItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                axis.setAxisNameVisible(true);
            } else {
                axis.setAxisNameVisible(false);
            }
        }
    };

    private final ItemListener axisVisibleItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                axis.setAxisVisible(true);
            } else {
                axis.setAxisVisible(false);
            }
        }
    };

    private PropertyChangeListener lineWidthPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
            axis.setLineWidth((int) numberTextField.getValue());
        }
    };

    private PropertyChangeListener valueMinPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
            axis.setValueMin(numberTextField.getValue());
        }
    };

    private PropertyChangeListener valueMaxPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
            axis.setValueMax(numberTextField.getValue());
        }
    };

    private final ItemListener autoscaleItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                axis.setAutoscale(true);
                valueMin.setEnabled(false);
                valueMax.setEnabled(false);
            } else {
                axis.setAutoscale(false);
                valueMin.setEnabled(true);
                valueMax.setEnabled(true);
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

    public AxisProperties(XReference xref) {
        this.xref = xref;

        // common properties
        axisName.setText(axis.getAxisName());
        axisName.addPropertyChangeListener(axisNamePropertyChanged);
        axisName.setBounds(15, 25, 210, 23);

        TitledPanel namePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_NAME));
        namePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        namePanel.add(axisName);
        
        JPanel namePanelContainer = new JPanel();
        namePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        namePanelContainer.add(namePanel); 
        
        // color properties
        axisColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        axisColor.setBackground(axis.getAxisColor());
        axisColor.setBounds(15, 25, 40, 40);
        axisColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        axisColor.addPropertyChangeListener("background", axisColorChanged);

        JLabel axisColorText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_AXIS_COLOR));
        axisColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisColorText.setBounds(60, 34, 160, 23);

        TitledPanel colorPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_COLOR));
        colorPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 80));
        colorPanel.add(axisColor);
        colorPanel.add(axisColorText);
        
        JPanel colorPanelContainer = new JPanel();
        colorPanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        colorPanelContainer.add(colorPanel); 
        
        // style properties
        lineWidth.setValue(axis.getLineWidth());
        lineWidth.setMinValue(0);
        lineWidth.setMaxValue(100);
        lineWidth.addPropertyChangeListener("number", lineWidthPropertyChanged);
        lineWidth.setBounds(15, 25, 40, 20);

        JLabel lineWidthText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_LINE_WIDTH));
        lineWidthText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineWidthText.setBounds(60, 25, 160, 21);

        axisNameVisible.setSelected(axis.isAxisNameVisible());
        axisNameVisible.addItemListener(axisNameVisibleItemListener);
        axisNameVisible.setFocusPainted(false);
        axisNameVisible.setBounds(25, 55, 20, 20);

        JLabel axisNameVisibleText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_NAME_VISIBLE));
        axisNameVisibleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisNameVisibleText.setBounds(60, 55, 160, 23);

        axisVisible.setSelected(axis.isAxisVisible());
        axisVisible.addItemListener(axisVisibleItemListener);
        axisVisible.setFocusPainted(false);
        axisVisible.setBounds(25, 85, 20, 20);

        JLabel axisVisibleText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_AXIS_VISIBLE));
        axisVisibleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisVisibleText.setBounds(60, 85, 160, 23);

        TitledPanel stylePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_STYLE));
        stylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 120));
        stylePanel.add(lineWidth);
        stylePanel.add(lineWidthText);
        stylePanel.add(axisNameVisible);
        stylePanel.add(axisNameVisibleText);
        stylePanel.add(axisVisible);
        stylePanel.add(axisVisibleText);
        
        JPanel stylePanelContainer = new JPanel();
        stylePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        stylePanelContainer.add(stylePanel); 
        
        // scale properties
        valueMin.setValue((long) axis.getValueMin());
        valueMin.setHorizontalAlignment(JTextField.LEFT);
        valueMin.addPropertyChangeListener("number", valueMinPropertyChanged);
        valueMin.setBounds(15, 25, 120, 23);

        JLabel valueMinText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_VALUE_MIN));
        valueMinText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        valueMinText.setBounds(145, 25, 160, 23);

        valueMax.setValue((long) axis.getValueMax());
        valueMax.setHorizontalAlignment(JTextField.LEFT);
        valueMax.addPropertyChangeListener("number", valueMaxPropertyChanged);
        valueMax.setBounds(15, 55, 120, 23);

        JLabel valueMaxText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_VALUE_MAX));
        valueMaxText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        valueMaxText.setBounds(145, 55, 160, 23);

        autoscale.setSelected(axis.isAxisNameVisible());
        autoscale.addItemListener(autoscaleItemListener);
        autoscale.setFocusPainted(false);
        autoscale.setBounds(25, 85, 20, 20);

        JLabel autoscaleText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_VALUE_AUTOSCALE));
        autoscaleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        autoscaleText.setBounds(60, 85, 150, 23);

        TitledPanel scalePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_SCALE));
        scalePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 120));
        scalePanel.add(valueMin);
        scalePanel.add(valueMinText);
        scalePanel.add(valueMax);
        scalePanel.add(valueMaxText);
        scalePanel.add(autoscale);
        scalePanel.add(autoscaleText);
        
        JPanel scalePanelContainer = new JPanel();
        scalePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        scalePanelContainer.add(scalePanel); 
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(namePanelContainer);
        contentPanel.add(colorPanelContainer);
        contentPanel.add(stylePanelContainer);
        contentPanel.add(scalePanelContainer);

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

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void reloadAxis() {
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
        axisNameVisible.setSelected(axis.isAxisNameVisible());

        if (!axisName.getText().equals(axis.getAxisName())) {
            axisName.setText(axis.getAxisName());
            axisName.setCaretPosition(0);
        }

        // reload color properties
        axisColor.setBackground(axis.getAxisColor());

        // reload style properties
        lineWidth.setValue(axis.getLineWidth());
        axisNameVisible.setSelected(axis.isAxisNameVisible());
        axisVisible.setSelected(axis.isAxisVisible());

        // reload scale properties
        valueMin.setValue((long) axis.getValueMin());
        valueMax.setValue((long) axis.getValueMax());
        autoscale.setSelected(axis.isAutoscale());

        valueMin.setEnabled(!axis.isAutoscale());
        valueMax.setEnabled(!axis.isAutoscale());

        // display axis properties
        scrollPanel.getVerticalScrollBar().setValue(0);
        xref.propertiesPanel.setCard(Propertie.AXIS);
    }
}
