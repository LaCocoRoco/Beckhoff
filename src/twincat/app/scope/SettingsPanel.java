package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        TRIGGER_GROUP, TRIGGER_CHANNEL,
        CHART, AXIS, CHANNEL, TARGET
    }
        
    private TriggerGroupPanel triggerGroupPanel = new TriggerGroupPanel();

    /*************************/
    /****** constructor ******/
    /*************************/

    public SettingsPanel() {
        this.setLayout(new CardLayout());
        this.add(triggerGroupPanel, Card.TRIGGER_GROUP.toString());
        this.add(triggerGroupPanel, Card.TRIGGER_CHANNEL.toString());
        this.add(triggerGroupPanel, Card.CHART.toString());
        this.add(triggerGroupPanel, Card.AXIS.toString());
        this.add(triggerGroupPanel, Card.CHANNEL.toString());
        this.add(triggerGroupPanel, Card.TARGET.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void setCard(Card card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
    }
}
