package twincat.app.components;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import twincat.utilities.Utilities;

public class AdsPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int FONT_SIZE = 12;

    private static final String FONT_FAMILY = "Consolas";

    private static final String ADSINFO_PATH = "/resources/string/adsinfo.txt";
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsPanel() {
        String instructionText = Utilities.readStringFromPath(ADSINFO_PATH);
        JTextArea instructionTextArea = new JTextArea(instructionText);
        instructionTextArea.setAlignmentY(TOP_ALIGNMENT);
        instructionTextArea.setAlignmentX(LEFT_ALIGNMENT);
        instructionTextArea.setMargin(new Insets(5, 5, 5, 5));
        instructionTextArea.setEditable(false);
        instructionTextArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));

		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.setBorder(new EmptyBorder(3, 3, 3, 3));
        this.setViewportView(instructionTextArea);
    }
}
