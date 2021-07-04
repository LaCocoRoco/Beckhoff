package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelWindow extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public static enum Card {
        SCOPE, ADS, AXIS, SETTINGS
    }

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Card card = Card.SCOPE;

    private final PanelAds panelAds = new PanelAds();

    private final PanelScope panelScope = new PanelScope();

    private final PanelAxis panelAxis = new PanelAxis();
    
    private final PanelSettings panelSettings = new PanelSettings();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelWindow() {
        this.setLayout(new CardLayout());
        this.add(panelScope, Card.SCOPE.toString());
        this.add(panelAds, Card.ADS.toString());
        this.add(panelAxis, Card.AXIS.toString());
        this.add(panelSettings, Card.SETTINGS.toString());
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public PanelAds getPanelAds() {
        return panelAds;
    }

    public PanelScope getPanelScope() {
        return panelScope;
    }

    public PanelAxis getPanelAxis() {
        return panelAxis;
    }

    public PanelSettings getPanelSettings() {
        return panelSettings;
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
