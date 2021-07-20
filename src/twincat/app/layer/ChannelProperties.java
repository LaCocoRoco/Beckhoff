package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.TextField;
import twincat.app.components.TitledPanel;
import twincat.app.components.WrapTopLayout;
import twincat.app.constant.Propertie;
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

    private final TextField channelNameTextField = new TextField();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private PropertyChangeListener channelNamePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            channel.setChannelName(channelNameTextField.getText());
            xref.scopeBrowser.reloadSelectedTreeNode();
        }
    };

    private AbstractAction scrollPanelDisableKey = new AbstractAction() {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            /* empty */
        }
    };
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ChannelProperties(XReference xref) {
        this.xref = xref;
 
        // common properties
        channelNameTextField.setText(channel.getChannelName());   
        channelNameTextField.addPropertyChangeListener(channelNamePropertyChanged);
        channelNameTextField.setBounds(15, 25, 210, 23);

        TitledPanel commonPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_COMMON));
        commonPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        commonPanel.add(channelNameTextField);

        // color properties
        // TODO : line color
        // TODO : plot color
        // TODO : anti alising
        TitledPanel colorPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_COLOR));
        colorPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        
        // line style properties
        // TODO : line width
        // TODO : line visible
        TitledPanel lineStylePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_LINE_STYLE));
        lineStylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));
        
        // plot style properties
        // TODO : plot size
        // TODO : plot visible;
        TitledPanel plotStylePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_PLOT_STYLE));
        plotStylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 60));

        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        contentPanel.add(colorPanel);
        contentPanel.add(lineStylePanel);
        contentPanel.add(plotStylePanel);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);
        
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
            channelNameTextField.setText(channel.getChannelName());   
            channelNameTextField.setCaretPosition(0);        
        }
        
        // display channel properties
        xref.propertiesPanel.setCard(Propertie.CHANNEL);
    }    
}
