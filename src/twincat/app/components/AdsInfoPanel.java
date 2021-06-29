package twincat.app.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AdsInfoPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int FONT_SIZE = 12;

    private static final String FONT_FAMILY = "Consolas";

    /*************************/
    /****** constructor ******/
    /*************************/

    private static final String TEXT_INSTRUCTION = ""
    		+ "Commands:      	\n"
    		+ "					\n"
    		+ "ads				\n"
    		+ "info				\n"
    		+ "state			\n"
    		+ "					\n"
    		+ "port				\n"
    		+ "port list		\n"
    		+ "port ping		\n"
    		+ "port address		\n"
    		+ "					\n"
    		+ "netid			\n"
    		+ "netid local      \n"
    		+ "netid address	\n"
    		+ "					\n"
    		+ "symbol list		\n"
    		+ "symbol list name \n"
    		+ "symbol info name	\n"
    		+ "					\n"
    		+ "read name		\n"
    		+ "write name value	\n";
    
    private static final String TEXT_DESCRIPTION = ""
    		+ "Description:					\n"
    		+ "								\n"
    		+ "get ads dll version			\n"
    		+ "get device info				\n"
    		+ "get ads state Info			\n"
    		+ "								\n"
    		+ "get ams port					\n"
    		+ "get ams port list			\n"
    		+ "get ams port states			\n"
    		+ "set ams port	by address		\n"
    		+ "								\n"
    		+ "get net id					\n"
    		+ "set net id to local          \n"
    		+ "set net id					\n"
    		+ "								\n"
    		+ "get symbol list				\n"
    		+ "get symbol list by name      \n"
    		+ "get symbol info by name		\n"
    		+ "								\n"
    		+ "read symbol value by name	\n"
    		+ "write symbol value by name	\n";
    
    public AdsInfoPanel() {
        JTextArea instructionTextArea = new JTextArea(TEXT_INSTRUCTION);
        instructionTextArea.setAlignmentY(TOP_ALIGNMENT);
        instructionTextArea.setAlignmentX(LEFT_ALIGNMENT);
        instructionTextArea.setMargin(new Insets(5, 5, 5, 5));
        instructionTextArea.setEditable(false);
        instructionTextArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));

        JTextArea descriptionTextArea = new JTextArea(TEXT_DESCRIPTION);
        instructionTextArea.setAlignmentY(TOP_ALIGNMENT);
        instructionTextArea.setAlignmentX(LEFT_ALIGNMENT);
        descriptionTextArea.setMargin(new Insets(5, 5, 5, 5));
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));
        
    	JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    	textPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    	textPanel.setBackground(Color.WHITE);
        textPanel.add(instructionTextArea);
        textPanel.add(descriptionTextArea); 
        
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.setViewportView(textPanel);
    }
}
