package twincat.app.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import twincat.Resources;

public class NumberTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int VALUE_KEY_RESOLUTION = 1;
    
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private int minValue = 0;

    private int maxValue = 9999;
    
    /*********************************/
    /******** local variable *********/
    /*********************************/
  
    private String number = new String();
    
    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final Border outerBorder = BorderFactory.createLineBorder(Color.GRAY);
    
    private final Border innerBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
    
    private final Border border = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
    
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
                updateNumber();
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
    
    public NumberTextField() {
        super();
        this.initialize();
    }

    public NumberTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        this.initialize();
    }

    public NumberTextField(int columns) {
        super(columns);
        this.initialize();
    }

    public NumberTextField(String text, int columns) {
        super(text, columns);
        this.initialize();
    }

    public NumberTextField(String text) {
        super(text);
        this.initialize();
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void initialize() {
        this.getDocument().addDocumentListener(documentListener);
        this.addKeyListener(keyListener);
        this.setBorder(border);
        this.setFont(font);
        this.setHorizontalAlignment(CENTER);
    }
    
    private void addValue() {
        int value = Integer.valueOf(number);
        value += VALUE_KEY_RESOLUTION; 
        setText(String.valueOf(value));
    }
    
    private void subValue() {
        int value = Integer.valueOf(number);
        value -= VALUE_KEY_RESOLUTION;    
        setText(String.valueOf(value));
    }
    
    private void updateNumber() {
        String text = getText();
        int caretPosition = getCaretPosition();
        
        // no update necessary
        if (number.equals(text)) return;
        
        // replace none numeral
        text = text.replaceAll("[^0-9|^-]", "");

        // replace empty text
        if (text.isEmpty()) {
            caretPosition = 1;
            text = String.valueOf(minValue);
        }
        
        // max length exceeded
        if (text.length() > String.valueOf(maxValue).length()) {
 
            // number was inserted
            if (caretPosition < text.length()) {
                StringBuilder stringBuilder = new StringBuilder(text);
                stringBuilder.deleteCharAt(caretPosition);
                text = stringBuilder.toString();
            }
        }

        // text to value
        int value = Integer.valueOf(text);
        
        // value above maximum
        if (value > maxValue) {
            value = maxValue;
        }
        
        // value below minimum 
        if (value < minValue) {
            value = minValue;
        }
        
        // value to text
        number = String.valueOf(value);
        
        // caret position out of bounds
        if (caretPosition > number.length()) {
            caretPosition = number.length();
        }
        
        // update content
        setText(number);
        setCaretPosition(caretPosition);
        
        // fire property changed
        firePropertyChange("number", null, null);
    }
}
