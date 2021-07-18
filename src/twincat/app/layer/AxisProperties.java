package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import twincat.Resources;
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
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
    
    private final JTextField axisNameTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener axisNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            axis.setAxisName(axisNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            axis.setAxisName(axisNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
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
        axisNameCheckBox.setBounds(20, 55, 140, 20);

        Border axisNameOuterBorder = axisNameTextField.getBorder();
        Border axisNameInnerBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
        CompoundBorder axisNameCompoundBorder = BorderFactory.createCompoundBorder(axisNameOuterBorder, axisNameInnerBorder);

        axisNameTextField.setText(axis.getAxisName());
        axisNameTextField.setBorder(axisNameCompoundBorder);
        axisNameTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        axisNameTextField.getDocument().addDocumentListener(axisNameTextFieldDocumentListener);
        axisNameTextField.setBounds(15, 25, 140, 25);

        JPanel commonPanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 90));
        commonPanel.add(axisNameTextField);
        commonPanel.add(axisNameCheckBox);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
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
            axisNameTextField.getDocument().removeDocumentListener(axisNameTextFieldDocumentListener);
            axisNameTextField.setText(axis.getAxisName());
            axisNameTextField.getDocument().addDocumentListener(axisNameTextFieldDocumentListener);  
        }
    }    
}
