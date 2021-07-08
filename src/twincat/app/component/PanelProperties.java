package twincat.app.component;

import java.awt.CardLayout;

import javax.swing.JPanel;

import twincat.app.constant.Properties;

public class PanelProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private Properties card = Properties.EMPTY;  

    private final PanelControl panelControl;

    /*************************/
    /*** local attributes ***/
    /*************************/   

    private final PropertiesTriggerGroup propertiesTriggerGroup = new PropertiesTriggerGroup();
    
    private final PropertiesTriggerChannel propertiesTriggerChannel = new PropertiesTriggerChannel();
    
    private final PropertiesChart propertiesChart = new PropertiesChart();
    
    private final PropertiesAxis propertiesAxis = new PropertiesAxis();
    
    private final PropertiesChannel propertiesChannel = new PropertiesChannel();
    
    private final PropertiesAcquisition propertiesAcquisition = new PropertiesAcquisition();

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelProperties(PanelControl panelControl) {
        this.panelControl = panelControl;
        
        this.setLayout(new CardLayout());
        this.add(new JPanel(), Properties.EMPTY.toString());
        this.add(propertiesTriggerGroup, Properties.TRIGGER_GROUP.toString());
        this.add(propertiesTriggerChannel, Properties.TRIGGER_CHANNEL.toString());
        this.add(propertiesChart, Properties.CHART.toString());
        this.add(propertiesAxis, Properties.AXIS.toString());
        this.add(propertiesChannel, Properties.CHANNEL.toString());
        this.add(propertiesAcquisition, Properties.ACQUISITION.toString());
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

    public Properties getCard() {
        return card;
    }

    public void setCard(Properties card) {
        CardLayout cardLayout = (CardLayout) (this.getLayout());
        cardLayout.show(this, card.toString());
        this.card = card;        
    }
}
