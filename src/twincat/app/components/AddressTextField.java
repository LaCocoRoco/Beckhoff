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

public class AddressTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int VALUE_KEY_RESOLUTION = 1;
    
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private long minValue = - Long.MIN_VALUE;

    private long maxValue = + Long.MAX_VALUE;
    
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
                if (!number.equals(getText())) {
                    updateNumber();
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

    /*********************************/
    /********** constructor **********/
    /*********************************/
    
    public AddressTextField() {
        super();
        this.initialize();
    }

    public AddressTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        this.initialize();
    }

    public AddressTextField(int columns) {
        super(columns);
        this.initialize();
    }

    public AddressTextField(String text, int columns) {
        super(text, columns);
        this.initialize();
    }

    public AddressTextField(String text) {
        super(text);
        this.initialize();
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public long getMinValue() {
        return minValue;
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public long getValue() {
        return Long.valueOf(getText());
    }

    public void setValue(long value) {
        setText(String.valueOf(value));
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
        long value = Long.valueOf(number);
        value += VALUE_KEY_RESOLUTION; 
        setText(String.valueOf(value));
    }
    
    private void subValue() {
        long value = Long.valueOf(number);
        value -= VALUE_KEY_RESOLUTION;    
        setText(String.valueOf(value));
    }
    
    private void updateNumber() {
        String text = getText();
        int caret = getCaretPosition();
        boolean negate = false;
        
        // value is equal
        if (number.equals(text)) {
            return;
        }
        
        // value is empty
        if (text.isEmpty()) {
            return;
        }
        
        // value is negative
        if (text.charAt(0) == '-') {
            negate = true;
        }

        // replace none numbers
        text = text.replaceAll("[^0-9]", "");

        // value is empty
        if (text.isEmpty()) {
            return;
        }
       
        // max length exceeded
        if (text.length() > String.valueOf(maxValue).length()) {
 
            // number was inserted
            if (caret < text.length()) {
                
                // remove previous number
                StringBuilder stringBuilder = new StringBuilder(text);
                stringBuilder.deleteCharAt(caret);
                text = stringBuilder.toString();
            }
        }


        // max long string length
        int length = String.valueOf(Long.MAX_VALUE).length() - 1;
        
        // remove number exceeds length
        if (text.length() > length) {
            text = text.substring(0, length);
        }
        
        // text to value 
        long value = Long.valueOf(text);

        // negate value
        value = negate ? value * -1 : value;
        
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
        if (caret > number.length()) {
            caret = number.length();
        }
        
        // update content
        setText(number);
        setCaretPosition(caret);
        
        // fire property changed
        firePropertyChange("number", null, null);
    }
}
