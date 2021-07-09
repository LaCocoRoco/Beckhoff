package twincat.app.component;

import javax.swing.JScrollPane;

import twincat.LoremIpsum;

public class PanelSettings extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelSettings() {
        this.setViewportView(new LoremIpsum());
    }
}
