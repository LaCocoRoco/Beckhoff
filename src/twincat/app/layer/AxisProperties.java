package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.TextField;
import twincat.app.components.TitleBorder;
import twincat.app.constant.Propertie;
import twincat.java.ScrollablePanel;
import twincat.java.WrapTopLayout;
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

    private final JCheckBox axisNameCheckBox = new JCheckBox();
    
    private final TextField axisNameTextField = new TextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener axisNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            axis.setAxisName(axisNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private final ItemListener axisNameCheckBoxItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                axis.setAxisNameVisible(true);
            } else {
                axis.setAxisNameVisible(false);
            }
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AxisProperties(XReference xref) {
        this.xref = xref;

        // common properties
        axisNameCheckBox.setSelected(axis.isAxisNameVisible());
        axisNameCheckBox.setText(languageBundle.getString(Resources.TEXT_COMMON_SHOW_NAME));
        axisNameCheckBox.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisNameCheckBox.addItemListener(axisNameCheckBoxItemListener);
        axisNameCheckBox.setFocusPainted(false);
        axisNameCheckBox.setBounds(20, 55, 150, 20);

        axisNameTextField.setText(axis.getAxisName()); 
        axisNameTextField.addPropertyChangeListener(axisNamePropertyChanged);
        axisNameTextField.setBounds(15, 25, 210, 23);
        
        JPanel commonPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 85));
        commonPanel.add(axisNameTextField);
        commonPanel.add(axisNameCheckBox);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_TITLE));
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
        axisNameCheckBox.setSelected(axis.isAxisNameVisible());
        
        if (!axisNameTextField.getText().equals(axis.getAxisName())) {
            axisNameTextField.setText(axis.getAxisName());
            axisNameTextField.setCaretPosition(0);
        }
        
        // display axis properties
        xref.propertiesPanel.setCard(Propertie.AXIS);
    }    
}
