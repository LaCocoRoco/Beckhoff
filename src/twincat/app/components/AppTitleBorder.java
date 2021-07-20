package twincat.app.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class AppTitleBorder extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AppTitleBorder(String title) {
        super(null, true);
        
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(lineBorder, title);
        this.setBorder(titleBorder);
    }
}
