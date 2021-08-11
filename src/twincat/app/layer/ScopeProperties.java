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
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.CheckBox;
import twincat.app.components.TextField;
import twincat.app.components.TimeTextField;
import twincat.app.components.TitledPanel;
import twincat.app.constant.Propertie;
import twincat.scope.Scope;

public class ScopeProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Scope scope = new Scope();

    /*********************************/
    /****** local final variable *****/
    /*********************************/
    
    private final JScrollPane scrollPanel = new JScrollPane();
    
    private final TextField scopeName = new TextField();

    private final TimeTextField recordTime = new TimeTextField();

    private final CheckBox autoRecord = new CheckBox();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener scopeNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("text")) {
                scope.setScopeName(scopeName.getText());
                xref.scopeBrowser.reloadSelectedTreeNode();            
            }
        }
    };

    private PropertyChangeListener recordTimePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName().equals("time")) {
                TimeTextField timeTextField = (TimeTextField) propertyChangeEvent.getSource();
                scope.setRecordTime(timeTextField.getText());         
            }
        }
    };

    private final ItemListener autoRecordItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                scope.setAutoRecord(true);
                recordTime.setEnabled(false);
            } else {
                scope.setAutoRecord(false);
                recordTime.setEnabled(true);
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

    public ScopeProperties(XReference xref) {
        this.xref = xref;

        // name properties
        scopeName.setText(scope.getScopeName());
        scopeName.addPropertyChangeListener(scopeNamePropertyChanged);
        scopeName.setBounds(15, 30, 210, 23);

        TitledPanel namePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_NAME));
        namePanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
        namePanel.add(scopeName);

        // record mode properties
        recordTime.setText(Scope.TIME_FORMAT_MIN_TIME);
        recordTime.addPropertyChangeListener(recordTimePropertyChanged);
        recordTime.setBounds(15, 30, 100, 23);

        JLabel recordTimeText = new JLabel("[" + Scope.TIME_FORMAT_TEMPLATE + "]");
        recordTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        recordTimeText.setBounds(125, 29, 120, 23);

        autoRecord.setSelected(scope.isAutoRecord());
        autoRecord.addItemListener(autoRecordItemListener);
        autoRecord.setBounds(25, 60, 20, 20);

        JLabel autoRecordText = new JLabel(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_AUTO_RECORD));
        autoRecordText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        autoRecordText.setBounds(60, 60, 180, 23);

        TitledPanel recordTimePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_RECORD_TIME));
        recordTimePanel.setMaximumSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 100));
        recordTimePanel.add(recordTime);
        recordTimePanel.add(recordTimeText);
        recordTimePanel.add(autoRecord);
        recordTimePanel.add(autoRecordText);

        // default content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL + 20, 200));
        contentPanel.add(namePanel);
        contentPanel.add(recordTimePanel);
        
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

    public Scope getScope() {
        return scope;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void setScope(Scope scope) {
        this.scope = scope;
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
        scopeName.removePropertyChangeListener(scopeNamePropertyChanged);
        scopeName.setText(scope.getScopeName());
        scopeName.setCaretPosition(0);
        scopeName.addPropertyChangeListener(scopeNamePropertyChanged);
        
        // reload record mode properties
        recordTime.setText(scope.getRecordTime());
        autoRecord.setSelected(scope.isAutoRecord());
        recordTime.setEnabled(!scope.isAutoRecord());

        // display scope properties
        scrollPanel.getVerticalScrollBar().setValue(0);
        xref.propertiesPanel.setCard(Propertie.SCOPE);
    }
}
