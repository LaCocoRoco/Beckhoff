package twincat.app.layer;

import java.awt.BorderLayout;
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
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.NumberTextField;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TextField;
import twincat.app.components.TitledPanel;
import twincat.app.constant.Propertie;
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
    
    private final JScrollPane scrollPanel = new JScrollPane();
    
    private final TextField triggerGroupName = new TextField();

    private final NumberTextField offset = new NumberTextField();
    
    private final JCheckBox enabled = new JCheckBox();
    
    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener triggerChannelNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            triggerGroup.setTriggerGroupName(triggerGroupName.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private PropertyChangeListener offsetPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                triggerGroup.setTriggerOffset((int) numberTextField.getValue());        
            }
        }
    };

    private final ItemListener enabledItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                triggerGroup.setEnabled(true);
            } else {
                triggerGroup.setEnabled(false);
            }
        }
    };

    private AbstractAction scrollPanelDisableKey = new AbstractAction() {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            /* empty */
        }
    };
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TriggerGroupProperties(XReference xref) {
        this.xref = xref;

        // name properties
        triggerGroupName.setText(triggerGroup.getTriggerGroupName());
        triggerGroupName.addPropertyChangeListener(triggerChannelNamePropertyChanged);
        triggerGroupName.setBounds(15, 25, 210, 23);

        TitledPanel namePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_TRIGGER_GROUP_PROPERTIES_NAME));
        namePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        namePanel.add(triggerGroupName);
        
        JPanel namePanelContainer = new JPanel();
        namePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        namePanelContainer.add(namePanel); 
             
        // module properties
        offset.setValue(triggerGroup.getTriggerOffset());
        offset.setMinValue(0);
        offset.setMaxValue(100);
        offset.addPropertyChangeListener(offsetPropertyChanged);
        offset.setBounds(15, 25, 40, 20);

        JLabel offsetText = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_GROUP_PROPERTIES_OFFSET));
        offsetText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        offsetText.setBounds(60, 25, 120, 21);
        
        enabled.setSelected(triggerGroup.isEnabled());
        enabled.addItemListener(enabledItemListener);
        enabled.setFocusPainted(false);
        enabled.setBounds(25, 55, 20, 20);

        JLabel enableText = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_GROUP_PROPERTIES_ENABLED));
        enableText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        enableText.setBounds(60, 55, 120, 23);

        TitledPanel modulePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_TRIGGER_GROUP_PROPERTIES_MODULE));
        modulePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 90));
        modulePanel.add(offset);
        modulePanel.add(offsetText);
        modulePanel.add(enabled);
        modulePanel.add(enableText);
        
        JPanel modulePanelContainer = new JPanel();
        modulePanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        modulePanelContainer.add(modulePanel); 
                  
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(namePanelContainer);
        contentPanel.add(modulePanelContainer);

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
    
    public TriggerGroup getTriggerGroup() {
        return triggerGroup;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void setTriggerGroup(TriggerGroup triggerGroup) {
        this.triggerGroup = triggerGroup;
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
        triggerGroupName.removePropertyChangeListener(triggerChannelNamePropertyChanged);
        triggerGroupName.setText(triggerGroup.getTriggerGroupName());
        triggerGroupName.setCaretPosition(0);
        triggerGroupName.addPropertyChangeListener(triggerChannelNamePropertyChanged);
        
        // reload module properties
        offset.setValue(triggerGroup.getTriggerOffset());
        enabled.setSelected(triggerGroup.isEnabled());

        // reload trigger group properties
        scrollPanel.getVerticalScrollBar().setValue(0);
        xref.propertiesPanel.setCard(Propertie.TRIGGER_GROUP);
    } 
}
