package twincat.app.component;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class PanelTree extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public static enum Card {
        BROWSE, SEARCH
    }

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Card card = Card.BROWSE;

    private final PanelControl panelControl;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final TreeBrowser treeBrowser = new TreeBrowser(this);

    private final TreeSearch treeSearch = new TreeSearch(this);

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelTree(PanelControl panelControl) {
        this.panelControl = panelControl;
        this.setLayout(new CardLayout());
        this.add(treeBrowser, Card.BROWSE.toString());
        this.add(treeSearch, Card.SEARCH.toString());
        this.setCard(card);
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
