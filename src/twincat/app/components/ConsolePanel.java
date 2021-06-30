package twincat.app.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.BadLocationException;

import twincat.ads.AdsCmd;
import twincat.ads.AdsLogger;

public class ConsolePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final int MAX_LINE_COUNT = 2000;

    private static final int FONT_SIZE = 12;

    private static final String FONT_FAMILY = "Consolas";

    /*************************/
    /*** local attributes ****/
    /*************************/

    private String clipboard = new String();

    /*************************/
    /****** constructor ******/
    /*************************/

    public ConsolePanel() {
        JTextArea textArea = new JTextArea();
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
        textArea.setEditable(false);
        textArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));
        
        StreamHandler consoleHandler = new StreamHandler() {
            @Override
            public synchronized void publish(LogRecord record) {
                try {
                    if (textArea.getLineCount() > MAX_LINE_COUNT) {
                        int lineEndOffset = textArea.getLineEndOffset(0);
                        textArea.replaceRange(new String(), 0, lineEndOffset);
                    }
                } catch (BadLocationException e) {
                    // empty
                }

                String message = getFormatter().format(record);
                textArea.append(message);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        };
        
        consoleHandler.setFormatter(AdsLogger.getFormatter());
        
        Logger logger = AdsLogger.getLogger();
        logger.addHandler(consoleHandler);

        JScrollPane consolePanel = new JScrollPane(textArea);
        consolePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        consolePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        consolePanel.setBackground(Color.WHITE);
        consolePanel.setViewportView(textArea);

        AdsCmd adsCmd = new AdsCmd();
        JTextField consoleInput = new JTextField();
        consoleInput.setMargin(new Insets(2, 2, 2, 2));
        consoleInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        consoleInput.setFont(new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE));
        consoleInput.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String input = consoleInput.getText();
                    consoleInput.setText(new String());

                    if (!input.isEmpty()) {
                        clipboard = input;
                        logger.info("> " + input);
                        adsCmd.send(input);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    consoleInput.setText(clipboard);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // empty
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // empty
            }
        });

        consoleInput.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                consoleInput.requestFocus();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // empty
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                // empty
            }
        });

        this.setLayout(new BorderLayout());
        this.add(consoleInput, BorderLayout.PAGE_START);
        this.add(consolePanel, BorderLayout.CENTER);
    }
}
