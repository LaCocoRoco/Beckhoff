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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import twincat.Resources;
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
import twincat.scope.TriggerGroup;

public class TriggerGroupProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private TriggerGroup triggerGroup = new TriggerGroup();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JTextField triggerGroupNameTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener triggerChannelNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            triggerGroup.setTriggerGroupName(triggerGroupNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            triggerGroup.setTriggerGroupName(triggerGroupNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            triggerGroup.setTriggerGroupName(triggerGroupNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TriggerGroupProperties(XReference xref) {
        this.xref = xref;

        // common properties
        Border triggerGroupNameOuterBorder = triggerGroupNameTextField.getBorder();
        Border triggerGroupNameInnerBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
        CompoundBorder triggerGroupNameCompoundBorder = BorderFactory.createCompoundBorder(triggerGroupNameOuterBorder, triggerGroupNameInnerBorder);

        triggerGroupNameTextField.setText(triggerGroup.getTriggerGroupName());
        triggerGroupNameTextField.setBorder(triggerGroupNameCompoundBorder);
        triggerGroupNameTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        triggerGroupNameTextField.getDocument().addDocumentListener(triggerChannelNameTextFieldDocumentListener);
        triggerGroupNameTextField.setBounds(15, 25, 140, 25);

        JPanel commonPanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 70));
        commonPanel.add(triggerGroupNameTextField);
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_GROUP_PROPERTIES_TITLE));
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
    
    public TriggerGroup getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(TriggerGroup triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void reloadTriggerGroup() {
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
        if (!triggerGroupNameTextField.getText().equals(triggerGroup.getTriggerGroupName())) {
            triggerGroupNameTextField.getDocument().removeDocumentListener(triggerChannelNameTextFieldDocumentListener);
            triggerGroupNameTextField.setText(triggerGroup.getTriggerGroupName());
            triggerGroupNameTextField.getDocument().addDocumentListener(triggerChannelNameTextFieldDocumentListener);
        }
    } 
}
