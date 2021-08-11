package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.CheckBox;
import twincat.app.components.NumberTextField;
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

    private final CheckBox axisNameVisible = new CheckBox();

    private final CheckBox axisVisible = new CheckBox();

    private final NumberTextField lineWidth = new NumberTextField();

    private final NumberTextField valueMin = new NumberTextField();

    private final NumberTextField valueMax = new NumberTextField();

    private final CheckBox autoscale = new CheckBox();

    private final CheckBox scaleSymetric = new CheckBox();

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
            if (propertyChangeEvent.getPropertyName().equals("background")) {     
                Component component = (Component) propertyChangeEvent.getSource();
                axis.setAxisColor(component.getBackground());
                xref.scopeBrowser.reloadSelectedTreeNode();                         
            }
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
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                axis.setLineWidth((int) numberTextField.getValue());           
            }
        }
    };

    private PropertyChangeListener valueMinPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                axis.setValueMin(numberTextField.getValue());          
            }
        }
    };

    private PropertyChangeListener valueMaxPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                axis.setValueMax(numberTextField.getValue());             
            }
        }
    };

    private final ItemListener autoscaleItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                axis.setAutoscale(true);
                scaleSymetric.setEnabled(true);
                valueMin.setEnabled(false);
                valueMax.setEnabled(false);
            } else {
                axis.setAutoscale(false);
                scaleSymetric.setEnabled(false);
                valueMin.setEnabled(true);
                valueMax.setEnabled(true);
            }
        }
    };

    private final ItemListener scaleSymetricItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                axis.setScaleSymetrical(true);
            } else {
                axis.setScaleSymetrical(false);
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

        // name properties
        axisName.setText(axis.getAxisName());
        axisName.addPropertyChangeListener(axisNamePropertyChanged);
        axisName.setBounds(15, 30, 210, 23);

        TitledPanel namePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_NAME));
        namePanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
        namePanel.add(axisName);

        // color properties
        axisColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        axisColor.setBackground(axis.getAxisColor());
        axisColor.setBounds(15, 30, 40, 40);
        axisColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        axisColor.addPropertyChangeListener(axisColorChanged);

        JLabel axisColorText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_AXIS_COLOR));
        axisColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisColorText.setBounds(60, 39, 160, 23);

        TitledPanel colorPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_COLOR));
        colorPanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 90));
        colorPanel.add(axisColor);
        colorPanel.add(axisColorText);

        // style properties
        lineWidth.setValue(axis.getLineWidth());
        lineWidth.setMinValue(0);
        lineWidth.setMaxValue(100);
        lineWidth.addPropertyChangeListener(lineWidthPropertyChanged);
        lineWidth.setBounds(15, 30, 40, 20);

        JLabel lineWidthText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_LINE_WIDTH));
        lineWidthText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineWidthText.setBounds(60, 30, 160, 21);

        axisNameVisible.setSelected(axis.isAxisNameVisible());
        axisNameVisible.addItemListener(axisNameVisibleItemListener);
        axisNameVisible.setFocusPainted(false);
        axisNameVisible.setBounds(25, 60, 20, 20);

        JLabel axisNameVisibleText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_NAME_VISIBLE));
        axisNameVisibleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisNameVisibleText.setBounds(60, 60, 160, 23);

        axisVisible.setSelected(axis.isAxisVisible());
        axisVisible.addItemListener(axisVisibleItemListener);
        axisVisible.setFocusPainted(false);
        axisVisible.setBounds(25, 90, 20, 20);

        JLabel axisVisibleText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_AXIS_VISIBLE));
        axisVisibleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisVisibleText.setBounds(60, 90, 160, 23);

        TitledPanel stylePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_STYLE));
        stylePanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 130));
        stylePanel.add(lineWidth);
        stylePanel.add(lineWidthText);
        stylePanel.add(axisNameVisible);
        stylePanel.add(axisNameVisibleText);
        stylePanel.add(axisVisible);
        stylePanel.add(axisVisibleText);

        // scale properties
        valueMax.setValue((long) axis.getValueMax());
        valueMax.setHorizontalAlignment(JTextField.LEFT);
        valueMax.addPropertyChangeListener(valueMaxPropertyChanged);
        valueMax.setBounds(15, 30, 120, 23);

        JLabel valueMaxText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_VALUE_MAX));
        valueMaxText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        valueMaxText.setBounds(145, 30, 160, 23);
        valueMin.setValue((long) axis.getValueMin());
        valueMin.setHorizontalAlignment(JTextField.LEFT);
        valueMin.addPropertyChangeListener(valueMinPropertyChanged);
        valueMin.setBounds(15, 60, 120, 23);

        JLabel valueMinText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_VALUE_MIN));
        valueMinText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        valueMinText.setBounds(145, 60, 160, 23);

        autoscale.setSelected(axis.isAxisNameVisible());
        autoscale.addItemListener(autoscaleItemListener);
        autoscale.setBounds(25, 90, 20, 20);
        
        JLabel autoscaleText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_AUTOSCALE));
        autoscaleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        autoscaleText.setBounds(60, 90, 150, 23);

        scaleSymetric.setSelected(axis.isScaleSymetrical());
        scaleSymetric.addItemListener(scaleSymetricItemListener);
        scaleSymetric.setBounds(25, 120, 20, 20);
        
        JLabel scaleSymetricText = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_SCALE_SYMETRICAL));
        scaleSymetricText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        scaleSymetricText.setBounds(60, 120, 150, 23);

        TitledPanel scalePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_SCALE));
        scalePanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 160));
        scalePanel.add(valueMin);
        scalePanel.add(valueMinText);
        scalePanel.add(valueMax);
        scalePanel.add(valueMaxText);
        scalePanel.add(autoscale);
        scalePanel.add(autoscaleText);
        scalePanel.add(scaleSymetric);
        scalePanel.add(scaleSymetricText);

        // default content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL + 20, 470));
        contentPanel.add(namePanel);
        contentPanel.add(colorPanel);
        contentPanel.add(stylePanel);
        contentPanel.add(scalePanel);

        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void setAxis(Axis axis) {
        this.axis = axis;
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
        axisNameVisible.setSelected(axis.isAxisNameVisible());

        axisName.removePropertyChangeListener(axisNamePropertyChanged);
        axisName.setText(axis.getAxisName());
        axisName.setCaretPosition(0);
        axisName.addPropertyChangeListener(axisNamePropertyChanged);

        // reload color properties
        axisColor.removePropertyChangeListener(axisColorChanged);
        axisColor.setBackground(axis.getAxisColor());
        axisColor.addPropertyChangeListener(axisColorChanged);
        
        // reload style properties
        lineWidth.setValue(axis.getLineWidth());
        axisNameVisible.setSelected(axis.isAxisNameVisible());
        axisVisible.setSelected(axis.isAxisVisible());

        // reload scale properties
        valueMin.setValue((long) axis.getValueMin());
        valueMax.setValue((long) axis.getValueMax());
        autoscale.setSelected(axis.isAutoscale());
        scaleSymetric.setSelected(axis.isScaleSymetrical());

        scaleSymetric.setEnabled(axis.isAutoscale());
        valueMin.setEnabled(!axis.isAutoscale());
        valueMax.setEnabled(!axis.isAutoscale());

        // display axis properties
        scrollPanel.getVerticalScrollBar().setValue(0);
        xref.propertiesPanel.setCard(Propertie.AXIS);
    }
}
