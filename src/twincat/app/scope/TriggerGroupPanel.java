package twincat.app.scope;

import javax.swing.JScrollPane;

import twincat.LoremIpsum;

public class TriggerGroupPanel extends JScrollPane {
   private static final long serialVersionUID = 1L;
    
    /*************************/
    /****** constructor ******/
    /*************************/
     
    public TriggerGroupPanel() {
        this.setViewportView(new LoremIpsum());
    }
}
