package twincat.app.panel;

import javax.swing.JScrollPane;

public class PanelSettings extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelSettings() {
        this.setViewportView(new LoremIpsum());
    }
}
