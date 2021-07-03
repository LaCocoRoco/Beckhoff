package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        TRIGGER_GROUP, TRIGGER_CHANNEL,
        CHART, AXIS, CHANNEL, TARGET
    }
        
    private final PropertiesTriggerGroup triggerGroupProperties = new PropertiesTriggerGroup();
    
    private final PropertiesTriggerChannel riggerChannelProperties = new PropertiesTriggerChannel();
    
    private final PropertiesChart chartProperties = new PropertiesChart();
    
    private final PropertiesAxis axisProperties = new PropertiesAxis();
    
    private final PropertiesChannel channelProperties = new PropertiesChannel();
    
    private final PropertiesAcquisition acquisitionProperties = new PropertiesAcquisition();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelProperties() {
        this.setLayout(new CardLayout());
        this.add(triggerGroupProperties, Card.TRIGGER_GROUP.toString());
        this.add(riggerChannelProperties, Card.TRIGGER_CHANNEL.toString());
        this.add(chartProperties, Card.CHART.toString());
        this.add(axisProperties, Card.AXIS.toString());
        this.add(channelProperties, Card.CHANNEL.toString());
        this.add(acquisitionProperties, Card.TARGET.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void setCard(Card card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
    }
}
