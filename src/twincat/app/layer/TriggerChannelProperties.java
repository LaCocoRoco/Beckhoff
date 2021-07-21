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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.ComboBox;
import twincat.app.components.NumberTextField;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TitledPanel;
import twincat.app.components.WrapTopLayout;
import twincat.app.constant.Propertie;
import twincat.scope.TriggerChannel;

public class TriggerChannelProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private TriggerChannel triggerChannel = new TriggerChannel();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final ComboBox combine = new ComboBox();

    private final ComboBox release = new ComboBox();

    private final NumberTextField threshold = new NumberTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ItemListener combineItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {

            }
        }
    };

    private final ItemListener releaseItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {

            }
        }
    };

    private PropertyChangeListener thresholdPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
            triggerChannel.setThreshold((int) numberTextField.getValue());
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

    public TriggerChannelProperties(XReference xref) {
        this.xref = xref;
        
        // build trigger combo box
        buildTriggerComboBox();
        
        // trigger properties
        JLabel combineLabel = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_CHANNEL_PROPERTIES_COMBINE));
        combineLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        combineLabel.setBounds(20, 20, 205, 20);

        combine.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        combine.setBounds(18, 40, 205, 22);

        JLabel releaseLabel = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_CHANNEL_PROPERTIES_RELEASE));
        releaseLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        releaseLabel.setBounds(20, 70, 205, 20);
     
        release.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        release.setBounds(18, 90, 205, 22);

        threshold.setValue((int) triggerChannel.getThreshold());
        threshold.setHorizontalAlignment(JTextField.LEFT);
        threshold.addPropertyChangeListener("number", thresholdPropertyChanged);
        threshold.setBounds(20, 130, 120, 23);

        JLabel thresholdText = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_CHANNEL_PROPERTIES_THRESHOLD));
        thresholdText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        thresholdText.setBounds(150, 130, 80, 23);
        
        TitledPanel triggerPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_TRIGGER_CHANNEL_PROPERTIES_TRIGGER));
        triggerPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 170));
        triggerPanel.add(combine);
        triggerPanel.add(combineLabel);      
        triggerPanel.add(release);
        triggerPanel.add(releaseLabel);   
        triggerPanel.add(threshold);
        triggerPanel.add(thresholdText); 
 
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(triggerPanel);
        
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);
        
        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_TRIGGER_CHANNEL_PROPERTIES_TITLE));
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
    
    public TriggerChannel getTriggerChannel() {
        return triggerChannel;
    }

    public void setTriggerChannel(TriggerChannel triggerChannel) {
        this.triggerChannel = triggerChannel;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void reloadTriggerChannel() {
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
        // reload trigger properties
        // TODO : reload and set combo box

        // reload trigger channel properties
        xref.propertiesPanel.setCard(Propertie.TRIGGER_CHANNEL);
    }
    
    private void buildTriggerComboBox() {
        combine.addItem(TriggerChannel.Combine.AND.toString());
        combine.addItem(TriggerChannel.Combine.OR.toString());
        combine.addItemListener(combineItemListener);
        
        release.addItem(TriggerChannel.Release.RISING_EDGE.toString().replace("_", " "));
        release.addItem(TriggerChannel.Release.FALLING_EDGE.toString().replace("_", " "));
        release.addItemListener(releaseItemListener);
    }
    
}
