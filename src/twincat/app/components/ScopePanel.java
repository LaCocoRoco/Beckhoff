package twincat.app.components;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class ScopePanel extends JPanel{
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        SIMPLE, COMPLEX
    }
    
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final ScopeComplex scopeComplex = new ScopeComplex();

    private final ScopeSimple scopeSimple = new ScopeSimple();
    
    /*************************/
    /****** constructor ******/
    /*************************/

    public ScopePanel() {
        this.setLayout(new CardLayout());
        this.add(scopeComplex, Card.COMPLEX.toString());
        this.add(scopeSimple, Card.SIMPLE.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void displayScope(String card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card);
    }
}
