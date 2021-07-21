package twincat.app.layer;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Propertie;

public class PropertiesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final boolean WRAP_LAYOUT = false; 
    
    public static final int TEMPLATE_WIDTH_BIG = 300;
    
    public static final int TEMPLATE_WIDTH_SMALL = 240;
    
    /*********************************/
    /******** global variable ********/
    /*********************************/
   
    private Propertie card = Propertie.EMPTY;

    private Propertie last = Propertie.EMPTY;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JPanel emptyProperties = new JPanel();
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public PropertiesPanel(XReference xref) {
        this.setLayout(new CardLayout());
        this.add(emptyProperties, Propertie.EMPTY.toString());
        this.add(xref.triggerGroupProperties, Propertie.TRIGGER_GROUP.toString());
        this.add(xref.triggerChannelProperties, Propertie.TRIGGER_CHANNEL.toString());
        this.add(xref.chartProperties, Propertie.CHART.toString());
        this.add(xref.axisProperties, Propertie.AXIS.toString());
        this.add(xref.channelProperties, Propertie.CHANNEL.toString());
        this.add(xref.acquisitionProperties, Propertie.ACQUISITION.toString());
        this.add(xref.scopeProperties, Propertie.SCOPE.toString());
        this.add(xref.colorProperties, Propertie.COLOR.toString());
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
        
        this.last = this.card;
        this.card = card;
    }
    
    public void lastCard() {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, last.toString());
        
        this.card = this.last;
    }
}
