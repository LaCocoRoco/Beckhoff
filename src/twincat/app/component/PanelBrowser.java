package twincat.app.component;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Browser;

public class PanelBrowser extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Browser browser = Browser.OVERVIEW;

    private final PanelControl panelControl;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final TreeOverview treeOverview = new TreeOverview(this);

    private final TreeAcquisition treeAcquisition = new TreeAcquisition(this);

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelBrowser(PanelControl panelControl) {
        this.panelControl = panelControl;
        this.setLayout(new CardLayout());
        this.add(treeOverview, Browser.OVERVIEW.toString());
        this.add(treeAcquisition, Browser.ACQUISITION.toString());
        this.setCard(browser);
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

    public Browser getCard() {
        return browser;
    }

    public void setCard(Browser card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.browser = card;       
    }
}
