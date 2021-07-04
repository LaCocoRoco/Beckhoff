package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public static enum Card {
        EMPTY, TRIGGER_GROUP, TRIGGER_CHANNEL,
        CHART, AXIS, CHANNEL, ACQUISITION
    }
    
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Card card = Card.EMPTY;  

    private final PanelControl panelControl;

    /*************************/
    /*** local attributes ***/
    /*************************/   

    private final PropertiesTriggerGroup propertiesTriggerGroup = new PropertiesTriggerGroup();
    
    private final PropertiesTriggerChannel propertiesTriggerChannel = new PropertiesTriggerChannel();
    
    private final PropertiesChart propertiesChart = new PropertiesChart();
    
    private final PropertiesAxis propertiesAxis = new PropertiesAxis();
    
    private final PropertiesChannel propertiesChannel = new PropertiesChannel();
    
    private final PropertiesAcquisition propertiesAcquisition = new PropertiesAcquisition();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelProperties(PanelControl panelControl) {
        this.panelControl = panelControl;
        
        this.setLayout(new CardLayout());
        this.add(new JPanel(), Card.EMPTY.toString());
        this.add(propertiesTriggerGroup, Card.TRIGGER_GROUP.toString());
        this.add(propertiesTriggerChannel, Card.TRIGGER_CHANNEL.toString());
        this.add(propertiesChart, Card.CHART.toString());
        this.add(propertiesAxis, Card.AXIS.toString());
        this.add(propertiesChannel, Card.CHANNEL.toString());
        this.add(propertiesAcquisition, Card.ACQUISITION.toString());
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public PanelControl getPanelControl() {
        return panelControl;
    }

    /*************************/
    /********* public ********/
    /*************************/

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;        
    }
}
