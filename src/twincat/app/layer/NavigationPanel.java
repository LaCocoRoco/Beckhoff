package twincat.app.layer;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Navigation;

public class NavigationPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Navigation card = Navigation.CHART;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public NavigationPanel(XReference ref) {
        this.setLayout(new CardLayout());
        this.add(ref.chartPanel, Navigation.CHART.toString());
        this.add(ref.loaderPanel, Navigation.LOADER.toString());
        this.setCard(card);
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Navigation getCard() {
        return card;
    }

    public void setCard(Navigation card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
}
