package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import twincat.Resources;
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
import twincat.scope.Scope;

public class ScopeProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Scope scope = new Scope();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    private final ScrollablePanel contentPanel = new ScrollablePanel();
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    private JPanel getPanelTemplate(String languageBundleText) {
        String text = languageBundle.getString(languageBundleText);
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(lineBorder, text);
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(titleBorder, border);
        JPanel panel = new JPanel();
        panel.setBorder(compoundBorder);
        panel.setPreferredSize(new Dimension(200, 100));
        return panel;
    }
    
    public ScopeProperties(XReference xref) {
        JFormattedTextField recordTimeInputText = new JFormattedTextField();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H'h' mm'm'");
        DateFormatter dateFormatter = new DateFormatter(simpleDateFormat);
        DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory(dateFormatter);
        recordTimeInputText.setFormatterFactory(defaultFormatterFactory);

        JPanel recordTimePanel = getPanelTemplate(Resources.TEXT_SCOPE_PROPERTIES_RECORD_TIME);
        recordTimePanel.setLayout(new BorderLayout());
        recordTimePanel.add(recordTimeInputText);
        

        contentPanel.add(recordTimePanel);
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout());
        this.add(scrollPanel, BorderLayout.CENTER);
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /*********************************/
    /******** private method *********/
    /*********************************/
}
