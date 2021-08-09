package twincat.app.layer;

import java.awt.BorderLayout;
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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import twincat.Resources;
import twincat.app.components.NumberTextField;
import twincat.scope.Chart;

public class SettingsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private boolean debugEnabled = false;

    private int refreshRate = Chart.DEFAULT_REFRESH_RATE;
    
    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** local final variable *****/
    /*********************************/
    
    private final JCheckBox settingDebugEnabled = new JCheckBox();

    private final NumberTextField settingRefreshRate = new NumberTextField();
   
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener refreshRatePropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                refreshRate = (int) numberTextField.getValue();             
            }
        }
    };
 
    private final ItemListener debugEnabledItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            xref.scopeBrowser.reloadSelectedTreeNode();
            
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                debugEnabled = true;
            } else {
                debugEnabled = false;
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

    public SettingsPanel(XReference xref) {
        this.xref = xref;
        
        // settings
        settingDebugEnabled.setSelected(debugEnabled);
        settingDebugEnabled.addItemListener(debugEnabledItemListener);
        settingDebugEnabled.setFocusPainted(false);
        settingDebugEnabled.setBounds(15, 15, 20, 20);

        JLabel settingDebugEnabledText = new JLabel(languageBundle.getString(Resources.TEXT_SETTINGS_CHART_DEBUG_ENABLED));
        settingDebugEnabledText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        settingDebugEnabledText.setBounds(50, 15, 300, 23);
 
        settingRefreshRate.setValue(refreshRate);
        settingRefreshRate.setMinValue(0);
        settingRefreshRate.setMaxValue(99);
        settingRefreshRate.addPropertyChangeListener(refreshRatePropertyChangeListener);
        settingRefreshRate.setBounds(10, 45, 30, 20);

        JLabel settingRefreshRateText = new JLabel(languageBundle.getString(Resources.TEXT_SETTINGS_CHART_REFRESH_RATE));
        settingRefreshRateText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        settingRefreshRateText.setBounds(50, 45, 300, 21);

        // default content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(settingDebugEnabled);
        contentPanel.add(settingDebugEnabledText);
        contentPanel.add(settingRefreshRate);
        contentPanel.add(settingRefreshRateText);
        
        JScrollPane scrollPanel = new JScrollPane();  
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
    
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }
}
