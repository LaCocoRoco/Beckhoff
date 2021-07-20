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
import twincat.app.components.TimeTextField;
import twincat.app.components.TitleBorder;
import twincat.app.constant.Propertie;
import twincat.java.ScrollablePanel;
import twincat.java.WrapTopLayout;
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

    private final TextField scopeNameTextField = new TextField();

    private final TimeTextField recordTimeTextField = new TimeTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener recordTimePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            TimeTextField component = (TimeTextField) propertyChangeEvent.getSource();
            scope.setRecordTime(component.getText());
        }
    };

    private PropertyChangeListener scopeNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            scope.setScopeName(scopeNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeProperties(XReference xref) {
        this.xref = xref;

        // common properties
        scopeNameTextField.setText(scope.getScopeName());
        scopeNameTextField.addPropertyChangeListener("text", scopeNamePropertyChanged);
        scopeNameTextField.setBounds(15, 25, 210, 23);

        JPanel commonPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        commonPanel.add(scopeNameTextField);

        // record mode properties
        recordTimeTextField.setText(Scope.TIME_FORMAT_MIN_TIME);
        recordTimeTextField.addPropertyChangeListener("time", recordTimePropertyChanged);
        recordTimeTextField.setBounds(15, 25, 100, 23);

        JLabel recordTimeText = new JLabel("[" + Scope.TIME_FORMAT_TEMPLATE + "]");
        recordTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        recordTimeText.setBounds(125, 24, 120, 23);

        JPanel recordTimePanel = new TitleBorder(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_RECORD_TIME));
        recordTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        recordTimePanel.add(recordTimeTextField);
        recordTimePanel.add(recordTimeText);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        contentPanel.add(recordTimePanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_TITLE));
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

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void reloadScope() {
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
        if (!scopeNameTextField.getText().equals(scope.getScopeName())) {
            scopeNameTextField.setText(scope.getScopeName());
            scopeNameTextField.setCaretPosition(0);
        }

        // reload record mode properties
        recordTimeTextField.setText(Scope.timeFormaterToString(scope.getRecordTime()));
        
        // display scope properties
        xref.propertiesPanel.setCard(Propertie.SCOPE);
    }
}
