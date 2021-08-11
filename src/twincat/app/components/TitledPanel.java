package twincat.app.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TitledPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TitledPanel(String title) {
        super(null, true);
        this.initialize(title);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void initialize(String title) {
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(lineBorder, title);
        Border outerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(outerBorder, titleBorder);
        this.setBorder(compoundBorder);
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}
