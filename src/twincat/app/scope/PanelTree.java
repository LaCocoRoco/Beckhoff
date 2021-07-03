package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelTree extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        BROWSER, SEARCH
    } 

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final PanelBrowser browserPanel = new PanelBrowser();
    
    private final PanelSearch searchPanel = new PanelSearch();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelTree() {
        this.setLayout(new CardLayout());
        this.add(searchPanel, Card.SEARCH.toString());
        this.add(browserPanel, Card.BROWSER.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void setCard(Card card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
    }
}
