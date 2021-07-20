package twincat.app.components;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TitledPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TitledPanel(String title) {
        super(null, true);
        this.setBorderTitle(title);
    }
    
    public TitledPanel() {
        super();
    }

    public TitledPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public TitledPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public TitledPanel(LayoutManager layout) {
        super(layout);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void setBorderTitle(String title) {
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(lineBorder, title);
        this.setBorder(titleBorder);
    }
}
