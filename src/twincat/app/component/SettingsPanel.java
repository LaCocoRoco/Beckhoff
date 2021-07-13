package twincat.app.component;

import javax.swing.JScrollPane;

import twincat.LoremIpsum;

public class SettingsPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public SettingsPanel(XReference xref) {
        this.setViewportView(new LoremIpsum());
    }
}
