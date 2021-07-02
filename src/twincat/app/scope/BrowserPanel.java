package twincat.app.scope;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class BrowserPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        TREE, TARGET
    } 

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final TreePanel treePanel = new TreePanel();
    
    private final TargetPanel targetPanel = new TargetPanel();

    /*************************/
    /****** constructor ******/
    /*************************/

    public BrowserPanel() {
        this.setLayout(new CardLayout());
        this.add(treePanel, Card.TREE.toString());
        this.add(targetPanel, Card.TARGET.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void setCard(Card card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
    }
}
