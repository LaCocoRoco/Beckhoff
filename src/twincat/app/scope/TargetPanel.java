package twincat.app.scope;

import javax.swing.JScrollPane;

import twincat.LoremIpsum;

public class TargetPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/
     
    public TargetPanel() {
        this.setViewportView(new LoremIpsum());
    }  
}
