package twincat.app.layer;

import java.awt.BorderLayout;
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
import twincat.app.components.TextField;
import twincat.app.components.TitleBorder;
import twincat.app.constant.Propertie;
import twincat.java.ScrollablePanel;
import twincat.java.WrapTopLayout;
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

    private final TextField triggerGroupNameTextField = new TextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/


    private PropertyChangeListener triggerChannelNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
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
        triggerGroupNameTextField.setText(triggerGroup.getTriggerGroupName());
        triggerGroupNameTextField.addPropertyChangeListener(triggerChannelNamePropertyChanged);
        triggerGroupNameTextField.setBounds(15, 25, 210, 23);
        
        JPanel commonPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        commonPanel.add(triggerGroupNameTextField);
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
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
            triggerGroupNameTextField.setText(triggerGroup.getTriggerGroupName());
            triggerGroupNameTextField.setCaretPosition(0);
        }
        
        // reload trigger group properties
        xref.propertiesPanel.setCard(Propertie.TRIGGER_GROUP);
    } 
}
