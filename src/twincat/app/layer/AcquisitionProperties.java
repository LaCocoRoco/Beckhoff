package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.ads.common.Route;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.RouteLoader;
import twincat.app.components.ComboBox;
import twincat.app.components.TextField;
import twincat.app.components.TitleBorder;
import twincat.app.constant.Browser;
import twincat.app.constant.Propertie;
import twincat.java.ScrollablePanel;
import twincat.java.WrapTopLayout;
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

    private final ComboBox targetSystemComboBox = new ComboBox();

    private final ComboBox targetPortComboBox = new ComboBox();

    private final TextField symbolNameTextField = new TextField();
       
    private final RouteLoader routeLoader = new RouteLoader();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ActionListener applyButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            applyAcquisition();
        }
    };

    private final ItemListener targetPortComboBoxItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Object selectedTargetPort = targetPortComboBox.getSelectedItem();
                AmsPort amsPort = AmsPort.getByString(selectedTargetPort.toString());
                acquisition.setAmsPort(amsPort);
            }
        }
    };

    private final ItemListener targetSystemComboBoxItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Object selectedTargetSystem = targetSystemComboBox.getSelectedItem();
                
                Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
                Matcher matcher = pattern.matcher(selectedTargetSystem.toString());
                matcher.find();

                if (matcher.groupCount() != 1) {
                    acquisition.setAmsNetId(matcher.group(1));
                }
            }
        }
    };

    private PropertyChangeListener symbolNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            acquisition.setSymbolName(symbolNameTextField.getText());
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AcquisitionProperties(XReference xref) {
        this.xref = xref;

        // build target combo box item list
        buildTargetComboBoxItemList();

        // target properties
        JLabel targetSystemLabel = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET_SYSTEM));
        targetSystemLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetSystemLabel.setBounds(20, 15, 140, 20);

        targetSystemComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetSystemComboBox.setBounds(18, 35, 265, 22);

        JLabel targetPortLabel = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET_PORT));
        targetPortLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetPortLabel.setBounds(20, 57, 140, 20);

        targetPortComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetPortComboBox.setBounds(18, 77, 265, 22);
        
        TitleBorder targetPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET));
        targetPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 115));
        targetPanel.add(targetSystemLabel);
        targetPanel.add(targetSystemComboBox);
        targetPanel.add(targetPortLabel);
        targetPanel.add(targetPortComboBox);

        symbolNameTextField.setText(acquisition.getSymbolName());
        symbolNameTextField.addPropertyChangeListener(symbolNamePropertyChanged);
        symbolNameTextField.setBounds(15, 25, 265, 23);
        
        JPanel symbolInfoPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_SYMBOL_INFO));
        symbolInfoPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 70));
        symbolInfoPanel.add(symbolNameTextField);
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(targetPanel);
        contentPanel.add(symbolInfoPanel);
        
        JButton applyButton = new JButton(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_APPLY));
        applyButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        applyButton.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        applyButton.setFocusable(false);
        applyButton.addActionListener(applyButtonActionListener);

        JToolBar applyToolBar = new JToolBar();
        applyToolBar.setLayout(new BorderLayout());
        applyToolBar.setFloatable(false);
        applyToolBar.setRollover(false);
        applyToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        applyToolBar.add(applyButton);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TITLE));
        textHeader.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        textHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout());
        this.add(textHeader, BorderLayout.PAGE_START);
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

    public void cloneAcquisition(Acquisition acquisition) {
        this.acquisition.setAmsNetId(acquisition.getAmsNetId());
        this.acquisition.setAmsPort(acquisition.getAmsPort());
        this.acquisition.setDataType(acquisition.getDataType());
        this.acquisition.setIndexGroup(acquisition.getIndexGroup());
        this.acquisition.setIndexOffset(acquisition.getIndexOffset());
        this.acquisition.setSymbolBased(acquisition.isSymbolBased());
        this.acquisition.setSymbolName(acquisition.getSymbolName());
        this.acquisition.setSampleTime(acquisition.getSampleTime());
    }

    public void reloadAcquisition() {
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
        // reload target properties
        for (int i = 0; i < targetSystemComboBox.getItemCount(); i++) {
            String system = targetSystemComboBox.getItemAt(i);

            if (system.contains(acquisition.getAmsNetId())) {
                targetSystemComboBox.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < targetPortComboBox.getItemCount(); i++) {
            String port = targetPortComboBox.getItemAt(i);

            if (port.contains(acquisition.getAmsPort().toString())) {
                targetPortComboBox.setSelectedIndex(i);
            }
        }
        
        // reload symbol information
        symbolNameTextField.setText(acquisition.getSymbolName());
        symbolNameTextField.setCaretPosition(0);
        
        // display acquisition properties
        xref.browserPanel.setCard(Browser.SYMBOL);
        xref.propertiesPanel.setCard(Propertie.ACQUISITION);
    }
    
    private void buildTargetComboBoxItemList() {
        List<Route> routeList = routeLoader.loadRouteList();
        List<String> systemList = new ArrayList<String>();
        List<String> portList = new ArrayList<String>();

        for (Route route : routeList) {
            String amsNetid = route.getAmsNetId();
            String hostName = route.getHostName();

            String system = hostName + " (" + amsNetid + ")";

            if (!systemList.contains(system)) {
                systemList.add(system);
            }
        }

        for (AmsPort amsPort : AmsPort.values()) {
            if (!amsPort.equals(AmsPort.NONE)) {
                portList.add(amsPort.toString());
            }
        }

        targetSystemComboBox.removeAllItems();
        targetPortComboBox.removeAllItems();

        for (String system : systemList) {
            targetSystemComboBox.addItem(system);
        }

        for (String port : portList) {
            targetPortComboBox.addItem(port);
        }

        targetSystemComboBox.addItemListener(targetSystemComboBoxItemListener);
        targetPortComboBox.addItemListener(targetPortComboBoxItemListener);
    }
}
