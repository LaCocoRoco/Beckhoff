package twincat.app.layer;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Properties;

public class PropertiesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Properties card = Properties.EMPTY;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public PropertiesPanel(XReference xref) {
        this.setLayout(new CardLayout());
        this.add(new JPanel(), Properties.EMPTY.toString());
        this.add(xref.triggerGroupProperties, Properties.TRIGGER_GROUP.toString());
        this.add(xref.triggerChannelProperties, Properties.TRIGGER_CHANNEL.toString());
        this.add(xref.chartProperties, Properties.CHART.toString());
        this.add(xref.axisProperties, Properties.AXIS.toString());
        this.add(xref.channelProperties, Properties.CHANNEL.toString());
        this.add(xref.acquisitionProperties, Properties.ACQUISITION.toString());
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public Properties getCard() {
        return card;
    }

    public void setCard(Properties card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;
    }
}
