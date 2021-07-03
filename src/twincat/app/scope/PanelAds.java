package twincat.app.scope;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twincat.Utilities;
import twincat.app.constants.Resources;

public class PanelAds extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelAds() {
        String instructionText = Utilities.getStringFromFilePath(Resources.PATH_TEXT_ADS_INFO);

        JTextArea instructionTextArea = new JTextArea(instructionText);
        instructionTextArea.setCaretPosition(0);
        instructionTextArea.setMargin(new Insets(5, 5, 5, 5));
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(false);
        instructionTextArea.setEditable(false);
        instructionTextArea.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));

        this.setViewportView(instructionTextArea);
    }
}
