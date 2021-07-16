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

    private final JTextField aixsNameTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener axisNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            axis.setAxisName(aixsNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            axis.setAxisName(aixsNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            axis.setAxisName(aixsNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
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

        // common
        JCheckBox axisNameCheckBox = new JCheckBox(languageBundle.getString(Resources.TEXT_COMMON_SHOW_NAME));
        axisNameCheckBox.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        axisNameCheckBox.setBounds(20, 55, 140, 20);
        axisNameCheckBox.addItemListener(axisNameCheckBoxItemListener);
        axisNameCheckBox.setFocusPainted(false);
        
        aixsNameTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        aixsNameTextField.setText(axis.getAxisName());
        aixsNameTextField.getDocument().addDocumentListener(axisNameTextFieldDocumentListener);
        aixsNameTextField.setBounds(15, 25, 140, 25);

        JPanel commonPanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 90));
        commonPanel.add(aixsNameTextField);
        commonPanel.add(axisNameCheckBox);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_AXIS_PROPERTIES_TITLE));
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

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }
}
