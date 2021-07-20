package twincat.app.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.Document;

import twincat.Resources;

// TODO : lose focus on enter

public class TextField extends JTextField {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TextField() {
        super();
    }

    public TextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public TextField(int columns) {
        super(columns);
    }

    public TextField(String text, int columns) {
        super(text, columns);
    }

    public TextField(String text) {
        super(text);
    }

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public void setFont(Font font) {
        super.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
    }

    @Override
    public void setBorder(Border border) {
        Border outerBorder = BorderFactory.createLineBorder(Color.GRAY);
        Border innterBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innterBorder);
        super.setBorder(compoundBorder);
    }
}
