package twincat.app.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextArea;

import twincat.Utilities;

public class LoremIpsum extends JTextArea {
    private static final long serialVersionUID = 1L;
    
    /*************************/
    /** constant attributes **/
    /*************************/

    private static final String LOREM_IPSUM_PATH = "/resources/string/lorem_ipsum.txt";

    private static final int FONT_SIZE = 12;
    
    /*************************/
    /****** constructor ******/
    /*************************/   
    
    public LoremIpsum() {
        this.setBackground(Color.WHITE);
        this.setText(Utilities.getStringFromFilePath(LOREM_IPSUM_PATH));
        this.setCaretPosition(0);
        this.setMargin(new Insets(5, 5, 5, 5));
        this.setLineWrap(true);
        this.setWrapStyleWord(false);
        this.setEditable(false);
        this.setFont(new Font("Consolas", Font.PLAIN, FONT_SIZE));
        this.setColumns(1);
    }   
}
