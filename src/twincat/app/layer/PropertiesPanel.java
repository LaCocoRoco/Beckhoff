package twincat.app.layer;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import twincat.app.constant.Propertie;

public class PropertiesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final int TEMPLATE_WIDTH = 200;
    
    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Propertie card = Propertie.EMPTY;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public PropertiesPanel(XReference xref) {
        this.setLayout(new CardLayout());
        this.add(new JPanel(), Propertie.EMPTY.toString());
        this.add(xref.triggerGroupProperties, Propertie.TRIGGER_GROUP.toString());
        this.add(xref.triggerChannelProperties, Propertie.TRIGGER_CHANNEL.toString());
        this.add(xref.chartProperties, Propertie.CHART.toString());
        this.add(xref.axisProperties, Propertie.AXIS.toString());
        this.add(xref.channelProperties, Propertie.CHANNEL.toString());
        this.add(xref.acquisitionProperties, Propertie.ACQUISITION.toString());
        this.add(xref.scopeProperties, Propertie.SCOPE.toString());
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Propertie getCard() {
        return card;
    }

    public void setCard(Propertie card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
    
    /*********************************/
    /** public static final method ***/
    /*********************************/
    
    public static final JPanel buildTemplate(String borderTitle) {
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(lineBorder, borderTitle);
        JPanel panel = new JPanel();
        panel.setBorder(titleBorder);
        panel.setLayout(null);
        return panel;
    }
}
