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
import twincat.app.components.TextField;
import twincat.app.components.TitleBorder;
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

    private final TextField recordTimeTextField = new TextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener recordTimeTextFieldDocumentListener = new DocumentListener() {
        private String recordTime = Scope.TIME_FORMAT_MIN_TIME;

        private final Runnable task = new Runnable() {
            @Override
            public void run() {
                String input = recordTimeTextField.getText();
                if (!recordTime.equals(recordTimeTextField.getText())) {
                    StringBuilder builder = new StringBuilder(input);
                    int caret = recordTimeTextField.getCaretPosition();
                    if (input.length() > recordTime.length()) {
                        // value added
                        if (caret <= recordTime.length()) {
                            char value = recordTime.charAt(caret - 1);

                            if (value == ':' || value == '.') {
                                input = recordTime;
                            } else {
                                builder.deleteCharAt(caret);
                                input = builder.toString();
                            }
                        } else {
                            caret = caret - 1;
                            input = recordTime;
                        }
                    } else {
                        // value removed
                        if (caret < recordTime.length()) {
                            char value = recordTime.charAt(caret);

                            if (value == ':' || value == '.') {
                                input = recordTime;
                            } else {
                                builder.insert(caret, "0");
                                input = builder.toString();
                            }
                        }
                    }

                    // format time
                    String formatedInput = Scope.timeFormaterParse(input);

                    // set time
                    if (formatedInput.length() > Scope.TIME_FORMAT_MIN_TIME.length()) {
                        recordTime = formatedInput.substring(1);
                    } else {
                        recordTime = formatedInput;
                    }

                    // set record time
                    scope.setRecordTime(recordTime);
                    recordTimeTextField.setText(recordTime);
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
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            scope.setScopeName(scopeNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
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
        scopeNameTextField.getDocument().addDocumentListener(scopeNameTextFieldDocumentListener);
        scopeNameTextField.setBounds(15, 25, 210, 25);

        JPanel commonPanel = new TitleBorder(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
        commonPanel.add(scopeNameTextField);

        // record mode properties
        recordTimeTextField.setText(Scope.TIME_FORMAT_MIN_TIME);
        recordTimeTextField.setHorizontalAlignment(JTextField.CENTER);
        recordTimeTextField.getDocument().addDocumentListener(recordTimeTextFieldDocumentListener);
        recordTimeTextField.setBounds(15, 25, 100, 25);

        JLabel recordTimeText = new JLabel("[" + Scope.TIME_FORMAT_TEMPLATE + "]");
        recordTimeText.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        recordTimeText.setBounds(125, 23, 120, 25);
        
        
        JPanel recordTimePanel = new TitleBorder(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_RECORD_TIME));
        recordTimePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
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
            scopeNameTextField.getDocument().removeDocumentListener(scopeNameTextFieldDocumentListener);
            scopeNameTextField.setText(scope.getScopeName());
            scopeNameTextField.setCaretPosition(0);
            scopeNameTextField.getDocument().addDocumentListener(scopeNameTextFieldDocumentListener);         
        }

        // reload record mode properties
        recordTimeTextField.setText(Scope.timeFormaterToString(scope.getRecordTime()));
    }
}
