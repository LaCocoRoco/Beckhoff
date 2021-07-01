package twincat.app.components;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ScopeSimple extends JScrollPane {
    private static final long serialVersionUID = 1L;   

    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeSimple() {
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.setBorder(new EmptyBorder(3, 3, 3, 3));
        this.setViewportView(new LoremIpsumText());
    }
}
