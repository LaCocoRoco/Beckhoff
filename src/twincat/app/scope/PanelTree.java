package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelTree extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public static enum Card {
        BROWSER, SEARCH
    }

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Card card = Card.BROWSER;

    private final PanelControl panelControl;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final PanelBrowser panelBrowser = new PanelBrowser(this);

    private final PanelSearch panelSearch = new PanelSearch(this);

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelTree(PanelControl panelControl) {
        this.panelControl = panelControl;
        
        this.setLayout(new CardLayout());
        this.add(panelBrowser, Card.BROWSER.toString());
        this.add(panelSearch, Card.SEARCH.toString());
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
