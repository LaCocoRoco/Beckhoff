package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelWindow extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        SCOPE, ADS, AXIS, SETTINGS
    }
    
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final PanelAds adsPanel = new PanelAds();

    private final PanelScope scopePanel = new PanelScope();

    private final PanelAxis axisPanel = new PanelAxis();
    
    private final PanelSettings settingsPanel = new PanelSettings();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelWindow() {
        this.setLayout(new CardLayout());
        this.add(scopePanel, Card.SCOPE.toString());
        this.add(adsPanel, Card.ADS.toString());
        this.add(axisPanel, Card.AXIS.toString());
        this.add(settingsPanel, Card.SETTINGS.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void setCard(Card card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
    }    
}
