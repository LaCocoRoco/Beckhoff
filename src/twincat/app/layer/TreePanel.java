package twincat.app.layer;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Tree;

public class TreePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Tree card = Tree.SCOPE;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public TreePanel(XReference xref) {
        this.setLayout(new CardLayout());
        this.add(xref.scopeTree, Tree.SCOPE.toString());
        this.add(xref.symbolTree, Tree.SYMBOL.toString());
        this.setCard(card);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Tree getCard() {
        return card;
    }

    public void setCard(Tree card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
}
