package twincat.app.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import twincat.Resources;
import twincat.scope.Scope;

public class TimeTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int TIME_KEY_RESOLUTION = 100;
    
    /*********************************/
    /******** local variable *********/
    /*********************************/

    private String time = Scope.TIME_FORMAT_MIN_TIME;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final Border outerBorder = BorderFactory.createLineBorder(Color.GRAY);
    
    private final Border innerBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
    
    private final CompoundBorder border = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

    private final Font font = new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL);   
    
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final KeyListener keyListener = new KeyListener() {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                getParent().requestFocusInWindow();
            }
            
            if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                getParent().requestFocusInWindow();
            }
  
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                addValue();
            }
            
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                subValue();
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    };

    private final DocumentListener documentListener = new DocumentListener() {
        private final Runnable task = new Runnable() {
            @Override
            public void run() {
                updateTime();
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

    /*********************************/
    /********** constructor **********/
    /*********************************/
     
    public TimeTextField() {
        super();
        this.initialize();
    }

    public TimeTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        this.initialize();
    }

    public TimeTextField(int columns) {
        super(columns);
        this.initialize();
    }

    public TimeTextField(String text, int columns) {
        super(text, columns);
        this.initialize();
    }

    public TimeTextField(String text) {
        super(text);
        this.initialize();
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void initialize() {
        this.getDocument().addDocumentListener(documentListener);
        this.setHorizontalAlignment(CENTER);
        this.addKeyListener(keyListener);
        this.setBorder(border);
        this.setFont(font);
    }

    private void addValue() {
        long value = Scope.timeFormaterToLong(time);
        value += TIME_KEY_RESOLUTION; 
        setText(Scope.timeFormaterToString(value));
    }
    
    private void subValue() {
        long value = Scope.timeFormaterToLong(time);
        value -= TIME_KEY_RESOLUTION; 
        setText(Scope.timeFormaterToString(value));
    }

    private void updateTime() {
        String text = getText();
        int caret = getCaretPosition();
        StringBuilder builder = new StringBuilder(text);
        
        // no update necessary
        if (time.equals(text)) return;
        
        // value added
        if (text.length() > time.length()) {
            if (caret <= time.length()) {
                char value = time.charAt(caret - 1);

                if (value == ':' || value == '.') {
                    text = time;
                } else {
                    builder.deleteCharAt(caret);
                    text = builder.toString();
                }
            } else {
                caret = caret - 1;
                text = time;
            }
        }
        
        // value removed   
        if (text.length() < time.length()) {
            if (caret < time.length()) {
                char value = time.charAt(caret);

                if (value == ':' || value == '.') {
                    text = time;
                } else {
                    builder.insert(caret, "0");
                    text = builder.toString();
                }
            }
        }

        // format text
        String formatedTime = Scope.timeFormaterParse(text);

        // set time
        if (formatedTime.length() > Scope.TIME_FORMAT_MIN_TIME.length()) {
            time = formatedTime.substring(1);
        } else {
            time = formatedTime;
        }

        // update content
        setText(time);
        setCaretPosition(caret);
        
        // fire property changed
        firePropertyChange("time", null, null);
    }
}
