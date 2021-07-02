package twincat.app.scope;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twincat.Utilities;
import twincat.app.constants.Resources;

public class AdsPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsPanel() {
        String instructionText = Utilities.getStringFromFilePath(Resources.PATH_TEXT_ADS_INFO);

        JTextArea instructionTextArea = new JTextArea(instructionText);
        instructionTextArea.setCaretPosition(0);
        instructionTextArea.setMargin(new Insets(5, 5, 5, 5));
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(false);
        instructionTextArea.setEditable(false);
        instructionTextArea.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, 12));

		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.setViewportView(instructionTextArea);
    }
}
