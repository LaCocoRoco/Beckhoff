package twincat.app.component;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Browser;

public class PanelTree extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Browser browser = Browser.BROWSER;

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
        this.add(treeBrowser, Browser.BROWSER.toString());
        this.add(treeSearch, Browser.SEARCH.toString());
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
