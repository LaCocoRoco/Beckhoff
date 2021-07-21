package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.components.NumberTextField;
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

    private final JPanel lineColor = new JPanel();

    private final JPanel plotColor = new JPanel();

    private final NumberTextField lineWidth = new NumberTextField();

    private final NumberTextField plotSize = new NumberTextField();
    
    private final JCheckBox antialias = new JCheckBox();

    private final JCheckBox lineVisible = new JCheckBox();

    private final JCheckBox plotVisible = new JCheckBox();

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

    private final PropertyChangeListener lineColorPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();
            channel.setLineColor(component.getBackground());
        }
    };

    private final PropertyChangeListener plotColorPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            Component component = (Component) propertyChangeEvent.getSource();
            channel.setPlotColor(component.getBackground());
        }
    };

    private PropertyChangeListener lineWidthPropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
            channel.setLineWidth((int) numberTextField.getValue());
        }
    };

    private PropertyChangeListener plotSizePropertyChanged = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            NumberTextField numberTextField = (NumberTextField) propertyChangeEvent.getSource();
            channel.setPlotSize((int) numberTextField.getValue());
        }
    };
  
    private final ItemListener lineVisibleItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                channel.setLineVisible(true);
            } else {
                channel.setLineVisible(false);
            }
        }
    };
    
    private final ItemListener plotVisibleItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                channel.setPlotVisible(true);
            } else {
                channel.setPlotVisible(false);
            }
        }
    };

    private final ItemListener antialiasItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                channel.setAntialias(true);
            } else {
                channel.setAntialias(false);
            }
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
        lineColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lineColor.setBackground(channel.getLineColor());
        lineColor.setBounds(15, 25, 40, 40);
        lineColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        lineColor.addPropertyChangeListener("background", lineColorPropertyChanged);

        JLabel lineColorText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_LINE_COLOR));
        lineColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineColorText.setBounds(60, 34, 120, 23);

        plotColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        plotColor.setBackground(channel.getPlotColor());
        plotColor.setBounds(15, 75, 40, 40);
        plotColor.addMouseListener(xref.colorProperties.getColorPropertieMouseAdapter());
        plotColor.addPropertyChangeListener("background", plotColorPropertyChanged);

        JLabel plotColorText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_PLOT_COLOR));
        plotColorText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        plotColorText.setBounds(60, 84, 120, 23);

        TitledPanel colorPanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_COLOR));
        colorPanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 130));
        colorPanel.add(lineColor);
        colorPanel.add(lineColorText);     
        colorPanel.add(plotColor);
        colorPanel.add(plotColorText); 
        
        // line style properties
        lineWidth.setValue(channel.getLineWidth());
        lineWidth.setMinValue(0);
        lineWidth.setMaxValue(100);
        lineWidth.addPropertyChangeListener("number", lineWidthPropertyChanged);
        lineWidth.setBounds(15, 25, 40, 20);

        JLabel lineWidthText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_LINE_WIDTH));
        lineWidthText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineWidthText.setBounds(60, 25, 120, 21);

        plotSize.setValue(channel.getPlotSize());
        plotSize.setMinValue(0);
        plotSize.setMaxValue(100);
        plotSize.addPropertyChangeListener("number", plotSizePropertyChanged);
        plotSize.setBounds(15, 55, 40, 20);

        JLabel plotSizeText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_PLOT_SIZE));
        plotSizeText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        plotSizeText.setBounds(60, 55, 120, 21);
        
        lineVisible.setSelected(channel.isLineVisible());
        lineVisible.addItemListener(lineVisibleItemListener);
        lineVisible.setFocusPainted(false);
        lineVisible.setBounds(25, 85, 20, 20);

        JLabel lineVisibleText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_LINE_VISIBLE));
        lineVisibleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        lineVisibleText.setBounds(60, 85, 120, 23);
        
        plotVisible.setSelected(channel.isPlotVisible());
        plotVisible.addItemListener(plotVisibleItemListener);
        plotVisible.setFocusPainted(false);
        plotVisible.setBounds(25, 110, 20, 20);

        JLabel plotVisibleText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_PLOT_VISIBLE));
        plotVisibleText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        plotVisibleText.setBounds(60, 110, 120, 23);
        
        antialias.setSelected(channel.isAntialias());
        antialias.addItemListener(antialiasItemListener);
        antialias.setFocusPainted(false);
        antialias.setBounds(25, 135, 20, 20);

        JLabel antialiasText = new JLabel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_ANTIALIAS));
        antialiasText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_SMALL));
        antialiasText.setBounds(60, 135, 120, 23);
 
        TitledPanel stylePanel = new TitledPanel(languageBundle.getString(Resources.TEXT_CHANNEL_PROPERTIES_STYLE));
        stylePanel.setPreferredSize(new Dimension(PropertiesPanel.TEMPLATE_WIDTH_SMALL, 169));
        stylePanel.add(lineWidth);
        stylePanel.add(lineWidthText);
        stylePanel.add(plotSize);
        stylePanel.add(plotSizeText);
        stylePanel.add(lineVisible);
        stylePanel.add(lineVisibleText);
        stylePanel.add(plotVisible);
        stylePanel.add(plotVisibleText);
        stylePanel.add(antialias);
        stylePanel.add(antialiasText);   
        
        // default content
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        contentPanel.add(commonPanel);
        contentPanel.add(colorPanel);
        contentPanel.add(stylePanel);

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
        channelNameTextField.setText(channel.getChannelName());
        channelNameTextField.setCaretPosition(0);

        
        // color properties
        lineColor.setBackground(channel.getLineColor());
        plotColor.setBackground(channel.getPlotColor());
        
        // style properties
        lineWidth.setValue(channel.getLineWidth());
        plotSize.setValue(channel.getPlotSize());
        antialias.setSelected(channel.isAntialias());
        lineVisible.setSelected(channel.isLineVisible());
        plotVisible.setSelected(channel.isPlotVisible());

        // display channel properties
        xref.propertiesPanel.setCard(Propertie.CHANNEL);
    }
}
