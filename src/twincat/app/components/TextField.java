package twincat.app.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import twincat.Resources;

public class TextField extends JTextField {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final Border outerBorder = BorderFactory.createLineBorder(Color.GRAY);
    
    private final Border innerBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
    
    private final CompoundBorder border = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

    private final Font font = new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL);
    
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
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    };

    private final DocumentListener documentListener = new DocumentListener() {
        private void updateText() {
            firePropertyChange("text", null, null);
        }

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            updateText();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            updateText();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            updateText();
        }
    };
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TextField() {
        super();
        this.initialize();
    }

    public TextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        this.initialize();
    }

    public TextField(int columns) {
        super(columns);
        this.initialize();
    }

    public TextField(String text, int columns) {
        super(text, columns);
        this.initialize();
    }

    public TextField(String text) {
        super(text);
        this.initialize();
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void initialize() {
        this.getDocument().addDocumentListener(documentListener);
        this.addKeyListener(keyListener);
        this.setBorder(border);
        this.setFont(font);
    }
}
