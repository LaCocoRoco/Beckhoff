package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import twincat.Resources;
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
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

    private final JTextField scopeNameTextField = new JTextField();
    
    private final JTextField recordTimeTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener recordTimeTextFieldDocumentListener = new DocumentListener() {
        private String time = Scope.TIME_FORMAT_TEMPLATE;

        private final Runnable task = new Runnable() {
            @Override
            public void run() {
                String input = recordTimeTextField.getText();
                if (!time.equals(recordTimeTextField.getText())) {
                    StringBuilder builder = new StringBuilder(input);
                    int caret = recordTimeTextField.getCaretPosition();
                    if (input.length() > time.length()) {
                        // value added
                        if (caret <= time.length()) {
                            char value = time.charAt(caret - 1);

                            if (value == ':' || value == '.') {
                                input = time;
                            } else {
                                builder.deleteCharAt(caret);
                                input = builder.toString();
                            }
                        } else {
                            caret = caret - 1;
                            input = time;
                        }
                    } else {
                        // value removed
                        char value = time.charAt(caret);

                        if (value == ':' || value == '.') {
                            input = time;
                        } else {
                            builder.insert(caret, "0");
                            input = builder.toString();
                        }
                    }   
                    
                    // format time 
                    String formatedInput = Scope.timeFormaterParse(input);
                 
                    // update record time
                    if (formatedInput.length() > Scope.TIME_FORMAT_TEMPLATE.length()) {
                        time = formatedInput.substring(1);
                    } else {
                        time = formatedInput;
                    }
                    
                    // set record time
                    scope.setRecordTime(time);
                    recordTimeTextField.setText(time);
                    recordTimeTextField.setCaretPosition(caret);
                }
            }
        };

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            SwingUtilities.invokeLater(task);
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            SwingUtilities.invokeLater(task);
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            SwingUtilities.invokeLater(task);
        }
    };

    private final DocumentListener scopeNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            scope.setScopeName(scopeNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            scope.setScopeName(scopeNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            scope.setScopeName(scopeNameTextField.getText());
            xref.scopeTree.reloadSelectedTreeNode();
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeProperties(XReference xref) {
        this.xref = xref;

        // common 
        scopeNameTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));       
        scopeNameTextField.setText(scope.getScopeName());
        scopeNameTextField.getDocument().addDocumentListener(scopeNameTextFieldDocumentListener); 
        scopeNameTextField.setBounds(15, 25, 140, 25);

        JPanel commonPanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 70));
        commonPanel.add(scopeNameTextField);

        // record time
        recordTimeTextField.setText(Scope.TIME_FORMAT_TEMPLATE);
        recordTimeTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        recordTimeTextField.setHorizontalAlignment(JTextField.CENTER);
        recordTimeTextField.getDocument().addDocumentListener(recordTimeTextFieldDocumentListener);
        recordTimeTextField.setBounds(10, 25, 100, 25);
        
        
        JPanel recordTimePanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_RECORD_TIME));
        recordTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH, 70));
        recordTimePanel.add(recordTimeTextField);

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        contentPanel.add(recordTimePanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_TITLE));
        textHeader.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        textHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
}
