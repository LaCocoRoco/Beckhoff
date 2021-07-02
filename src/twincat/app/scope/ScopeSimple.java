package twincat.app.scope;

import javax.swing.JScrollPane;

import twincat.LoremIpsum;

public class ScopeSimple extends JScrollPane {
    private static final long serialVersionUID = 1L;   

    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopeSimple() {
        this.setViewportView(new LoremIpsum());
    }
}
