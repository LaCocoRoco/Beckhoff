package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import twincat.Resources;
import twincat.ads.common.Route;
import twincat.ads.constant.AmsPort;
import twincat.ads.constant.DataType;
import twincat.ads.worker.RouteLoader;
import twincat.app.components.AddressTextField;
import twincat.app.components.CheckBox;
import twincat.app.components.ComboBox;
import twincat.app.components.NumberTextField;
import twincat.app.components.TextField;
import twincat.app.components.TitledPanel;
import twincat.app.constant.Propertie;
import twincat.scope.Acquisition;

public class AcquisitionProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Acquisition acquisition = new Acquisition();

    /*********************************/
    /****** local final variable *****/
    /*********************************/
    
    private final JScrollPane scrollPanel = new JScrollPane();
    
    private final ComboBox targetSystem = new ComboBox();

    private final ComboBox targetPort = new ComboBox();

    private final NumberTextField sampleTime = new NumberTextField();
    
    private final TextField symbolName = new TextField();

    private final AddressTextField indexGroup = new AddressTextField();
    
    private final AddressTextField indexOffset = new AddressTextField();

    private final ComboBox dataType = new ComboBox();

    private final CheckBox symbolBased = new CheckBox();
 
    private final RouteLoader routeLoader = new RouteLoader();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ActionListener applyActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            applyAcquisition();
        }
    };

    private final ItemListener targetPortItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Object selectedTargetPort = targetPort.getSelectedItem();
                AmsPort amsPort = AmsPort.getByString(selectedTargetPort.toString());
                acquisition.setAmsPort(amsPort);
            }
        }
    };

    private final ItemListener targetSystemItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Object selectedTargetSystem = targetSystem.getSelectedItem();

                Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
                Matcher matcher = pattern.matcher(selectedTargetSystem.toString());
                matcher.find();

                if (matcher.groupCount() != 1) {
                    acquisition.setAmsNetId(matcher.group(1));
                }
            }
        }
    };

    private PropertyChangeListener sampleTimePropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
                acquisition.setSampleTime((int) numberTextField.getValue());     
            }
        }
    };

    private PropertyChangeListener symbolNamePropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            acquisition.setSymbolName(symbolName.getText());
        }
    };

    private PropertyChangeListener indexGroupPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                AddressTextField numberTextField = (AddressTextField) propertyChangeEvent.getSource();
                acquisition.setIndexGroup((int) numberTextField.getValue());        
            }
        }
    };
    
    private PropertyChangeListener indexOffsetPropertyChangeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("number")) {
                AddressTextField numberTextField = (AddressTextField) propertyChangeEvent.getSource();
                acquisition.setIndexGroup((int) numberTextField.getValue());               
            }
        }
    };

    private final ItemListener dataTypeItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Object selectedTargetPort = targetPort.getSelectedItem();
                DataType dataType = DataType.getByString(selectedTargetPort.toString());
                acquisition.setDataType(dataType);
            }
        }
    };

    private final ItemListener symbolBasedItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                acquisition.setSymbolBased(true);
                symbolName.setEnabled(true);
                indexGroup.setEnabled(false);
                indexOffset.setEnabled(false);
                dataType.setEnabled(false);
            } else {
                acquisition.setSymbolBased(false);
                symbolName.setEnabled(false);
                indexGroup.setEnabled(true);
                indexOffset.setEnabled(true);
                dataType.setEnabled(true);
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

    public AcquisitionProperties(XReference xref) {
        this.xref = xref;

        // build combo box
        buildComboBox();

        // target properties
        JLabel targetSystemText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET_SYSTEM));
        targetSystemText.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetSystemText.setBounds(20, 25, 265, 20);

        targetSystem.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetSystem.setBounds(18, 45, 265, 22);

        JLabel targetPortText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET_PORT));
        targetPortText.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetPortText.setBounds(20, 75, 265, 20);

        targetPort.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetPort.setBounds(18, 95, 265, 22);

        TitledPanel targetPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET));
        targetPanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 140));
        targetPanel.add(targetSystemText);
        targetPanel.add(targetSystem);
        targetPanel.add(targetPortText);
        targetPanel.add(targetPort);

        // connection properties
        JLabel sampleTimeText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_SAMPLE_TIME));
        sampleTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        sampleTimeText.setBounds(15, 30, 160, 21);

        sampleTime.setValue(acquisition.getSampleTime());
        sampleTime.setMinValue(0);
        sampleTime.setMaxValue(100);
        sampleTime.addPropertyChangeListener(sampleTimePropertyChangeListener);
        sampleTime.setBounds(110, 30, 60, 20);

        JLabel sampleTimeRange = new JLabel("[ms]");
        sampleTimeRange.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        sampleTimeRange.setBounds(180, 30, 30, 21);  

        TitledPanel connectionPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_CONNETION));
        connectionPanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 70));
        connectionPanel.add(sampleTimeText);
        connectionPanel.add(sampleTime);
        connectionPanel.add(sampleTimeRange);

        // symbol information properties
        JLabel symbolNameText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_SYMBOL_NAME));
        symbolNameText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        symbolNameText.setBounds(15, 30, 110, 21);
        
        symbolName.setText(acquisition.getSymbolName());
        symbolName.addPropertyChangeListener(symbolNamePropertyChangeListener);
        symbolName.setBounds(110, 30, 175, 23);

        JLabel indexGroupText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_INDEX_GROUP));
        indexGroupText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        indexGroupText.setBounds(15, 60, 110, 21);

        indexGroup.setValue(acquisition.getIndexGroup());
        indexGroup.setHorizontalAlignment(JTextField.LEFT);
        indexGroup.setMinValue(0);
        indexGroup.setMaxValue(100);
        indexGroup.addPropertyChangeListener(indexGroupPropertyChangeListener);
        indexGroup.setBounds(110, 60, 60, 20);

        JLabel indexOffsetText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_INDEX_OFFSET));
        indexOffsetText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        indexOffsetText.setBounds(15, 90, 110, 21);

        indexOffset.setValue(acquisition.getIndexGroup());
        indexOffset.setHorizontalAlignment(JTextField.LEFT);
        indexOffset.setMinValue(0);
        indexOffset.setMaxValue(100);
        indexOffset.addPropertyChangeListener(indexOffsetPropertyChangeListener);
        indexOffset.setBounds(110, 90, 60, 20);

        JLabel dataTypeLabel = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_DATA_TYPE));
        dataTypeLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        dataTypeLabel.setBounds(15, 120, 110, 21);

        dataType.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        dataType.setBounds(110, 120, 100, 22);
 
        symbolBased.setSelected(acquisition.isSymbolBased());
        symbolBased.addItemListener(symbolBasedItemListener);
        symbolBased.setFocusPainted(false);
        symbolBased.setBounds(25, 150, 20, 20);
        
        JLabel symbolBasedText = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_SYMBOL_BASED));
        symbolBasedText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        symbolBasedText.setBounds(60, 150, 160, 23);
        
        TitledPanel symbolInfoPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_SYMBOL_INFO));
        symbolInfoPanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 190));
        symbolInfoPanel.add(symbolNameText);
        symbolInfoPanel.add(symbolName);
        symbolInfoPanel.add(indexGroupText);
        symbolInfoPanel.add(indexGroup);
        symbolInfoPanel.add(indexOffsetText);
        symbolInfoPanel.add(indexOffset);
        symbolInfoPanel.add(dataTypeLabel);
        symbolInfoPanel.add(dataType);
        symbolInfoPanel.add(symbolBasedText);
        symbolInfoPanel.add(symbolBased);

        // default content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG + 20, 420));
        contentPanel.add(targetPanel);
        contentPanel.add(connectionPanel);
        contentPanel.add(symbolInfoPanel);

        JButton applyButton = new JButton(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_APPLY));
        applyButton.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 2));
        applyButton.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        applyButton.setFocusable(false);
        applyButton.addActionListener(applyActionListener);

        JToolBar applyToolBar = new JToolBar();
        applyToolBar.setLayout(new BorderLayout());
        applyToolBar.setFloatable(false);
        applyToolBar.setRollover(false);
        applyToolBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); 
        applyToolBar.add(applyButton);

        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);

        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout());
        this.add(scrollPanel, BorderLayout.CENTER);
        this.add(applyToolBar, BorderLayout.PAGE_END);
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public Acquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Acquisition acquisition) {
        this.acquisition = acquisition;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void applyAcquisition() {
        xref.scopeBrowser.applySymbolAcquisition(acquisition);
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

    private void buildComboBox() {
        // ams net id
        List<Route> routeList = routeLoader.loadRouteList();
        List<String> systemList = new ArrayList<String>();
        
        for (Route route : routeList) {
            String amsNetid = route.getAmsNetId();
            String hostName = route.getHostName();

            String system = hostName + " (" + amsNetid + ")";

            if (!systemList.contains(system)) {
                systemList.add(system);
            }
        }

        for (String system : systemList) {
            targetSystem.addItem(system);
        }
        
        targetSystem.addItemListener(targetSystemItemListener);
        
        // ams port
        List<String> portList = new ArrayList<String>();
    
        for (AmsPort amsPort : AmsPort.values()) {
            if (!amsPort.equals(AmsPort.NONE)) {
                portList.add(amsPort.toString());
            }
        }

        for (String port : portList) {
            targetPort.addItem(port);
        }
   
        targetPort.addItemListener(targetPortItemListener);
        
        // data type
        for (DataType dataType : DataType.values()) {
            if (!dataType.equals(DataType.UNKNOWN)) {
                this.dataType.addItem(dataType.toString());       
            }
        }
        
        dataType.addItemListener(dataTypeItemListener); 
    }
    
    private void reload() {
        // reload target properties
        for (int i = 0; i < targetSystem.getItemCount(); i++) {
            String system = targetSystem.getItemAt(i);

            if (system.contains(acquisition.getAmsNetId())) {
                targetSystem.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < targetPort.getItemCount(); i++) {
            String port = targetPort.getItemAt(i);

            if (port.contains(acquisition.getAmsPort().toString())) {
                targetPort.setSelectedIndex(i);
                break;
            }
        }

        // reload connection properties
        sampleTime.setValue(acquisition.getSampleTime());
        
        // reload symbol information properties
        symbolName.setText(acquisition.getSymbolName());
        symbolName.setCaretPosition(0);
        
        
        indexGroup.setValue(acquisition.getIndexGroup());
        indexOffset.setValue(acquisition.getIndexOffset());
        symbolBased.setSelected(acquisition.isSymbolBased());

        for (int i = 0; i < dataType.getItemCount(); i++) {
            String dataType = this.dataType.getItemAt(i);

            if (dataType.contains(acquisition.getDataType().toString())) {
                this.dataType.setSelectedIndex(i);
                break;
            }
        }  
        
        indexGroup.setEnabled(!acquisition.isSymbolBased());
        indexOffset.setEnabled(!acquisition.isSymbolBased());
        dataType.setEnabled(!acquisition.isSymbolBased());   

        // display acquisition properties
        xref.propertiesPanel.setCard(Propertie.ACQUISITION);
    }
}
