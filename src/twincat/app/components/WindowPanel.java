package twincat.app.components;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class WindowPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public enum Card {
        SCOPE,
        ADS,
        AXIS
    }
    
    /*************************/
    /*** local attributes ***/
    /*************************/

    private final AdsPanel adsInfoPanel = new AdsPanel();

    private final ScopePanel scopePanel = new ScopePanel();

    private final AxisPanel axisPanel = new AxisPanel();

    /*************************/
    /****** constructor ******/
    /*************************/

    public WindowPanel() {
        this.setLayout(new CardLayout());
        this.add(scopePanel, Card.SCOPE.toString());
        this.add(adsInfoPanel, Card.ADS.toString());
        this.add(axisPanel, Card.AXIS.toString());
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void displayWindow(String name) {
        CardLayout windowLayout = (CardLayout) (this.getLayout());
        windowLayout.show(this, name);
    }    
}
