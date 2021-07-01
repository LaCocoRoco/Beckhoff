package twincat.app.components;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twincat.Utilities;

public class AdsPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int FONT_SIZE = 12;

    private static final String FONT_FAMILY = "Consolas";

    private static final String ADS_INFO_PATH = "/resources/string/ads_info.txt";
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsPanel() {
        String instructionText = Utilities.getStringFromFilePath(ADS_INFO_PATH);

        JTextArea instructionTextArea = new JTextArea(instructionText);
        instructionTextArea.setCaretPosition(0);
        instructionTextArea.setMargin(new Insets(5, 5, 5, 5));
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(false);
        instructionTextArea.setEditable(false);
        instructionTextArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));

		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.setViewportView(instructionTextArea);
    }
}
