package twincat.app.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import twincat.Resources;

public class AppTextField extends JTextField {
    private static final long serialVersionUID = 1L;

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
