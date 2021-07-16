package twincat.app.layer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import twincat.Resources;
import twincat.app.constant.Properties;

public class PropertiesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int TEMPLATE_BORDER_WIDTH = 200;
    
    private static final int TEMPATE_BORDER_HEIGHT = 100;
    
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Properties card = Properties.EMPTY;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public PropertiesPanel(XReference xref) {
        this.setLayout(new CardLayout());
        this.add(new JPanel(), Properties.EMPTY.toString());
        this.add(xref.triggerGroupProperties, Properties.TRIGGER_GROUP.toString());
        this.add(xref.triggerChannelProperties, Properties.TRIGGER_CHANNEL.toString());
        this.add(xref.chartProperties, Properties.CHART.toString());
        this.add(xref.axisProperties, Properties.AXIS.toString());
        this.add(xref.channelProperties, Properties.CHANNEL.toString());
        this.add(xref.acquisitionProperties, Properties.ACQUISITION.toString());
        this.add(xref.scopeProperties, Properties.SCOPE.toString());
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Properties getCard() {
        return card;
    }

    public void setCard(Properties card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
    
    /*********************************/
    /** public static final method ***/
    /*********************************/
    
    public static final JPanel buildTemplate(String languageBundleText) {
        ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);
        String text = languageBundle.getString(languageBundleText);
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(lineBorder, text);
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(titleBorder, border);
        JPanel panel = new JPanel();
        panel.setBorder(compoundBorder);
        panel.setPreferredSize(new Dimension(TEMPLATE_BORDER_WIDTH, TEMPATE_BORDER_HEIGHT));
        return panel;
    }
}
