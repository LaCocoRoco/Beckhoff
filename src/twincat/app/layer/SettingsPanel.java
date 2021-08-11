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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twincat.Resources;
import twincat.app.components.CheckBox;
import twincat.app.components.NumberTextField;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TitledPanel;
import twincat.scope.Chart;

public class SettingsPanel extends JPanel {
    private static final long serialVersionUID = 1L;
  
    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int DEFAULT_CONTAINER_WIDTH = 450;
    
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
    
    private final CheckBox chartDebugEnabled = new CheckBox();

    private final NumberTextField chartRefreshRate = new NumberTextField();
   
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener chartRefreshRatePropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                refreshRate = (int) numberTextField.getValue();             
            }
        }
    };
 
    private final ItemListener chartDebugEnabledItemListener = new ItemListener() {
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

        // chart settings
        chartRefreshRate.setValue(refreshRate);
        chartRefreshRate.setMinValue(0);
        chartRefreshRate.setMaxValue(100);
        chartRefreshRate.addPropertyChangeListener(chartRefreshRatePropertyChangeListener);
        chartRefreshRate.setBounds(15, 25, 40, 20);

        JLabel chartRefreshRateText = new JLabel(languageBundle.getString(Resources.TEXT_SETTINGS_CHART_REFRESH_RATE));
        chartRefreshRateText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        chartRefreshRateText.setBounds(70, 25, 300, 21);
        
        chartDebugEnabled.setSelected(debugEnabled);
        chartDebugEnabled.addItemListener(chartDebugEnabledItemListener);
        chartDebugEnabled.setFocusPainted(false);
        chartDebugEnabled.setBounds(25, 55, 20, 20);

        JLabel chartDebugEnabledText = new JLabel(languageBundle.getString(Resources.TEXT_SETTINGS_CHART_DEBUG_ENABLED));
        chartDebugEnabledText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        chartDebugEnabledText.setBounds(70, 55, 300, 23);
 
        JTextArea chartDescriptionTextArea = new JTextArea(languageBundle.getString(Resources.TEXT_SETTINGS_CHART_DESCRIPTION));
        chartDescriptionTextArea.setCaretPosition(0);
        chartDescriptionTextArea.setLineWrap(true);
        chartDescriptionTextArea.setWrapStyleWord(true);
        chartDescriptionTextArea.setEditable(false);
        chartDescriptionTextArea.setOpaque(false);
        chartDescriptionTextArea.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        chartDescriptionTextArea.setBounds(25, 85, DEFAULT_CONTAINER_WIDTH - 25, 40);
        
        TitledPanel chartPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_SETTINGS_CHART));
        chartPanel.setPreferredSize(new Dimension(DEFAULT_CONTAINER_WIDTH, 120));
        chartPanel.add(chartRefreshRate);
        chartPanel.add(chartRefreshRateText);
        chartPanel.add(chartDebugEnabled);
        chartPanel.add(chartDebugEnabledText);
        chartPanel.add(chartDescriptionTextArea);
 
        JPanel chartPanelContainer = new JPanel();
        chartPanelContainer.setLayout(new FlowLayout(FlowLayout.LEADING));
        chartPanelContainer.add(chartPanel);   
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(chartPanelContainer);
        
        JScrollPane scrollPanel = new JScrollPane();  
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
