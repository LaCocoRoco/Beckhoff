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

import javax.swing.AbstractAction;
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
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TextField;
import twincat.app.components.TitledPanel;
import twincat.app.components.WrapTopLayout;
import twincat.app.constant.Browser;
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

    private final ComboBox targetSystem = new ComboBox();

    private final ComboBox targetPort = new ComboBox();

    private final TextField symbolName = new TextField();

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

    private PropertyChangeListener symbolNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            acquisition.setSymbolName(symbolName.getText());
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

        // build target combo box
        buildTargetComboBox();

        // target properties
        JLabel targetSystemLabel = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET_SYSTEM));
        targetSystemLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetSystemLabel.setBounds(20, 20, 265, 20);

        targetSystem.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetSystem.setBounds(18, 40, 265, 22);

        JLabel targetPortLabel = new JLabel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET_PORT));
        targetPortLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetPortLabel.setBounds(20, 70, 265, 20);

        targetPort.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        targetPort.setBounds(18, 90, 265, 22);

        TitledPanel targetPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_TARGET));
        targetPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 130));
        targetPanel.add(targetSystemLabel);
        targetPanel.add(targetSystem);
        targetPanel.add(targetPortLabel);
        targetPanel.add(targetPort);

        // TODO : connection properties
        // sampleTime
        // symbolBased
        
        // TODO : symbol information
        // dataType;   
        symbolName.setText(acquisition.getSymbolName());
        symbolName.addPropertyChangeListener(symbolNamePropertyChanged);
        symbolName.setBounds(15, 25, 265, 23);

        TitledPanel symbolInfoPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_ACQUISITION_PROPERTIES_SYMBOL_INFO));
        symbolInfoPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_BIG, 70));
        symbolInfoPanel.add(symbolName);

        // TODO : symbol connect
        // indexGroup 
        // indexOffset;

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
        applyButton.addActionListener(applyActionListener);

        JToolBar applyToolBar = new JToolBar();
        applyToolBar.setLayout(new BorderLayout());
        applyToolBar.setFloatable(false);
        applyToolBar.setRollover(false);
        applyToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        applyToolBar.add(applyButton);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);

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
        for (int i = 0; i < targetSystem.getItemCount(); i++) {
            String system = targetSystem.getItemAt(i);

            if (system.contains(acquisition.getAmsNetId())) {
                targetSystem.setSelectedIndex(i);
            }
        }

        for (int i = 0; i < targetPort.getItemCount(); i++) {
            String port = targetPort.getItemAt(i);

            if (port.contains(acquisition.getAmsPort().toString())) {
                targetPort.setSelectedIndex(i);
            }
        }

        // reload symbol information
        symbolName.setText(acquisition.getSymbolName());
        symbolName.setCaretPosition(0);

        // display acquisition properties
        xref.browserPanel.setCard(Browser.SYMBOL);
        xref.propertiesPanel.setCard(Propertie.ACQUISITION);
    }

    private void buildTargetComboBox() {
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

        for (String system : systemList) {
            targetSystem.addItem(system);
        }
        
        targetSystem.addItemListener(targetSystemItemListener);
        
        for (AmsPort amsPort : AmsPort.values()) {
            if (!amsPort.equals(AmsPort.NONE)) {
                portList.add(amsPort.toString());
            }
        }

        for (String port : portList) {
            targetPort.addItem(port);
        }
   
        targetPort.addItemListener(targetPortItemListener);
    }
}
