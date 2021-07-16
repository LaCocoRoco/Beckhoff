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
    /******** global variable ********/
    /*********************************/

    private Scope scope = new Scope();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JTextField recordTimeTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener recordTimeTextFieldDocumentListener = new DocumentListener() {
        private String recordTimeText = Scope.TIME_FORMAT_TEMPLATE;

        private final Runnable task = new Runnable() {
            @Override
            public void run() {
                String inputText = recordTimeTextField.getText();
                if (!recordTimeText.equals(recordTimeTextField.getText())) {
                    // update text
                    StringBuilder builder = new StringBuilder(inputText);
                    int caretPosition = recordTimeTextField.getCaretPosition();
                    if (inputText.length() > recordTimeText.length()) {
                        // char added
                        if (caretPosition <= recordTimeText.length()) {
                            char input = recordTimeText.charAt(caretPosition - 1);

                            if (input == ':' || input == '.') {
                                inputText = recordTimeText;
                            } else {
                                builder.deleteCharAt(caretPosition);
                                inputText = builder.toString();
                            }
                        } else {
                            caretPosition = caretPosition - 1;
                            inputText = recordTimeText;
                        }
                    } else {
                        // char removed
                        char input = recordTimeText.charAt(caretPosition);

                        if (input == ':' || input == '.') {
                            inputText = recordTimeText;
                        } else {
                            builder.insert(caretPosition, "0");
                            inputText = builder.toString();
                        }
                    }   
                    
                    // format time 
                    String formatedInputText = Scope.formatTime(inputText);
                 
                    // set record time
                    if (formatedInputText.length() > Scope.TIME_FORMAT_TEMPLATE.length()) {
                        recordTimeText = formatedInputText.substring(1);
                    } else {
                        recordTimeText = formatedInputText;
                    }
                    
                    scope.setRecordTime(recordTimeText);
                    
                    recordTimeTextField.setText(recordTimeText);
                    recordTimeTextField.setCaretPosition(caretPosition);
                }
            }
        };

        @Override
        public void insertUpdate(DocumentEvent e) {
            SwingUtilities.invokeLater(task);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            SwingUtilities.invokeLater(task);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            SwingUtilities.invokeLater(task);
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeProperties(XReference xref) {
        recordTimeTextField.setText(Scope.TIME_FORMAT_TEMPLATE);
        recordTimeTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        recordTimeTextField.getDocument().addDocumentListener(recordTimeTextFieldDocumentListener);
        recordTimeTextField.setHorizontalAlignment(JTextField.CENTER);
        recordTimeTextField.setPreferredSize(new Dimension(100, 25));

        JPanel recordTimePanel = PropertiesPanel.buildTemplate(Resources.TEXT_SCOPE_PROPERTIES_RECORD_TIME);
        recordTimePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        recordTimePanel.add(recordTimeTextField);

        
        

        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(recordTimePanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_SCOPE_PROPERTIES_TITLE));
        textHeader.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        textHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
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
}
