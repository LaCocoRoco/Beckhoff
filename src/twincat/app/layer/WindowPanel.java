package twincat.app.layer;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Window;

public class WindowPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Window card = Window.SCOPE;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public WindowPanel(XReference ref) {
        this.setLayout(new CardLayout());
        this.add(ref.scopePanel, Window.SCOPE.toString());
        this.add(ref.adsPanel, Window.ADS.toString());
        this.add(ref.axisPanel, Window.AXIS.toString());
        this.add(ref.settingsPanel, Window.SETTINGS.toString());
        this.setCard(card);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Window getCard() {
        return card;
    }

    public void setCard(Window card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
}
