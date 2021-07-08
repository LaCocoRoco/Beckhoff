package twincat.app.component;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Window;

public class PanelWindow extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Window card = Window.SCOPE;

    private final PanelAds panelAds = new PanelAds();

    private final PanelScope panelScope = new PanelScope();

    private final PanelAxis panelAxis = new PanelAxis();
    
    private final PanelSettings panelSettings = new PanelSettings();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelWindow() {
        this.setLayout(new CardLayout());
        this.add(panelScope, Window.SCOPE.toString());
        this.add(panelAds, Window.ADS.toString());
        this.add(panelAxis, Window.AXIS.toString());
        this.add(panelSettings, Window.SETTINGS.toString());
        this.setCard(card);
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public PanelAds getPanelAds() {
        return panelAds;
    }

    public PanelScope getPanelScope() {
        return panelScope;
    }

    public PanelAxis getPanelAxis() {
        return panelAxis;
    }

    public PanelSettings getPanelSettings() {
        return panelSettings;
    }

    /*************************/
    /********* public ********/
    /*************************/
    
    public Window getCard() {
        return card;
    }

    public void setCard(Window card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }  
}
