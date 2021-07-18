package twincat.app.layer;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Browser;

public class BrowserPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Browser card = Browser.SCOPE;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public BrowserPanel(XReference xref) {
        this.setLayout(new CardLayout());
        this.add(xref.scopeBrowser, Browser.SCOPE.toString());
        this.add(xref.symbolBrowser, Browser.SYMBOL.toString());
        this.setCard(card);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Browser getCard() {
        return card;
    }

    public void setCard(Browser card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
}
