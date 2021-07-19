package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import twincat.Resources;
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
import twincat.scope.Channel;

public class ChannelProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Channel channel = new Channel();

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JTextField channelNameTextField = new JTextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DocumentListener channelNameTextFieldDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            channel.setChannelName(channelNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            channel.setChannelName(channelNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            channel.setChannelName(channelNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChannelProperties(XReference xref) {
        this.xref = xref;
 
        // common properties
        Border channelNameOuterBorder = channelNameTextField.getBorder();
        Border channelNameInnerBorder = BorderFactory.createEmptyBorder(0, 4, 0, 4);
        CompoundBorder channelNameCompoundBorder = BorderFactory.createCompoundBorder(channelNameOuterBorder, channelNameInnerBorder);

        channelNameTextField.setText(channel.getChannelName());
        channelNameTextField.setBorder(channelNameCompoundBorder);
        channelNameTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));       
        channelNameTextField.getDocument().addDocumentListener(channelNameTextFieldDocumentListener); 
        channelNameTextField.setBounds(15, 25, 210, 25);

        JPanel commonPanel = PropertiesPanel.buildTemplate(languageBundle.getString(Resources.TEXT_COMMON_NAME));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 70));
        commonPanel.add(channelNameTextField);
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);

        JLabel textHeader = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_TITLE));
        textHeader.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        textHeader.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BorderLayout());
        this.add(textHeader, BorderLayout.PAGE_START);
        this.add(scrollPanel, BorderLayout.CENTER);
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/
    
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    /*********************************/
    /********* public method *********/
    /*********************************/

    public void reloadChannel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                reload();
            }
        });
    }

    /*********************************/
    /******** private method *********/
    /*********************************/
 
    private void reload() {
        // reload common properties
        if (!channelNameTextField.getText().equals(channel.getChannelName())) {
            channelNameTextField.getDocument().removeDocumentListener(channelNameTextFieldDocumentListener);
            channelNameTextField.setText(channel.getChannelName());   
            channelNameTextField.setCaretPosition(0);
            channelNameTextField.getDocument().addDocumentListener(channelNameTextFieldDocumentListener);           
        }
    }    
}
